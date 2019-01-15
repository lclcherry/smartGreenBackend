package com.micer.backend.controller;

import com.micer.backend.entity.Result;
import com.micer.backend.service.EntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
        return  entityService.getEntityInfo(uuid);
    }
    
    @PostMapping("/details")
    public Result getDetails(@RequestBody Map<String, Object> requestParams)
    {
        String uuid = (String) requestParams.get("uuid");
        String type = (String) requestParams.get("type");
        Long startTime = (Long) requestParams.get("startTime");
        Long endTime = (Long) requestParams.get("endTime");
        return entityService.getDetails(uuid, type, startTime, endTime);
    }
    
    @PostMapping("/moreDetails")
    public Result getMoreDetail_0(@RequestBody Map<String, Object> rb)
    {
        String master_uuid = rb.get("master_uuid").toString();
        String uuid = rb.get("uuid").toString();
        String type = rb.get("type").toString();
        Long startTime = (Long) rb.get("startTime");
        Long endTime = (Long) rb.get("endTime");
        logger.info("MoreDetail数据:master_uuid[{}],uuid[{}],type[{}],startTime[{}],endTime[{}]", master_uuid, uuid, type, startTime, endTime);
        if (StringUtils.isEmpty(master_uuid))
        {
            return Result.BadRequest().msg("上层实体为空，无法获取uuid:{uuid}的同一层级实体数据").build();
        }
        if (StringUtils.isEmpty(uuid) || StringUtils.isEmpty(type) || startTime == null || endTime == null)
        {
            return Result.BadRequest().msg("数据不全，无法查询").build();
        }
        
        return entityService.getMoreDetail(master_uuid, uuid, type, startTime, endTime);
    }
    
    // 用SpringBoot提供的参数验证机制来检验参数，而不是上面那种写很多业务逻辑的判断
    // 这段代码先别删吧，暂时留着
//    @GetMapping("/moreDetails")
//    @ResponseBody
//    public Result getMoreDetail(@Size(min = 5, max = 13, message = "uuid长度为5或13")
//                                @RequestParam("master_uuid") String master_uuid,
//                                @Size(min = 5, max = 13, message = "uuid长度为5或13")
//                                @RequestParam("uuid") String uuid,
//                                @NotBlank @RequestParam("type") String type,
//                                @NotNull @RequestParam("startTime") Long startTime,
//                                @NotNull @RequestParam("endTime") Long endTime)
//    {
//        logger.info("MoreDetail数据:master_uuid[{}],uuid[{}],type[{}],startTime[{}],endTime[{}]", master_uuid, uuid, type, startTime, endTime);
//        return entityService.getMoreDetail(master_uuid, uuid, type, startTime, endTime);
//    }
    
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
