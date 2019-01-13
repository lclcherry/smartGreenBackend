package com.micer.backend.service;

import com.micer.backend.utils.JsonResult;


public interface GetMoreDetailService {

    public JsonResult getMoreDetail(String master_uuid,String uuid,String type,Long startTime,Long endTime);
    public String getMasterSlaveTable(int buildingType);
    public String getBuidingTable(int buildingType);
}
