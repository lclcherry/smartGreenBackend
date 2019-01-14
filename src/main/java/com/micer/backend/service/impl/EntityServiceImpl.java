package com.micer.backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.micer.backend.dao.BuildingEntityDao;
import com.micer.backend.dao.EnergyConsumptionDao;
import com.micer.backend.entity.Result;
import com.micer.backend.enums.EntityType;
import com.micer.backend.enums.TimeType;
import com.micer.backend.pojo.BuildingEntity;
import com.micer.backend.service.EntityService;
import com.micer.backend.utils.EntityUtil;
import com.micer.backend.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class EntityServiceImpl implements EntityService
{
    @Autowired
    private BuildingEntityDao buildingEntityDao;
    
    @Autowired
    private EnergyConsumptionDao energyConsumptionDao;
    
    public Map<String, Object> getEntityBaseInfo(String uuid, EntityType entityType)
    {
        BuildingEntity buildingEntity = buildingEntityDao.getEntityInfo(uuid, entityType.getEntityTable());
        JSONObject entityInfoMap = (JSONObject)JSON.toJSON(buildingEntity);
        Map<String, Object> data = new LinkedHashMap<>(entityInfoMap);
    
        Map<String, Object> superStructure = new LinkedHashMap<>();
        getSuperStructure(uuid, entityType, superStructure);
        data.put("superStructure", superStructure);
        return data;
    }
    
    @Override
    public Result getEntityInfo(String uuid)
    {
        EntityType entityType= EntityUtil.getEntityTypeFromUUID(uuid);
        if(entityType == null)
        {
            throw new IllegalArgumentException("uuid未指定有效实体类型");
            //            return Result.BadRequest().msg("uuid未指定有效实体类型").build();
        }
        Map<String, Object> data = getEntityBaseInfo(uuid, entityType);
    
        Iterator<TimeType> timeTypeIterator = TimeType.iterator();
        while (timeTypeIterator.hasNext())
        {
            TimeType timeType = timeTypeIterator.next();
            data.put(timeType.getType(), getEntityRecentEC(uuid, entityType, timeType));
        }
        
        return Result.OK().data(data).build();
    }
    
    /**
     * 递归调用，把实体的上一层实体的数据填充进superStructure中
     * @param uuid
     * @param entityType
     * @param superStructure
     */
    private void getSuperStructure(String uuid, EntityType entityType, Map<String, Object> superStructure)
    {
        if(entityType == null || entityType == EntityType.PROJECT) return;
        BuildingEntity masterEntity = buildingEntityDao.getMasterEntityInfo(uuid, entityType.getMasterRelationTable(), entityType.getMasterType().getEntityTable());
        superStructure.put(entityType.getMasterType().getType(), masterEntity);
//        uuid = buildingEntityDao.getMasterUuid(uuid, entityType.getMasterRelationTable());
        uuid = masterEntity.getUuid();
        entityType = entityType.getMasterType();
        getSuperStructure(uuid, entityType, superStructure);
    }
    
    public Map<String, Object> getEntityRecentEC(String uuid, EntityType entityType, TimeType timeType)
    {
        String entityTimeTable = EntityUtil.getEntityTimeTable(entityType, timeType);
        Map<String, Object> entityTimeEC = new LinkedHashMap<>();
        List<Double> top2EC = energyConsumptionDao.getTopTwoEC(uuid, entityTimeTable);
//        List<Number> top2EC = energyConsumptionDao.getTop2EC(uuid, entityTimeTable);
        Number value = top2EC.get(0);
        Number last_value = top2EC.size() > 1 ? top2EC.get(1) : -1; // 建议确定一个DEFAULT_VALUE
        
        // TODO: circle_rate 环比
        
        
        List<Map<String, Number>> period_data = energyConsumptionDao.getPeriodEC(uuid, entityTimeTable);
        entityTimeEC.put("value", value);
        entityTimeEC.put("last_value", last_value);
//        entityTimeEC.put("circle_value", last_value);
        entityTimeEC.put("period_data", period_data);
        
        return entityTimeEC;
    }
    
    public Map<String, Object> getEntityFixTimePeriodEC(String uuid, EntityType entityType, TimeType timeType,
                                           Long startTime, Long endTime)
    {
        String entityTimeTable = EntityUtil.getEntityTimeTable(entityType, timeType);
        Map<String, Object> entityTimeEC = new LinkedHashMap<>();
        List<Double> top2EC = energyConsumptionDao.getTopTwoEC(uuid, entityTimeTable);
        //        List<Number> top2EC = energyConsumptionDao.getTop2EC(uuid, entityTimeTable);
        Number value = top2EC.get(0);
        Number last_value = top2EC.size() > 1 ? top2EC.get(1) : -1; // 建议确定一个DEFAULT_VALUE
        
        // TODO: circle_rate 环比
        
        
        List<Map<String, Number>> fixTimePeriodEC = energyConsumptionDao.getFixTimePeriodEC(uuid, entityTimeTable, startTime, endTime);
        entityTimeEC.put("value", value);
        entityTimeEC.put("last_value", last_value);
        //        entityTimeEC.put("circle_value", last_value);
        entityTimeEC.put("period_data", fixTimePeriodEC);
        
        return entityTimeEC;
    }
    
    @Override
    public Result getDetails(String uuid, String type, Long startTime, Long endTime)
    {
        EntityType entityType= EntityUtil.getEntityTypeFromUUID(uuid);
        if(entityType == null)
        {
            return Result.BadRequest().msg("uuid未指定有效实体类型").build();
        }
        Map<String, Object> data = getEntityBaseInfo(uuid, entityType);
        Map<String, Object> fixTimePeriodEC = getEntityFixTimePeriodEC(uuid, entityType, TimeType.fromType(type), startTime, endTime);
        data.putAll(fixTimePeriodEC);
        return Result.OK().data(data).build();
    }
    
    @Override
    public Result getMoreDetail(String master_uuid, String uuid, String type, Long startTime, Long endTime) {
        Result result = new Result(201,"");
        if(StringUtil.isEmpty(master_uuid)) {
            result.setMsg("master_uuid为空，无法获取uuid同一层级实体数据");
            return result;
        }
        
        EntityType buildingType = EntityUtil.getEntityTypeFromUUID(master_uuid);
        EntityType slaveBuildingType = EntityUtil.getEntityTypeFromUUID(uuid);
        String masterslaveTable = buildingType.getMasterRelationTable();
        String buildingTable = slaveBuildingType.getEntityTable();
        
        List<String> slave_uuid = buildingEntityDao.getSlavesUuid(master_uuid,masterslaveTable);
        
        List<Map<String,Object>> slaveData = new ArrayList<>();
        double[] valueSum = new double[slave_uuid.size()];
        List<Map<String,Number>> value_rate = new ArrayList<>();
        double totalValue = 0;
        DecimalFormat twoDog = new DecimalFormat("#.00");
        
        for(int i = 0;i<slave_uuid.size();i++) {
            String slave = slave_uuid.get(i);
            BuildingEntity buildingEntity = buildingEntityDao.getEntityInfo(slave,buildingTable);
            List<Map<String, Number>> energy = getFixedTimePeriodEC(slave,slaveBuildingType,TimeType.valueOf(type),startTime,endTime);
            for(int j = 0;j<energy.size();j++) {
                Map<String, Number> time_value = energy.get(i);
                double db = time_value.get("value").doubleValue();
                valueSum[i] += time_value.get("value") == null ?  0.0 : time_value.get("value").doubleValue();
            }
            totalValue += valueSum[i];
            
            Map<String,Number> value_rate_map = new HashMap<>();
            value_rate_map.put("value",Math.floor(valueSum[i]));
            value_rate.add(value_rate_map);
            Map<String,Object> slaveDataMap = new HashMap<>();
            slaveDataMap.put("uuid",buildingEntity.getUuid());
            slaveDataMap.put("name",buildingEntity.getName());
            slaveDataMap.put("order",buildingEntity.getOrder());
            slaveData.add(slaveDataMap);
        }
        
        for(int i = 0;i<slave_uuid.size();i++) {
            String slave = slave_uuid.get(i);
            double rate = (valueSum[i]/totalValue)*100;
            BigDecimal bg = new BigDecimal(rate);
            value_rate.get(i).put("rate",bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            Map<String,Object> slaveDataMap = slaveData.get(i);
            slaveDataMap.put("data",value_rate.get(i));
        }
        
        Map<String,Object> data = new HashMap<>();
        data.put("comparison",slaveData);
        result.setMsg("success");
        result.setData(data);
        return result;
    }
    
    public List<Map<String, Number>> getFixedTimePeriodEC(String uuid, EntityType entityType, TimeType timeType, Long startTime, Long endTime) {
        String tableStr = EntityUtil.getEntityTimeTable(entityType, timeType);
        List<Map<String, Number>> result = energyConsumptionDao.getFixTimePeriodEC(uuid, tableStr,startTime*1000,endTime*1000);
        return result;
    }
}
