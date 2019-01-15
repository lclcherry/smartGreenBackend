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
import com.micer.backend.utils.TimeUtil;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    
    /**
     * 获取实体基本信息 + 上层建筑信息
     * @param uuid 实体标识
     * @param entityType 实体类型
     * @return
     */
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
    
    /**
     * 获取实体基本信息 + 上层建筑信息 + 各个时间维度下的最新能耗数据
     * @param uuid
     * @return
     */
    @Override
    public Result getEntityInfo(String uuid)
    {
        EntityType entityType= EntityType.getEntityTypeFromUUID(uuid);
        if(entityType == null)
        {
//            throw new IllegalArgumentException("uuid未指定有效实体类型");
            return Result.BadRequest().msg("uuid未指定有效实体类型").build();
        }
        Map<String, Object> data = getEntityBaseInfo(uuid, entityType);
    
        Iterator<TimeType> timeTypeIterator = TimeType.iterator();
        while (timeTypeIterator.hasNext()) // 迭代器遍历各个时间维度
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
     * @param superStructure 用于接收实体数据
     */
    private void getSuperStructure(String uuid, EntityType entityType, Map<String, Object> superStructure)
    {
        if(entityType == null || entityType == EntityType.PROJECT) return;
        BuildingEntity masterEntity = buildingEntityDao.getMasterEntityInfo(uuid, entityType.getMasterRelationTable(), entityType.getMasterType().getEntityTable());
        superStructure.put(entityType.getMasterType().getType(), masterEntity);
        uuid = masterEntity.getUuid();
        entityType = entityType.getMasterType();
        getSuperStructure(uuid, entityType, superStructure);
    }
    
    /**
     * 获取实体某一时间维度下的最新7条数据
     * @param uuid 实体标识
     * @param entityType 实体类型
     * @param timeType 时间类型
     * @return
     */
    public Map<String, Object> getEntityRecentEC(String uuid, EntityType entityType, TimeType timeType)
    {
        String entityTimeTable = EntityUtil.getEntityTimeTable(entityType, timeType);
        Map<String, Object> entityTimeEC = new LinkedHashMap<>();
//        List<Double> top2EC = energyConsumptionDao.getTopTwoEC(uuid, entityTimeTable);
        List<Map<String, Number>> top2EC = energyConsumptionDao.getTop2EC(uuid, entityTimeTable);
        Number value = top2EC.get(0).get("value");
        Number last_value = top2EC.size() > 1 ? top2EC.get(1).get("value") : -1; // 建议确定一个DEFAULT_VALUE
        
        // circle_rate 环比
        Long mostRecentTimeStamp = (top2EC.get(0).get("run_at")).longValue();
        Double circle_value;
        if(timeType == TimeType.YEAR)
        {
            circle_value = null;
        }else{
            Long circle_timestamp = TimeUtil.getCircleValueUnixTimeStamp(mostRecentTimeStamp, timeType);
            circle_value = energyConsumptionDao.getValueAt(uuid, entityTimeTable, circle_timestamp);
        }
        
        List<Map<String, Number>> period_data = energyConsumptionDao.getPeriodEC(uuid, entityTimeTable);
        entityTimeEC.put("value", value);
        entityTimeEC.put("last_value", last_value);
        entityTimeEC.put("circle_value", circle_value);
        entityTimeEC.put("period_data", period_data);
        
        return entityTimeEC;
    }
    
    /**
     * 获取实体特定时间维度下，特定时间段[startTime, endTime]内的能耗数据
     * @param uuid
     * @param entityType
     * @param timeType
     * @param startTime 开始时间点（包含）
     * @param endTime 结束时间点（包含）
     * @return
     */
    public Map<String, Object> getEntityFixTimePeriodEC(String uuid, EntityType entityType, TimeType timeType,
                                           Long startTime, Long endTime)
    {
        String entityTimeTable = EntityUtil.getEntityTimeTable(entityType, timeType);
        Map<String, Object> entityTimeEC = new LinkedHashMap<>();
        
        // last_value 同比
        Long last_timestamp = TimeUtil.getLastValueUnixTimeStamp(endTime, timeType);
        Double last_value = energyConsumptionDao.getValueAt(uuid, entityTimeTable, last_timestamp);
    
        // circle_value 环比
        Double circle_value;
        if(timeType == TimeType.YEAR)
        {
            circle_value = null;
        }else{
            Long circle_timestamp = TimeUtil.getCircleValueUnixTimeStamp(endTime, timeType);
            circle_value = energyConsumptionDao.getValueAt(uuid, entityTimeTable, circle_timestamp);
        }
        
        List<Map<String, Number>> fixTimePeriodEC = energyConsumptionDao.getFixTimePeriodEC(uuid, entityTimeTable, startTime, endTime);
        Double value = 0.0;
        for(Map<String, Number>map: fixTimePeriodEC)
        {
            value += (Double) map.get("value"); // 也可以考虑直接从数据库用sum语句？
        }
        
        entityTimeEC.put("value", value);
        entityTimeEC.put("last_value", last_value);
        entityTimeEC.put("circle_value", circle_value);
        entityTimeEC.put("period_data", fixTimePeriodEC);
        
        return entityTimeEC;
    }
    
    /**
     * 获取实体基本信息 + 上层建筑信息 + 实体特定时间下的能耗数据
     * @param uuid
     * @param type 时间维度类型
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Result getDetails(String uuid, String type, Long startTime, Long endTime)
    {
        EntityType entityType= EntityType.getEntityTypeFromUUID(uuid);
        if(entityType == null)
        {
            return Result.BadRequest().msg("uuid未指定有效实体类型").build();
        }
        Map<String, Object> data = getEntityBaseInfo(uuid, entityType);
        Map<String, Object> fixTimePeriodEC = getEntityFixTimePeriodEC(uuid, entityType, TimeType.fromType(type), startTime, endTime);
        data.putAll(fixTimePeriodEC);
        return Result.OK().data(data).build();
    }
    
    /**
     * 获取该实体各个“兄弟实体”的能耗数据和比率
     * @param master_uuid 主实体标识
     * @param uuid 当前实体标识
     * @param type 时间维度类型
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Result getMoreDetail(String master_uuid, String uuid, String type, Long startTime, Long endTime) {
        EntityType buildingType = EntityType.getEntityTypeFromUUID(master_uuid);
        EntityType slaveBuildingType = EntityType.getEntityTypeFromUUID(uuid);
        String masterSlaveTable = slaveBuildingType.getMasterRelationTable();
        String buildingTable = slaveBuildingType.getEntityTable();

        List<String> slave_uuid_list = buildingEntityDao.getSlavesUuid(master_uuid,masterSlaveTable);

        List<Map<String,Object>> slaveData = new ArrayList<>();
        double[] valueSum = new double[slave_uuid_list.size()];
        List<Map<String,Number>> value_rate = new ArrayList<>();
        double totalValue = 0;

        for(int i = 0;i<slave_uuid_list.size();i++) {
            String slave_uuid = slave_uuid_list.get(i);
            BuildingEntity buildingEntity = buildingEntityDao.getEntityInfo(slave_uuid,buildingTable);
            List<Map<String, Number>> energy = getFixedTimePeriodEC(slave_uuid,slaveBuildingType,TimeType.fromType(type),startTime,endTime);
            for(int j = 0;j<energy.size();j++) {
                Map<String, Number> time_value = energy.get(j);
                double db = time_value.get("value").doubleValue();
                valueSum[i] += Math.max(0, db); // 未采集到的值为-1
            }
            totalValue += valueSum[i];

            Map<String,Number> value_rate_map = new HashMap<>();
            value_rate.add(value_rate_map);
            Map<String,Object> slaveDataMap = new HashMap<>();
            slaveDataMap.put("uuid",buildingEntity.getUuid());
            slaveDataMap.put("name",buildingEntity.getName());
            slaveDataMap.put("order",buildingEntity.getOrder());
            slaveData.add(slaveDataMap);
        }

        for(int i = 0;i<slave_uuid_list.size();i++) {
            double rate = totalValue == 0 ? 0 : (valueSum[i]/totalValue)*100;
            value_rate.get(i).put("value",Math.floor(valueSum[i]));
            BigDecimal bg = new BigDecimal(rate);
            value_rate.get(i).put("rate",bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());
            Map<String,Object> slaveDataMap = slaveData.get(i);
            slaveDataMap.put("data",value_rate.get(i));
        }

        Map<String,Object> data = new HashMap<>();
        data.put("comparison",slaveData);
        return Result.OK().data(data).build();
    }
    
    /**
     * 获取实体在特定时间维度下，特定时间段的能耗数据
     * @param uuid
     * @param entityType
     * @param timeType
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Map<String, Number>> getFixedTimePeriodEC(String uuid, EntityType entityType, TimeType timeType, Long startTime, Long endTime) {
        String tableStr = EntityUtil.getEntityTimeTable(entityType, timeType);
        List<Map<String, Number>> result = energyConsumptionDao.getFixTimePeriodEC(uuid, tableStr,startTime,endTime);
        return result;
    }
}
