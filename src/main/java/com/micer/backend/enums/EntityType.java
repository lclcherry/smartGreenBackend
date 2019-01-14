package com.micer.backend.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 用面向对象的方法抽象实体类型
 * @since 2019/1/14
 */
public enum EntityType
{
    PROJECT("project"),
    BUILDING("building"),
    FLOOR("floor"),
    ROOM("room"),
    // 也许以后会用上吧？
    EMETER("emeter"),
    WMETER("wmeter")
    ;
    
    private static Map<EntityType, EntityType> slave2MasterMap = new HashMap<>();
    private static Map<EntityType, EntityType> master2SlaveMap = new HashMap<>();
    
    // 静态代码块，用于初始化slave2MasterMap和master2SlaveMap
    static {
        slave2MasterMap.put(EntityType.BUILDING, EntityType.PROJECT );
        slave2MasterMap.put(EntityType.FLOOR, EntityType.BUILDING);
        slave2MasterMap.put(EntityType.ROOM, EntityType.FLOOR);
        
        for(EntityType entityType: slave2MasterMap.keySet())
            master2SlaveMap.put(slave2MasterMap.get(entityType), entityType);
    }
    
    EntityType(String type)
    {
        this.type = type;
    }
    
    private String type;
    
    public String getType()
    {
        return type;
    }
    
    /**
     * 实体的父实体类型
     * @return EntityType
     */
    public EntityType getMasterType()
    {
        return slave2MasterMap.get(this);
    }
    
    public EntityType getSlaveType()
    {
        return master2SlaveMap.get(this);
    }
    
    /**
     * 实体表格
     * @return String
     */
    public String getEntityTable()
    {
        return "e_" + type + "_t";
    }
    
    /**
     * 实体和父实体的关系表 r_master_slave_t
     * @return String
     */
    public String getMasterRelationTable()
    {
        if(this == EntityType.PROJECT) return null;
        return "r_" + getMasterType().getType() + "_" + getType() + "_t";
    }
    
    /**
     * 实体和从实体的关系表
     * @return String
     */
    public String getSlaveRelationTable()
    {
        if(this == EntityType.ROOM) return null;
        return "r_" + getType() + "_" + getSlaveType().getType() + "_t";
    }
    
    /**
     * 获取实体类型的字符表示的对应实体对象
     * 即 "project" -> Entity.PROJECT
     * @param type ∈ {"project", "building", "floor", "room"}
     * @return
     * @throws IllegalArgumentException if the specified enum type @code{EntityType} has
     *         no constant with the specified name
     */
    public static EntityType fromType(String type)
    {
        return EntityType.valueOf(type.toUpperCase());
    }
    
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
     * @param uuid 实体标识。长度为5：Entity.PROJECT；长度为13：其它实体
     * @return EntityType
     * 如果找不到uuid指定的实体类型，返回null
     */
    public static EntityType getEntityTypeFromUUID(String uuid)
    {
        assert uuid != null && (uuid.length() == 5 || uuid.length() == 13);
        if(uuid.length() == 5) return EntityType.PROJECT;
        String deviceType = uuid.substring(5, 9);
        return uuid2EntityTypeMap.get(deviceType);
    }
}
