package com.micer.backend.enums;

import java.util.HashMap;
import java.util.Map;

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
    
    public EntityType getMasterType()
    {
        return slave2MasterMap.get(this);
    }
    
    public EntityType getSlaveType()
    {
        return master2SlaveMap.get(this);
    }
    
    public String getEntityTable()
    {
        return "e_" + type + "_t";
    }
    
    public String getMasterRelationTable()
    {
        if(this == EntityType.PROJECT) return null;
        return "r_" + getMasterType().getType() + "_" + getType() + "_t";
    }
    
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
}
