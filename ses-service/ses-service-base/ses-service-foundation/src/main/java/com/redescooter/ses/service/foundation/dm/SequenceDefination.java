package com.redescooter.ses.service.foundation.dm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SequenceDefination {

    private String name;

    private long currentValue;

    private long increment;

    private long cache;
}
