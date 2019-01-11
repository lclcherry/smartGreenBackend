package com.micer.backend.pojo;

import org.apache.ibatis.type.Alias;

/**建筑实体关系
 * 数据库表: r_xxx_xxx_t
 * 字段:
 * id: 数据库表中每个数据的标记
 * master_uuid: 主实体uuid
 * slave_uuid: 从实体uuid
 * */

@Alias("entityRelation")
public class EntityRelation {
    private int id;
    private String master_uuid;
    private String slave_uuid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaster_uuid() {
        return master_uuid;
    }

    public void setMaster_uuid(String master_uuid) {
        this.master_uuid = master_uuid;
    }

    public String getSlave_uuid() {
        return slave_uuid;
    }

    public void setSlave_uuid(String slave_uuid) {
        this.slave_uuid = slave_uuid;
    }
}
