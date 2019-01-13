package com.micer.backend.service;

import com.micer.backend.entity.Result;
import com.micer.backend.pojo.BuildingEntity;

import java.util.List;
import java.util.Map;

public interface BuildingEntityService {
    
    public Result getEntityInfo(String uuid);

    public Result getIndexInfo(String uuid);

    public Result getBuildingEntityInfo(String uuid, int buildingType);

    public Result getSuperStructure(String uuid, int buildingType);

    public Result getSlavesUuid(String uuid);

    public Result getBuildingType(String uuid);
}
