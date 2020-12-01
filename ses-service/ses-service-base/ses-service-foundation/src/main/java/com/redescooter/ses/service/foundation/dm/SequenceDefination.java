package com.redescooter.ses.service.foundation.dm;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Data
public class SequenceDefination implements Serializable {

    private String name;

    private long currentValue;

    private long increment;

    private long cache;
}
