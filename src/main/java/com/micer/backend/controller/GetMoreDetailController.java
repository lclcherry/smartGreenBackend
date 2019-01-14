package com.micer.backend.controller;

import com.micer.backend.service.GetMoreDetailService;
import com.micer.backend.utils.JsonResult;
import org.apache.ibatis.annotations.Param;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.util.Map;
import java.util.logging.Logger;

@Controller
public class GetMoreDetailController {
    protected org.slf4j.Logger logger = LoggerFactory.getLogger(GetMoreDetailController.class);

    @Autowired
    GetMoreDetailService getMoreDetailService = null;

    @PostMapping("/v1/entity/moreDetails")
    @ResponseBody
    public JsonResult getMoreDetail(@RequestBody Map<String,Object> rb) {
        String master_uuid = rb.get("master_uuid").toString();
        String uuid = rb.get("uuid").toString();
        String type = rb.get("type").toString();
        Long startTime = ((Integer) rb.get("startTime")).longValue();
        Long endTime = ((Integer) rb.get("endTime")).longValue();
        logger.info("MoreDetail数据:master_uuid[{}],uuid[{}],type[{}],startTime[{}],endTime[{}]",master_uuid,uuid,type,startTime,endTime);
        JsonResult result = new JsonResult("201","");
        if(master_uuid == "") {
            result.setMsg(MessageFormat.format("上层实体为空，无法获取uuid:{0}的同一层级实体数据",uuid));
            return result;
        }
        if(uuid == "" || type == "" || startTime == null || endTime == null) {
            result.setMsg("数据不全，无法查询");
            return result;
        }

        result = getMoreDetailService.getMoreDetail(master_uuid,uuid,type,startTime,endTime);
        return result;
    }

}
