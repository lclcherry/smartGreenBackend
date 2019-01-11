package com.micer.backend.pojo;

import org.apache.ibatis.type.Alias;

/**能耗信息
 * 数据库表: d_xxx_xxx_t
 * 字段:
 * id: 数据库表中数据的标记
 * uuid: 实体唯一标识
 * value: 该时间点能耗
 * run_at: 时间点
 * */

@Alias("energyConsumption")
public class EnergyConsumption {
    private String uuid;
    private double value;
    private long run_at;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getRun_at() {
        return run_at;
    }

    public void setRun_at(long run_at) {
        this.run_at = run_at;
    }
}
