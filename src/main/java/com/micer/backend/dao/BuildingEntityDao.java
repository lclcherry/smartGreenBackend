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
     * 动态传入uuid，table（表名）
     * 相关数据库表: e_room_t/e_floor_t/e_building_t/e_project_t
     * 返回: BuildingEntity
     * */
    public BuildingEntity getEntityInfo(@Param("uuid") String uuid, @Param("table") String table);

    /**
     * 获取实体上级建筑的uuid(master_uuid)
     * 动态传入uuid, table
     * 相关数据库表: r_project_building_t/r_building_floor_t/r_floor_room_t
     * 返回: master_uuid
     * */
    public String getMasterUuid(@Param("uuid") String uuid, @Param("table") String table);

    /**
     * 获取实体下级建筑的uuid(slave_uuid)
     * 动态传入uuid， table
     * 相关数据库表: r_project_building_t/r_building_floor_t/r_floor_room_t
     * 返回: 以List格式存储的多个slave_uuid
     * */
    public List<String> getSlavesUuid(@Param("uuid") String uuid, @Param("table") String table);
}
