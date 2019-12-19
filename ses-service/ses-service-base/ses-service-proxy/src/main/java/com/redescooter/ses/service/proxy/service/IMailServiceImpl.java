package com.redescooter.ses.service.proxy.service;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.proxy.exception.ProxyException;
import com.redescooter.ses.api.proxy.service.IMailService;
import com.redescooter.ses.service.proxy.exception.ExceptionCodeEnums;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 5/9/2019 5:08 下午
 * @ClassName: IMailServiceImpl
 * @Function: TODO
 */


@Slf4j
@Service
@Component
public class IMailServiceImpl implements IMailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Value("${spring.mail.from}")
    private String from;


    /**
     * 发送文本邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    @Override
    public void sendSimpleMail(String to, String subject, String content, String... cc) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setCc(cc);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    /**
     * 发送HTML邮件
     *
     * @param to
     * @param subject
     * @param parameterJson
     * @param templateNo
     */
    @Async("taskExecutor")
    @Override
    public void sendHtmlMail(String to, String subject, String parameterJson, int templateNo) {
        MimeMessage message = mailSender.createMimeMessage();

        //true表示需要创建一个multipart message
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(getContent(parameterJson, templateNo), true);
            mailSender.send(message);
            log.info("发送邮件成功");
        } catch (MessagingException e) {
            log.error("发送HTML邮件失败：", e);
            throw new ProxyException(ExceptionCodeEnums.SEND_CODE_FAILURE.getCode(), ExceptionCodeEnums.SEND_CODE_FAILURE.getMessage());
        }
    }


    @Async("taskExecutor")
    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();

        //true表示需要创建一个multipart message
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
            log.info("发送邮件成功");
        } catch (MessagingException e) {
            log.error("发送HTML邮件失败：", e);
            throw new ProxyException(ExceptionCodeEnums.SEND_CODE_FAILURE.getCode(), ExceptionCodeEnums.SEND_CODE_FAILURE.getMessage());
        }
    }

    @Override
    public void sendHtmlMail(String to, String subject, String parameterJson, int templateNo, String... cc) {
        MimeMessage message = mailSender.createMimeMessage();

        //true表示需要创建一个multipart message
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(getContent(parameterJson, templateNo), true);
            helper.setCc(cc);
            mailSender.send(message);
            log.info("发送邮件成功");
        } catch (MessagingException e) {
            log.error("发送HTML邮件失败：", e);
            throw new ProxyException(ExceptionCodeEnums.SEND_CODE_FAILURE.getCode(), ExceptionCodeEnums.SEND_CODE_FAILURE.getMessage());
        }
    }

    /**
     * 发送带附件的邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);

            mailSender.send(message);
            log.info("发送邮件成功");
        } catch (MessagingException e) {
            log.error("发送HTML邮件失败：", e);
            throw new ProxyException(ExceptionCodeEnums.SEND_CODE_FAILURE.getCode(), ExceptionCodeEnums.SEND_CODE_FAILURE.getMessage());
        }

    }

    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath, String... cc) {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            helper.setCc(cc);
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
            log.info("发送邮件成功");
        } catch (MessagingException e) {
            log.error("发送HTML邮件失败：", e);
            throw new ProxyException(ExceptionCodeEnums.SEND_CODE_FAILURE.getCode(), ExceptionCodeEnums.SEND_CODE_FAILURE.getMessage());
        }

        mailSender.send(message);
    }

    /**
     * 发送正文中有静态资源（图片）的邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     */
    @Override
    public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId) {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);

            mailSender.send(message);
            log.info("发送邮件成功");
        } catch (MessagingException e) {
            log.error("发送HTML邮件失败：", e);
            throw new ProxyException(ExceptionCodeEnums.SEND_CODE_FAILURE.getCode(), ExceptionCodeEnums.SEND_CODE_FAILURE.getMessage());
        }

    }

    @Override
    public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId, String... cc) {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = null;

        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            helper.setCc(cc);
            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);
            mailSender.send(message);
            log.info("发送邮件成功");
        } catch (MessagingException e) {
            log.error("发送HTML邮件失败：", e);
            throw new ProxyException(ExceptionCodeEnums.SEND_CODE_FAILURE.getCode(), ExceptionCodeEnums.SEND_CODE_FAILURE.getMessage());
        }
    }

    /**
     * 模板转化
     *
     * @param parameterJson
     * @param mailTemplateNo
     * @return
     */
    private String getContent(String parameterJson, int mailTemplateNo) {
        Context context = new Context();
        context.setVariables(JSON.parseObject(parameterJson, Map.class));
        return templateEngine.process(MailTemplateEventEnums.getMailTemplateEventEnum(mailTemplateNo).getName(), context);

    }
}
