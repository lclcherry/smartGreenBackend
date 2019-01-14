package com.micer.backend.dao;

import com.micer.backend.pojo.EnergyConsumption;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EnergyConsumptionDao {

    /**
     * 获取实体从当前(最新)时刻往前一个周期(一般为7个小时，7天......)的能耗数据
     * 相关数据库表: d_#{entity}_#{time}_t
     * #{entity} ∈ {"project", "building", "floor", "room"}
     * #{time} ∈ {"hour", "day", "week", "month", "year"}
     * d_#{entity}_week_t(暂时还没有)
     * @param uuid 实体唯一标识
     * @param d_entity_time_t 能耗数据表的表名
     * @return List<Map<String, Number>>
     * {
     *     "run_at": xxx,
     *     "value": xxx
     * }
     * */
    public List<Map<String, Number>> getPeriodEC(@Param("uuid") String uuid, @Param("table") String d_entity_time_t);

    /**
     * 获取实体从开始日期到终止日期的能耗数据
     * 相关数据库表: d_#{entity}_#{time}_t
     * #{entity} ∈ {"project", "building", "floor", "room"}
     * #{time} ∈ {"hour", "day", "week", "month", "year"}
     * d_#{entity}_week_t(暂时还没有)
     * @param uuid 实体唯一标识
     * @param d_entity_time_t 能耗数据表的表名
     * @return List<Map<Long, Double>>
     * */
    public List<Map<String, Number>> getFixTimePeriodEC(@Param("uuid") String uuid, @Param("table") String d_entity_time_t,
                                    @Param("startTime") Long startTime, @Param("endTime") Long endTime);
    
    
    Number getMostRecentEC(@Param("uuid") String uuid, @Param("table") String d_entity_time_t);
    
    List<Number> getTop2EC(@Param("uuid") String uuid, @Param("table") String d_entity_time_t);
    
    List<Double> getTopTwoEC(@Param("uuid") String uuid, @Param("table") String d_entity_time_t);
}
