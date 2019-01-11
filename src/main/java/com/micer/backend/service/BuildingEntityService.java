package com.micer.backend.service;

import com.micer.backend.pojo.BuildingEntity;

import java.util.List;
import java.util.Map;

public interface BuildingEntityService {

    public Map<String, Object> getIndexInfo(String uuid);

    public BuildingEntity getBuildingEntityInfo(String uuid, int buildingType);

    public Map<String, Object> getSuperStructure(String uuid, int buildingType);

    public List<String> getSlavesUuid(String uuid);

    public int getBuildingType(String uuid);
}
