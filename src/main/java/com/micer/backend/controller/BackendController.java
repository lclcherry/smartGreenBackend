package com.micer.backend.controller;

import com.micer.backend.pojo.BuildingEntity;
import com.micer.backend.utils.BusinessException;
import com.micer.backend.utils.JsonResult;
import com.micer.backend.service.BuildingEntityService;
import com.micer.backend.service.EnergyConsumptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BackendController {
    @Autowired
    private BuildingEntityService buildingEntityService = null;

    @Autowired
    private EnergyConsumptionService energyConsumptionService = null;

    protected static Logger logger = LoggerFactory.getLogger(BackendController.class);

    @GetMapping("/v1/entity/{uuid}")
    @ResponseBody
    public JsonResult<Map<String, Object>> getEntityInfo(@PathVariable String uuid){
        if (uuid.contains("sa")){
            throw new BusinessException("1", "uuid error");
        }
        Map<String, Object> entityMap = new HashMap<>();
        entityMap = buildingEntityService.getIndexInfo(uuid);

        JsonResult<Map<String, Object>> jsonResult = new JsonResult<>(entityMap, "success");

        logger.info("访问/v1/entity/{uuid}");
        return jsonResult;
    }

    @PostMapping("/v2/entity/test")
    @ResponseBody
    public Map<String, Object> justForTest(@RequestBody Map<String, Object> map){
        String uuid = (String) map.get("uuid");
        String energyType = (String) map.get("energyType");
        Integer startTime = (Integer) map.get("startTime");
        Integer endTime = (Integer) map.get("endTime");

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("uuid", uuid);
        returnMap.put("energyType", energyType);
        if(startTime != null)
        returnMap.put("startTime", startTime);
        if(endTime != null)
        returnMap.put("endTime", endTime);

        return returnMap;
    }

    @GetMapping("/v1/entity/test/{uuid}")
    @ResponseBody
    public JsonResult<Map<String, Object>> getEntity(@PathVariable String uuid){
        BuildingEntity buildingEntity = buildingEntityService.getBuildingEntityInfo(uuid, 3);
        Map<Long, Double>[] data = energyConsumptionService.getPeriodEC(uuid, 3,0);
        Map<String, Object> map = new HashMap<>();
        map.put("metaData", buildingEntity);
        map.put("ECdata", data);
        JsonResult<Map<String, Object>> jsonResult = new JsonResult<>(map, "success");
        return jsonResult;
    }

    @GetMapping("/v1/getSlaves/{uuid}")
    @ResponseBody
    public JsonResult<List<String>> getSlavesUuid(@PathVariable String uuid){
        List<String> list = buildingEntityService.getSlavesUuid(uuid);
        JsonResult<List<String>> jsonResult = new JsonResult<>(list, "success");
        return jsonResult;
    }

    @GetMapping("/v1/consumption/{uuid}/{startTime}/{endTime}")
    @ResponseBody
    public Map<String, Object> getEnergyConsumption(@PathVariable String uuid, @PathVariable Long startTime, @PathVariable Long endTime){
        Map<String, Object> map = new HashMap<>();
        Map<Long, Double>[] maps = energyConsumptionService.getFixTimePeriodEC(uuid, startTime, endTime);
        map.put("periodData", maps);
        return map;
    }
}
