package com.micer.backend.service;

import java.util.List;
import java.util.Map;

public interface GetEnergyConsumptionService {
    public List<Map<String, Number>> getFixedTimePeriodEC(String uuid, int entityType, String timeType, Long startTime, Long endTime);
}
