package com.micer.backend.utils;

import com.micer.backend.enums.EntityType;
import com.micer.backend.enums.TimeType;

/**
 * 实体工具类
 */
public class EntityUtil
{
    public static String getEntityTimeTable(EntityType entityType, TimeType timeType)
    {
        return "d_" + entityType.getType() + "_" + timeType.getType() + "_t";
    }
}
