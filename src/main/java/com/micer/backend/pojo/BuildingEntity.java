package com.micer.backend.pojo;

import org.apache.ibatis.type.Alias;

/**建筑实体
 * 数据库表: e_xxx_t;
 * 字段:
 * id: 数据库表每个记录的标记
 * uuid: 实体唯一标识
 * name: 实体名字
 * desc: 建筑实体描述，可以理解为全称，这个字段数据库中还没有录入信息，暂时没用
 * order: 同一层级的实体的排序
 * */

@Alias("buildingEntity")
public class BuildingEntity {
    private int id;
    private String uuid;
    private String name;
    private String desc;
    private int order;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
