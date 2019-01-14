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
     * @param uuid 实体唯一标识
     * @param d_entity_time_t 能耗数据表
     * #{entity} ∈ {"project", "building", "floor", "room"}
     * #{time} ∈ {"hour", "day", "week", "month", "year"}
     * d_#{entity}_week_t(暂时还没有)
     * @return List<Map<String, Number>>
     * {
     *     "run_at": xxx,
     *     "value": xxx
     * }
     * */
    public List<Map<String, Number>> getPeriodEC(@Param("uuid") String uuid, @Param("d_entity_time_t") String d_entity_time_t);

    /**
     * 获取实体从开始日期到终止日期的能耗数据
     * @param uuid 实体唯一标识
     * @param d_entity_time_t 能耗数据表的表名
     * #{entity} ∈ {"project", "building", "floor", "room"}
     * #{time} ∈ {"hour", "day", "week", "month", "year"}
     * d_#{entity}_week_t(暂时还没有)
     * @return List<Map<Long, Double>>
     * */
    public List<Map<String, Number>> getFixTimePeriodEC(@Param("uuid") String uuid, @Param("d_entity_time_t") String d_entity_time_t,
                                    @Param("startTime") Long startTime, @Param("endTime") Long endTime);
    
    /**
     * 获取实体从开始日期到终止日期的能耗数据的值
     * @param uuid 实体唯一标识
     * @param d_entity_time_t 能耗数据表的表名
     * #{entity} ∈ {"project", "building", "floor", "room"}
     * #{time} ∈ {"hour", "day", "week", "month", "year"}
     * d_#{entity}_week_t(暂时还没有)
     * @return List<Double>
     * */
//    public List<Double> getFixTimePeriodECValues(@Param("uuid") String uuid, @Param("d_entity_time_t") String d_entity_time_t,
//                                                        @Param("startTime") Long startTime, @Param("endTime") Long endTime);
    
    /**
     * 获取实体某一特定时间维度下的最新数据
     * @param uuid 实体标识
     * @param d_entity_time_t 能耗数据表
     * @return Number
     */
    Number getMostRecentEC(@Param("uuid") String uuid, @Param("d_entity_time_t") String d_entity_time_t);
    
    /**
     * 获取实体某一特定时间维度下的最新两个数值，即value和last_value 同比
     * @param uuid
     * @param d_entity_time_t
     * @return List<Map<String, Number>> 注意list的size取值有{0, 1, 2}三种情况
     */
    List<Map<String, Number>> getTop2EC(@Param("uuid") String uuid, @Param("d_entity_time_t") String d_entity_time_t);
    
    /**
     * 获取某一特定时间点表示的时间段能耗数据，用于获取[同比]和[环比]数据
     * @param uuid
     * @param d_entity_time_t
     * @param run_at
     * @return
     */
    Double getValueAt(@Param("uuid")String uuid, @Param("d_entity_time_t")String d_entity_time_t,
                      @Param("run_at") Long run_at);
}
