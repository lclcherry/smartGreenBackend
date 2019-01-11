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
     * 相关数据库表: d_room_hour_t, d_room_day_t, d_room_week_t(暂时还没有), d_room_month_t, d_room_year_t, d_floor_hour_t ......
     * @param uuid 实体唯一标识
     * @param table 表名
     * @return List<Map<Long, Double>>
     * {
     *     "run_at": xxx,
     *     "value": xxx
     * }
     * */
    public List<Map<Long, Double>> getPeriodEC(@Param("uuid") String uuid, @Param("table") String table);

    /**
     * 获取实体从开始日期到终止日期的能耗数据
     * 相关数据库表: d_room_hour_t, d_room_day_t, d_room_week_t(暂时还没有), d_room_month_t, d_room_year_t, d_floor_hour_t ......
     * @param uuid 实体唯一标识
     * @param table 表名
     * @return List<Map<Long, Double>>
     * */
    public List<Map<Long, Double>> getFixTimePeriodEC(@Param("uuid") String uuid, @Param("table") String table,
                                    @Param("startTime") Long startTime, @Param("endTime") Long endTime);
}
