package com.micer.backend.service;

import java.util.Map;

public interface EnergyConsumptionService {
    public Map<Long, Double>[] getPeriodEC(String uuid, int buildingType, int timeType);

    public Map<String, Object> getAllPeriodEC(String uuid);

    public Map<Long, Double>[] getFixTimePeriodEC(String uuid, Long startTime, Long endTime);
}
