package com.micer.backend.utils;

import com.micer.backend.enums.EntityType;
import com.micer.backend.enums.TimeType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenlvjia
 * @since 2019/01/12
 */
public class EntityUtil
{
    private static final Map<String, EntityType> uuid2EntityTypeMap = new HashMap<>();
    
    /**
     * 数据库目前的编码形式 until 2019/01/12
     * 二级编码：uuid的第5个字符到第8个字符（字符下标从0开始）
     * 二级编码 实体类型
     *   null    project
     *   2001    building
     *   2002    floor
     *   2003    room
     *   1001    emeter
     *
     * 也许可以考虑把这些编码存到数据库单独一张表里，程序启动时加载数据库
     * 而不是采用这种硬编码的方式
     */
    static {
        uuid2EntityTypeMap.put("", EntityType.PROJECT);
        uuid2EntityTypeMap.put("2001", EntityType.BUILDING);
        uuid2EntityTypeMap.put("2002", EntityType.FLOOR);
        uuid2EntityTypeMap.put("2003", EntityType.ROOM);
        uuid2EntityTypeMap.put("1001", EntityType.EMETER);
    }
    
    /**
     * 获取uuid指定的实体类型
     * @param uuid
     * @return
     * 如果找不到uuid指定的实体类型，返回null
     */
    public static EntityType getEntityTypeFromUUID(String uuid)
    {
        assert uuid != null && (uuid.length() == 5 || uuid.length() == 13);
        if(uuid.length() == 5) return EntityType.PROJECT;
        String deviceType = uuid.substring(5, 9);
        return uuid2EntityTypeMap.get(deviceType);
    }
    
    public static String getEntityTimeTable(EntityType entityType, TimeType timeType)
    {
        return "d_" + entityType.getType() + "_" + timeType.getType() + "_t";
    }
    
    
}
