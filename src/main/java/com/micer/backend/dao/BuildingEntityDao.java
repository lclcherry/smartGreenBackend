package com.micer.backend.dao;

import com.micer.backend.pojo.BuildingEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 建筑实体DAO层
 * */
@Repository
public interface BuildingEntityDao {
    /**
     * 获取实体信息
     * @param uuid 实体唯一标识
     * @param e_entity_t 表名 ∈ {e_room_t, e_floor_t, e_building_t, e_project_t}
     * @return BuildingEntity 建筑实体信息
     * */
    public BuildingEntity getEntityInfo(@Param("uuid") String uuid, @Param("e_entity_t") String e_entity_t);

    /**
     * 获取实体上级建筑的uuid(master_uuid)
     * 相关数据库表: r_project_building_t/r_building_floor_t/r_floor_room_t
     * @param slave_uuid 实体唯一标识
     * @param r_master_slave_t 表名
     * @return String 上级建筑uuid
     * */
    public String getMasterUuid(@Param("slave_uuid") String slave_uuid, @Param("r_master_slave_t") String r_master_slave_t);

    /**
     * 获取实体下级建筑的uuid(master_uuid)
     * 相关数据库表: r_project_building_t/r_building_floor_t/r_floor_room_t
     * @param master_uuid 实体唯一标识
     * @param r_master_slave_t 表名
     * @return List<String> 下级建筑uuid
     * */
    public List<String> getSlavesUuid(@Param("master_uuid") String master_uuid, @Param("r_master_slave_t") String r_master_slave_t);
    
    /**
     * 获取实体的上级建筑实体的信息
     * 为了避免访问两次数据库，所以就单独写出来了
     * @param slave_uuid
     * @param r_master_slave_t 主从实体关系表
     * @param e_master_t
     * @return BuildingEntity 主实体的信息
     */
    public BuildingEntity getMasterEntityInfo(@Param("slave_uuid") String slave_uuid,
                                              @Param("r_master_slave_t") String r_master_slave_t,
                                              @Param("e_master_t") String e_master_t);
}
