package com.micer.backend.service.impl;

import com.micer.backend.dao.EnergyConsumptionDao;
import com.micer.backend.service.GetEnergyConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GetEnergyConsumptionServiceImpl implements GetEnergyConsumptionService {
    @Autowired
    EnergyConsumptionDao energyConsumptionDao = null;

    @Override
    public List<Map<String, Number>> getFixedTimePeriodEC(String uuid, int entityType, String timeType, Long startTime, Long endTime) {
        StringBuilder table = new StringBuilder("d_");
        switch(entityType) {
            case 0:
                table.append("project_");
                break;
            case 1:
                table.append("building_");
                break;
            case 2:
                table.append("floor_");
                break;
            case 3:
                table.append("room_");
                break;
        }
        table.append(timeType).append("_t");

        String tableStr = table.toString();
        List<Map<String, Number>> result = energyConsumptionDao.getFixTimePeriodEC(uuid,table.toString(),startTime*1000,endTime*1000);

        return result;
    }
}
