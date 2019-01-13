package com.micer.backend.service;

import com.micer.backend.pojo.BuildingEntity;

public interface ManagementEntityService {
    public int getBuildingType(String uuid);
    public BuildingEntity getBuildingEntityInfo(String uuid, int buildingType);
}
