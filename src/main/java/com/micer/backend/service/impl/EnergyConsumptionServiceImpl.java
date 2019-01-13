package com.micer.backend.service.impl;

import com.micer.backend.dao.EnergyConsumptionDao;
import com.micer.backend.pojo.EnergyConsumption;
import com.micer.backend.service.BuildingEntityService;
import com.micer.backend.service.EnergyConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class EnergyConsumptionServiceImpl implements EnergyConsumptionService {
    @Autowired
    EnergyConsumptionDao energyConsumptionDao = null;
    @Autowired
    BuildingEntityService buildingEntityService = null;

    @Override
    public Map<String, Object> getAllPeriodEC(String uuid){
        int buildingType = buildingEntityService.getBuildingType(uuid);

        Map<Long, Double>[] hours = getPeriodEC(uuid, buildingType, 0);
        Map<Long, Double>[] days = getPeriodEC(uuid, buildingType, 1);
        Map<Long, Double>[] months = getPeriodEC(uuid, buildingType, 3);
        Map<Long, Double>[] years = getPeriodEC(uuid, buildingType, 4);

        Map<String, Object> map = new HashMap<>();
        map.put("hour", hours);
        map.put("day", days);
        map.put("month", months);
        map.put("year", years);

        return map;
    }

    @Override
    public Map<Long, Double>[] getPeriodEC(String uuid, int buildingType, int timeType){
        List<Map<Long, Double>> list = new ArrayList<>();

//        if(timeType == 0){
//            list = energyConsumptionDao.getPeriodEC(uuid, "d_room_hour_t");
//        } else if (timeType == 1){
//            list = energyConsumptionDao.getPeriodEC(uuid, "d_room_day_t");
//        } else if (timeType == 2){
//            list = energyConsumptionDao.getPeriodEC(uuid, "d_room_week_t");
//        } else if (timeType == 3){
//            list = energyConsumptionDao.getPeriodEC(uuid, "d_room_month_t");
//        } else if (timeType == 4) {
//            list = energyConsumptionDao.getPeriodEC(uuid, "d_room_year_t");
//        }

        Map<Long, Double>[] maps = new Map[list.size()];
        for(int i = 0; i < list.size(); i++){
            maps[i] = list.get(i);
        }
        return maps;
    }

    @Override
    public Map<Long, Double>[] getFixTimePeriodEC(String uuid, Long startTime, Long endTime){
        List<Map<Long, Double>> list = new ArrayList<>();
//        list = energyConsumptionDao.getFixTimePeriodEC(uuid, "d_room_hour_t", startTime, endTime);
        Map<Long, Double>[] maps = new Map[list.size()];
//        for(int i = 0; i < list.size(); i++){
//            maps[i] = list.get(i);
//        }
        return maps;
    }
}
