package com.micer.backend.service.impl;

import com.micer.backend.dao.BuildingEntityDao;
import com.micer.backend.pojo.BuildingEntity;
import com.micer.backend.service.GetMoreDetailService;
import com.micer.backend.service.ManagementEntityService;
import com.micer.backend.utils.JsonResult;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GetMoreDetailServiceImpl implements GetMoreDetailService {
    protected static org.slf4j.Logger logger = LoggerFactory.getLogger(GetMoreDetailServiceImpl.class);

    @Autowired
    BuildingEntityDao buildingEntityDao = null;

    @Autowired(required = false)
    ManagementEntityService managementEntityService = null;

    @Autowired
    GetEnergyConsumptionServiceImpl getEnergyConsumptionService = null;

    @Override
    public JsonResult getMoreDetail(String master_uuid, String uuid, String type, Long startTime, Long endTime) {
        JsonResult result = new JsonResult("201","");
        int masterbuildingType = managementEntityService.getBuildingType(master_uuid);
        int slaveBuildingType = managementEntityService.getBuildingType(uuid);
        String masterslaveTable = getMasterSlaveTable(masterbuildingType);
        String buildingTable = getBuidingTable(slaveBuildingType);
        List<String> slave_uuid = buildingEntityDao.getSlavesUuid(master_uuid,masterslaveTable);

        List<Map<String,Object>> slaveData = new ArrayList<>();
        //某一实体总电量
        double[] valueSum = new double[slave_uuid.size()];
        List<Map<String,Number>> value_rate = new ArrayList<>();
        double totalValue = 0;

        for(int i = 0;i<slave_uuid.size();i++) {
            String slave = slave_uuid.get(i);
            BuildingEntity buildingEntity = buildingEntityDao.getEntityInfo(slave,buildingTable);
            List<Map<String, Number>> energy = getEnergyConsumptionService.getFixedTimePeriodEC(slave,slaveBuildingType,type,startTime,endTime);
            for(int j = 0;j<energy.size();j++) {
                Map<String, Number> time_value = energy.get(j);
                valueSum[i] += time_value.get("value") == null ?  0.0 : time_value.get("value").doubleValue();
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

        for(int i = 0;i<slave_uuid.size();i++) {
            String slave = slave_uuid.get(i);
            double rate = totalValue == 0 ? 0 : (valueSum[i]/totalValue)*100;
            value_rate.get(i).put("value",Math.floor(valueSum[i]));
            BigDecimal bg = new BigDecimal(rate);
            value_rate.get(i).put("rate",bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());
            Map<String,Object> slaveDataMap = slaveData.get(i);
            slaveDataMap.put("data",value_rate.get(i));
        }

        Map<String,Object> data = new HashMap<>();
        data.put("comparison",slaveData);
        result.setMsg("success");
        result.setData(data);
        return result;
    }

    @Override
    public String getMasterSlaveTable(int masterBuildingType) {
        switch(masterBuildingType) {
            case 0:
                return "r_project_building_t";
            case 1:
                return "r_building_floor_t";
            case 2:
                return "r_floor_room_t";
            default:
                return "";
        }
    }

    @Override
    public String getBuidingTable(int buildingType) {
        switch(buildingType) {
            case 0:
                return "e_project_t";
            case 1:
                return "e_building_t";
            case 2:
                return "e_floor_t";
            case 3:
                return "e_room_t";
            default:
                return "";
        }
    }
}
