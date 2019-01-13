package com.micer.backend.controller;

import com.micer.backend.utils.BusinessException;
import com.micer.backend.utils.JsonResult;
import com.micer.backend.service.BuildingEntityService;
import com.micer.backend.service.EnergyConsumptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BackendController {
    @Autowired
    private BuildingEntityService buildingEntityService;

    @Autowired
    private EnergyConsumptionService energyConsumptionService;

    protected static Logger logger = LoggerFactory.getLogger(BackendController.class);

    @GetMapping("/v1/entity/{uuid}")
    @ResponseBody
    public Object getEntityInfo(@PathVariable String uuid){
        
        Map<String, Object> entityMap = new HashMap<>();
        entityMap = buildingEntityService.getIndexInfo(uuid);

        Object jsonResult = new JsonResult<>(entityMap, "success");

        logger.info("访问/v1/entity/{uuid}");
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
