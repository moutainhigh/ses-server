package com.redescooter.ses.service.foundation.service.impl;


import com.redescooter.ses.api.foundation.exception.SequenceException;
import com.redescooter.ses.api.foundation.service.SequenceService;
import com.redescooter.ses.service.foundation.dao.SequenceMapper;
import com.redescooter.ses.service.foundation.dm.Sequence;
import com.redescooter.ses.service.foundation.dm.SequenceDefination;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import java.util.*;

@Slf4j
@Service
public class SequenceServiceImpl implements SequenceService {

    private Map<String, Sequence[]> sequenceCache = new HashMap<>();

    private static final int SEQUENCE_INDEX_MAIN = 0;

    private static final int SEQUENCE_INDEX_RESERVE = 1;

    private static final int SEQUENCE_INDEX_LOCK = 2;

    private Random random = new Random();

    @Autowired
    private SequenceMapper sequenceDao;

    @Override
    public long get(String sequenceName) throws SequenceException {
        if (sequenceName == null || sequenceName.trim().length() == 0) {
            throw new SequenceException("sequenceName is null.");
        }
        synchronized (sequenceName.intern()) {
            if (!sequenceCache.containsKey(sequenceName)) {
                cacheSequence(sequenceName);
            }
            try {
                Sequence sequence = getAvaibleSequence(sequenceName);
                if (sequence.available() == 0) {
                    throw new SequenceException(
                            "Sequence[" + sequenceName + "] acquisition failure, please try again");
                }
                return sequence.getNext();
            } catch (SequenceException e) {
                log.error(e.getMessage(), e);
                throw new SequenceException(e);
            } catch (Exception e) {
                log.error("Sequence[" + sequenceName + "] acquisition failure", e);
                throw new SequenceException("Sequence[" + sequenceName + "] acquisition failure", e);
            }
        }
    }

    @Override
    public List<Long> batchGet(String sequenceName, int batch) throws SequenceException {
        if (sequenceName == null || sequenceName.trim().length() == 0) {
            throw new SequenceException("sequenceName is null.");
        }
        if (batch < 1 || batch > 10000) {
            throw new SequenceException("batch size Range is [1-100000].");
        }
        List<Long> ids = new ArrayList<>(batch);
        synchronized (sequenceName.intern()) {
            if (!sequenceCache.containsKey(sequenceName)) {
                cacheSequence(sequenceName);
            }
            try {
                while (batch > 0) {
                    Sequence sequence = getAvaibleSequence(sequenceName);
                    if (sequence.available() == 0) {
                        throw new SequenceException(
                                "Sequence[" + sequenceName + "] acquisition failure, please try again");
                    }
                    while (sequence.available() > 0 && batch > 0) {
                        ids.add(sequence.getNext());
                        batch--;
                    }
                }
                return ids;
            } catch (SequenceException e) {
                log.error(e.getMessage(), e);
                throw new SequenceException(e);
            } catch (Exception e) {
                log.error("Sequence[" + sequenceName + "] acquisition failure", e);
                throw new SequenceException("Sequence[" + sequenceName + "] acquisition failure", e);
            }
        }
    }

    /**
     * 获取可用的sequence 先从缓存中无可用sequence，缓存中没有则从数据库获取
     *
     * @param sequenceName 序列名称
     * @return
     * @throws SequenceException
     */
    private Sequence getAvaibleSequence(String sequenceName) throws SequenceException {
        Sequence[] sequences = sequenceCache.get(sequenceName);
        Sequence lock = sequences[SEQUENCE_INDEX_LOCK];
        Sequence sequence = sequences[SEQUENCE_INDEX_MAIN];
        if (sequences[SEQUENCE_INDEX_MAIN].available() == 0
                && sequences[SEQUENCE_INDEX_RESERVE].available() == 0) {
            initSequenceByDb(sequenceName, sequences[SEQUENCE_INDEX_MAIN], 10);
            log.info("sync update sequence(" + sequenceName + "):" + sequences[SEQUENCE_INDEX_MAIN]);
            sequence = sequences[SEQUENCE_INDEX_MAIN];
            sequences[SEQUENCE_INDEX_RESERVE].clear();
            synchronized (lock) {
                lock.notifyAll();
            }
        } else if (sequences[SEQUENCE_INDEX_MAIN].available() == 0) {
            Sequence temp = sequences[SEQUENCE_INDEX_MAIN];
            sequences[SEQUENCE_INDEX_MAIN] = sequences[SEQUENCE_INDEX_RESERVE];
            sequences[SEQUENCE_INDEX_RESERVE] = temp;
            sequences[SEQUENCE_INDEX_RESERVE].clear();
            synchronized (lock) {
                lock.notifyAll();
            }
            sequence = sequences[SEQUENCE_INDEX_MAIN];
        }
        return sequence;
    }

