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
     * 相关数据库表: e_room_t/e_floor_t/e_building_t/e_project_t
     * @param uuid 实体唯一标识
     * @param e_entity_t 表名
     * @return BuildingEntity 建筑实体信息
     * */
    public BuildingEntity getEntityInfo(@Param("uuid") String uuid, @Param("table") String e_entity_t);

    /**
     * 获取实体上级建筑的uuid(master_uuid)
     * 相关数据库表: r_project_building_t/r_building_floor_t/r_floor_room_t
     * @param uuid 实体唯一标识
     * @param table 表名
     * @return String 上级建筑uuid
     * */
    public String getMasterUuid(@Param("uuid") String uuid, @Param("table") String table);

    /**
     * 获取实体下级建筑的uuid(master_uuid)
     * 相关数据库表: r_project_building_t/r_building_floor_t/r_floor_room_t
     * @param uuid 实体唯一标识
     * @param table 表名
     * @return List<String> 下级建筑uuid
     * */
    public List<String> getSlavesUuid(@Param("uuid") String uuid, @Param("table") String table);
    
    /**
     * 获取实体的上级建筑实体的信息
     * 为了避免访问两次数据库，所以就单独写出来了
     * @param slave_uuid
     * @param relation_table
     * @param master_table
     * @return
     */
    public BuildingEntity getMasterEntityInfo(@Param("slave_uuid") String slave_uuid,
                                              @Param("relation_table") String relation_table,
                                              @Param("master_table") String master_table);
}
