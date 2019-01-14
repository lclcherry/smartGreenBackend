package com.micer.backend.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public enum TimeType
{
    HOUR("hour"),
    DAY("day"),
    WEEK("week"),
    MONTH("month"),
    YEAR("year");
    
    TimeType(String type)
    {
        this.type = type;
    }
    
    private String type;
    
    public String getType()
    {
        return type;
    }
    
    public static TimeType fromType(String type)
    {
        return TimeType.valueOf(type.toUpperCase());
    }
    
    // 之后week数据库的表格完善后，可以考虑直接调用TimeType.values()返回一个数组用于迭代
    private static final List<TimeType> timeTypes = Collections.unmodifiableList(
            Arrays.asList(
                    TimeType.HOUR,
                    TimeType.DAY,
//                    TimeType.WEEK,
                    TimeType.MONTH,
                    TimeType.YEAR));
    
    public static Iterator<TimeType> iterator()
    {
        return timeTypes.iterator();
    }
}
