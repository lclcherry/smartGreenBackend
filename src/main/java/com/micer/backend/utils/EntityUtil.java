package com.micer.backend.utils;

import com.micer.backend.enums.EntityType;

public class EntityUtil
{
    /*
    @author chenlvjia
    数据库目前的编码形式 until 20190112
    二级编码：uuid的第5个字符到第8个字符（字符下标从0开始）
    二级编码 实体类型
    null    project
    2001    building
    2002    floor
    2003    room
    1001    emeter
     */
    public static EntityType getEntityType(String uuid)
    {
        String deviceType = uuid.substring(5, 9);
        if(StringUtil.isEmpty(deviceType))
        {
            return EntityType.PROJECT;
        }else if("2001".equals(deviceType)){
            return EntityType.BUILDING;
        }else if("2002".equals(deviceType)){
            return EntityType.FLOOR;
        }else if("2003".equals(deviceType)){
            return EntityType.ROOM;
        }else if("1001".equals(deviceType)){
            return EntityType.EMETER;
        }
        
        throw new IllegalArgumentException("未指定有效实体类型的uuid: " + uuid);
        // 暂时没有水表数据
        //return EntityType.WMETER;
    }
}
