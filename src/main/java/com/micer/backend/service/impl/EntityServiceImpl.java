package com.micer.backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.micer.backend.dao.BuildingEntityDao;
import com.micer.backend.entity.Result;
import com.micer.backend.enums.EntityType;
import com.micer.backend.pojo.BuildingEntity;
import com.micer.backend.service.EntityService;
import com.micer.backend.utils.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EntityServiceImpl implements EntityService
{
    @Autowired
    private BuildingEntityDao buildingEntityDao;
    
    
    @Override
    public Result getEntityInfo(String uuid)
    {
        EntityType entityType;
        try
        {
            entityType = EntityUtil.getEntityType(uuid);
        }catch (IllegalArgumentException ex)
        {
            return Result.BadRequest().msg("uuid未指定有效实体类型").build();
        }
    
        BuildingEntity buildingEntity = buildingEntityDao.getEntityInfo(uuid, entityType.getEntityTable());
//        JSONObject data = new JSONObject();
//        JSONObject data = JSONObject.toJSON(buildingEntity);
        JSONObject data = (JSONObject)JSON.toJSON(buildingEntity);
        Map<String, Object> superStructure = new HashMap<>();
        getSuperStructure(uuid, entityType, superStructure);
        
       
        
        return Result.OK().build();
    }
    
    private void getSuperStructure(String uuid, EntityType entityType, Map<String, Object> superStructure)
    {
        if(entityType == null) return;
        BuildingEntity entity = buildingEntityDao.getMasterEntityInfo(uuid, entityType.getMasterRelationTable(), entityType.getMasterType().getEntityTable());
        superStructure.put(entityType.getMasterType().getType(), entity);
        uuid = buildingEntityDao.getMasterUuid(uuid, entityType.getMasterRelationTable());
        entityType = entityType.getMasterType();
        getSuperStructure(uuid, entityType, superStructure);
    }
}
