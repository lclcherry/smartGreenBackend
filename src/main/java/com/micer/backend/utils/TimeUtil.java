package com.micer.backend.utils;

import com.micer.backend.enums.TimeType;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class TimeUtil
{
    /**
     * 获取同比的时间戳
     * 思路：给定时间戳 -> 给定时间 -> 同比时间 -> 同比时间戳
     * @param unixTimeStamp
     * @param timeType
     * @return
     */
    public static Long getLastValueUnixTimeStamp(Long unixTimeStamp, TimeType timeType)
    {
        Timestamp timestamp = new Timestamp(unixTimeStamp);
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        switch(timeType)
        {
            case HOUR: localDateTime = localDateTime.minusHours(1); break;
            case DAY: localDateTime = localDateTime.minusDays(1); break;
            case WEEK: localDateTime = localDateTime.minusWeeks(1); break;
            case MONTH: localDateTime = localDateTime.minusMonths(1); break;
            case YEAR: localDateTime = localDateTime.minusYears(1); break;
        }
        return Timestamp.valueOf(localDateTime).getTime();
    }
    
    /**
     * 获取给定时间戳的环比时间戳
     * @param unixTimeStamp
     * @param timeType
     * @return
     * 注意：年份类型没有环比（上一个世纪这一年？？）
     */
    public static Long getCircleValueUnixTimeStamp(Long unixTimeStamp, TimeType timeType)
    {
        Timestamp timestamp = new Timestamp(unixTimeStamp);
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        switch(timeType)
        {
            case HOUR: localDateTime = localDateTime.minusDays(1); break;
            case DAY: localDateTime = localDateTime.minusWeeks(1); break;
            case WEEK: localDateTime = localDateTime.minusMonths(1); break;
            case MONTH: localDateTime = localDateTime.minusYears(1); break;
            case YEAR: throw new IllegalArgumentException("环比的时间维度不能是[年]");
        }
        return Timestamp.valueOf(localDateTime).getTime();
    }
}
