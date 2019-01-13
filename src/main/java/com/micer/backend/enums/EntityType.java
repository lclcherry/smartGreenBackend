package com.micer.backend.enums;

public enum EntityType
{
    PROJECT("project", null),
    BUILDING("building", EntityType.PROJECT),
    FLOOR("floor", EntityType.BUILDING),
    ROOM("room", EntityType.FLOOR),
    EMETER("emeter", null),
    WMETER("wmeter", null)
    ;
    
    EntityType(String type, EntityType masterType)
    {
        this.type = type;
        this.masterType = masterType;
    }
    
    String type;
    EntityType masterType;
    
    public String getType()
    {
        return type;
    }
    
    public String getEntityTable()
    {
        return "e_" + type + "_t";
    }
    
    public EntityType getMasterType()
    {
        return masterType;
    }
    
//    public EntityType getMasterType()
//    {
//        switch (this)
//        {
//            case ROOM: return EntityType.FLOOR;
//            case FLOOR: return EntityType.BUILDING;
//            case BUILDING: return EntityType.PROJECT;
//            case PROJECT: return null;
//        }
//        return null;
//    }
    
    public String getMasterRelationTable()
    {
        return "r_" + getMasterType() + "_" + getType() + "_t";
    }
}
