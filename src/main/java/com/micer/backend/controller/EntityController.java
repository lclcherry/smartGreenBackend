package com.micer.backend.controller;

import com.micer.backend.entity.Result;
import com.micer.backend.service.EntityService;
import com.micer.backend.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;

@RestController
@RequestMapping("/v1/entity")
public class EntityController
{
    //    @Autowired
    //    private BuildingEntityService buildingEntityService;
    //
    //    @Autowired
    //    private EnergyConsumptionService energyConsumptionService;
    
    @Autowired
    private EntityService entityService;
    
    protected static Logger logger = LoggerFactory.getLogger(EntityController.class);
    
    @GetMapping("/{uuid}")
    public Result getEntityInfo(@PathVariable String uuid)
    {
        logger.info("访问/v1/entity/{uuid}");
//        return entityService.getEntityInfo(uuid);
        Result result =  entityService.getEntityInfo(uuid);
        return result;
    }
    
    @PostMapping("/details")
    public Result getDetails(@RequestBody Map<String, Object> requestParams)
    {
        String uuid = (String) requestParams.get("uuid");
        String type = (String) requestParams.get("type");
//        Long startTime = ((Integer) requestParams.get("startTime")).longValue();
//        Long endTime = ((Integer) requestParams.get("endTime")).longValue();
        Long startTime = Long.parseLong((String)requestParams.get("startTime"));
        Long endTime = Long.parseLong((String)requestParams.get("endTime"));
//        return entityService.getDetails(uuid, type, startTime, endTime);
        Result result = entityService.getDetails(uuid, type, startTime, endTime);
        return result;
    }
    
    @PostMapping("/moreDetails")
    public Result getMoreDetail_0(@RequestBody Map<String, Object> rb)
    {
        String master_uuid = rb.get("master_uuid").toString();
        String uuid = rb.get("uuid").toString();
        String type = rb.get("type").toString();
//        Long startTime = ((Integer) rb.get("startTime")).longValue();
//        Long endTime = ((Integer) rb.get("endTime")).longValue();
        Long startTime = Long.parseLong((String)rb.get("startTime"));
        Long endTime = Long.parseLong((String)rb.get("endTime"));
        logger.info("MoreDetail数据:master_uuid[{}],uuid[{}],type[{}],startTime[{}],endTime[{}]", master_uuid, uuid, type, startTime, endTime);
        Result result = new Result(201, "");
        if (StringUtil.isEmpty(master_uuid))
        {
            result.setMsg("上层实体为空，无法获取uuid:{uuid}的同一层级实体数据");
            return result;
        }
        if (StringUtil.isEmpty(uuid) || StringUtil.isEmpty(type) || startTime == null || endTime == null)
        {
            result.setMsg("数据不全，无法查询");
            return result;
        }
        
        return entityService.getMoreDetail(master_uuid, uuid, type, startTime, endTime);
    }
    
    @GetMapping("/moreDetails")
    @ResponseBody
    public Result getMoreDetail(@Size(min = 5, max = 13, message = "uuid长度为5或13")
                                @RequestParam("master_uuid") String master_uuid,
                                @Size(min = 5, max = 13, message = "uuid长度为5或13")
                                @RequestParam("uuid") String uuid,
                                @NotBlank @RequestParam("type") String type,
                                @NotNull @RequestParam("startTime") Long startTime,
                                @NotNull @RequestParam("endTime") Long endTime)
    {
        logger.info("MoreDetail数据:master_uuid[{}],uuid[{}],type[{}],startTime[{}],endTime[{}]", master_uuid, uuid, type, startTime, endTime);
        return entityService.getMoreDetail(master_uuid, uuid, type, startTime, endTime);
    }
    
    //    @GetMapping("/v1/getSlaves/{uuid}")
    //    @ResponseBody
    //    public JsonResult<List<String>> getSlavesUuid(@PathVariable String uuid){
    //        List<String> list = buildingEntityService.getSlavesUuid(uuid);
    //        JsonResult<List<String>> jsonResult = new JsonResult<>(list, "success");
    //        return jsonResult;
    //    }
    //
    //    @GetMapping("/v1/consumption/{uuid}/{startTime}/{endTime}")
    //    @ResponseBody
    //    public Map<String, Object> getEnergyConsumption(@PathVariable String uuid, @PathVariable Long startTime, @PathVariable Long endTime){
    //        Map<String, Object> map = new HashMap<>();
    //        Map<Long, Double>[] maps = energyConsumptionService.getFixTimePeriodEC(uuid, startTime, endTime);
    //        map.put("periodData", maps);
    //        return map;
    //    }
}
