package com.micer.backend.service;

import com.micer.backend.entity.Result;

public interface EntityService
{
    Result getEntityInfo(String uuid);
    Result getDetails(String uuid, String type, Long startTime, Long endTime);
    Result getMoreDetail(String master_uuid, String uuid, String type, Long startTime, Long endTime, String energyType);
}