    /**
     * 缓存sequence数组 数组索引0位主sequence 数组索引1位备用 sequence 数组索引2位异步更新线程锁
     *
     * @param sequenceName 序列名称
     * @throws SequenceException
     */
    private void cacheSequence(String sequenceName) throws SequenceException {
        Sequence[] sequences = new Sequence[3];

        sequences[SEQUENCE_INDEX_MAIN] = new Sequence();
        initSequenceByDb(sequenceName, sequences[SEQUENCE_INDEX_MAIN], 50);

        sequences[SEQUENCE_INDEX_RESERVE] = new Sequence();
        initSequenceByDb(sequenceName, sequences[SEQUENCE_INDEX_RESERVE], 50);

        Sequence lock = new Sequence();
        sequences[SEQUENCE_INDEX_LOCK] = lock;

        sequenceCache.put(sequenceName, sequences);
        log.info("sequence[" + sequenceName + "]cache init successful");

        startAsyncUpdateThread(sequenceName, lock);
    }

    /**
     * 启动线程进行sequence的后台更新
     *
     * @param sequenceName 序列名称
     * @param lock         异步更新线程锁
     */
    private void startAsyncUpdateThread(String sequenceName, Sequence lock) {
        Thread t = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            synchronized (lock) {
                                try {
                                    lock.wait();
                                } catch (InterruptedException e) {
                                    log.error("init sequence wait InterruptedException:", e);
                                }
                            }
                            Sequence sequence = sequenceCache.get(sequenceName)[SEQUENCE_INDEX_RESERVE];
                            try {
                                initSequenceByDb(sequenceName, sequence, 10);
                                log.info("async update sequence(" + sequenceName + "):" + sequence);
                            } catch (SequenceException e) {
                                log.error("async update sequence(" + sequenceName + ") failure:", e);
                            }
                        }
                    }
                });
        t.setName("sequence-async-update-thread-" + sequenceName);
        t.start();
    }

    /**
     * @param sequenceName 序列名称
     * @param sequence     缓存sequence对象
     * @param retryTimes   从数据库获取sequence时加了乐观锁，集群情况下有获取不到的情况，该参数可以设置获取次数
     * @throws SequenceException
     */
    private synchronized void initSequenceByDb(String sequenceName, Sequence sequence, int retryTimes)
            throws SequenceException {
        for (int i = 0; i < retryTimes; i++) {
            SequenceDefination sequenceDefination = sequenceDao.getSequence(sequenceName);
            if (sequenceDefination == null) {
                try {
                    sequenceDao.insertSequence(sequenceName);
                    sequenceDefination = sequenceDao.getSequence(sequenceName);
                } catch (DuplicateKeyException e) {
                    log.warn("create sequence[" + sequenceName + "] DuplicateKeyException");
                    sequenceDefination = sequenceDao.getSequence(sequenceName);
                } catch (Exception e) {
                    throw new SequenceException("Sequence[" + sequenceName + "] uncreated");
                }
            }
            long length = sequenceDefination.getCache() + random.nextInt(100);
            int updateSize =
                    sequenceDao.updateSequence(sequenceName, sequenceDefination.getCurrentValue(), length);
            if (updateSize == 0) {
                log.warn("sequence[" + sequenceName + "] update failure,retry times[" + i + "]");
                try {
                    Thread.sleep(1 + random.nextInt(3));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                sequence.init(
                        sequenceDefination.getCurrentValue(), length, sequenceDefination.getIncrement());
                return;
            }
        }
        throw new SequenceException("sequence[" + sequenceName + "] update failure");
    }
}
