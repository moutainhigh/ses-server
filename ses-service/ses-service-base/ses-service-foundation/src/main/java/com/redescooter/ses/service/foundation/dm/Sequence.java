package com.redescooter.ses.service.foundation.dm;


import lombok.ToString;

@ToString
public class Sequence {

    private long offset = 0;

    private long increment = 1;

    private long max = 0;

    public long getNext() {
        long value = this.offset;
        this.offset = value + this.increment;
        return value;
    }

    public void init(long currentValue, long length, long increment) {
        this.offset = currentValue;
        this.max = currentValue + length;
        this.increment = increment;
    }

    public long available() {
        if (offset == 0) {
            return 0;
        }
        if (this.max == this.offset) {
            return 0;
        }
        return ((this.max - this.offset) / this.increment) - 1;
    }


    public void clear() {
        this.offset = 0;
        this.increment = 1;
        this.max = 0;
    }
}
