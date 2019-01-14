package com.micer.backend.service.impl;

import com.micer.backend.dao.BuildingEntityDao;
import com.micer.backend.pojo.BuildingEntity;
import com.micer.backend.service.BuildingEntityService;
import com.micer.backend.service.EnergyConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BuildingEntityServiceImpl implements BuildingEntityService {
//    @Autowired
//    private BuildingEntityDao buildingEntityDao;
//
//    @Autowired
//    private EnergyConsumptionService energyConsumptionService;
//
//    @Override
//    public int getBuildingType(String uuid){
//        int buildingType = 0;
//
//        //        uuid为5，查询 Project
//        if (uuid.length() == 5){
//            buildingType = 0;
//        } else if (uuid.startsWith("S00012001")){ //uuid前缀为S00012001，查询 Building
//            buildingType = 1;
//        } else if (uuid.startsWith("S00012002")){ //uuid前缀为S00012002，查询 Floor
//            buildingType = 2;
//        } else if (uuid.startsWith("S00012003")){ //uuid前缀为S00012003，查询 Room
//            buildingType = 3;
//        }
//
//        return buildingType;
//    }
//
//    @Override
//    public Map<String, Object> getIndexInfo(String uuid){
//        Map<String, Object> map = new HashMap<>();
//        int buildingType = getBuildingType(uuid);
//        BuildingEntity buildingEntity = null;
//        Map<String, Object> superStructure = null;
//        Map<String, Object> datas = null;
//
//        buildingEntity = getBuildingEntityInfo(uuid, buildingType);
//        superStructure = getSuperStructure(uuid, buildingType);
//        datas = energyConsumptionService.getAllPeriodEC(uuid);
//
//        map.put("type", "buildingEntity");
//        map.put("id", buildingEntity.getId());
//        map.put("uuid", uuid);
//        map.put("name", buildingEntity.getName());
//        map.put("superStructure", superStructure);
//        map.put("EC", datas);
//
//        return map;
//    }
//
//    @Override
//    public BuildingEntity getBuildingEntityInfo(String uuid, int buildingType){
//        BuildingEntity buildingEntity = new BuildingEntity();
//
//        switch (buildingType){
//            case 0:
//                buildingEntity = buildingEntityDao.getEntityInfo(uuid, "e_project_t");
//                break;
//            case 1:
//                buildingEntity = buildingEntityDao.getEntityInfo(uuid, "e_building_t");
//                break;
//            case 2:
//                buildingEntity = buildingEntityDao.getEntityInfo(uuid, "e_floor_t");
//                break;
//            case 3:
//                buildingEntity = buildingEntityDao.getEntityInfo(uuid, "e_room_t");
//                break;
//        }
//
//        return buildingEntity;
//    }
//
//    @Override
//    public Map<String, Object> getSuperStructure(String uuid, int buildingType){
//        Map<String, Object> map = new HashMap<>();
//
//        switch (buildingType){
//            case 0:
//                map.put("project", null);
//                map.put("building", null);
//                map.put("floor", null);
//                break;
//            case 1:
//                Map<String, Object> projectMap = new HashMap<>();
//                String project_uuid = buildingEntityDao.getMasterUuid(uuid, "r_project_building_t");
//                BuildingEntity project = getBuildingEntityInfo(project_uuid, 0);
//                projectMap.put("id", project.getId());
//                projectMap.put("uuid", project.getUuid());
//                projectMap.put("name", project.getName());
//                map.put("project", projectMap);
//                map.put("building", null);
//                map.put("floor", null);
//                break;
//            case 2:
//                Map<String, Object> projectMap_1 = new HashMap<>();
//                Map<String, Object> buildingMap_1 = new HashMap<>();
//                //获取floor上级building信息
//                String building_uuid_1 = buildingEntityDao.getMasterUuid(uuid, "r_building_floor_t");
//                BuildingEntity building_1 = getBuildingEntityInfo(building_uuid_1, 1);
//                buildingMap_1.put("id", building_1.getId());
//                buildingMap_1.put("uuid", building_1.getUuid());
//                buildingMap_1.put("name", building_1.getName());
//                //获取building上级project信息
//                String project_uuid_1 = buildingEntityDao.getMasterUuid(building_uuid_1, "r_project_building_t");
//                BuildingEntity project_1 = getBuildingEntityInfo(project_uuid_1, 0);
//                projectMap_1.put("id", project_1.getId());
//                projectMap_1.put("uuid", project_1.getUuid());
//                projectMap_1.put("name", project_1.getName());
//
//                map.put("project", projectMap_1);
//                map.put("building", buildingMap_1);
//                map.put("floor", null);
//                break;
//            case 3:
//                Map<String, Object> projectMap_2 = new HashMap<>();
//                Map<String, Object> buildingMap_2 = new HashMap<>();
//                Map<String, Object> floorMap_2 = new HashMap<>();
//                //获取room上级floor的信息
//                String floor_uuid_2 = buildingEntityDao.getMasterUuid(uuid, "r_floor_room_t");
//                BuildingEntity floor_2 = getBuildingEntityInfo(floor_uuid_2, 2);
//                floorMap_2.put("id", floor_2.getId());
//                floorMap_2.put("uuid", floor_2.getUuid());
//                floorMap_2.put("name", floor_2.getName());
//                //获取floor上级building的信息
//                String building_uuid_2 = buildingEntityDao.getMasterUuid(floor_uuid_2, "r_building_floor_t");
//                BuildingEntity building_2 = getBuildingEntityInfo(building_uuid_2, 1);
//                buildingMap_2.put("id", building_2.getId());
//                buildingMap_2.put("uuid", building_2.getUuid());
//                buildingMap_2.put("name", building_2.getName());
//                //获取building上级project的信息
//                String project_uuid_2 = buildingEntityDao.getMasterUuid(building_uuid_2, "r_project_building_t");
//                BuildingEntity project_2 = getBuildingEntityInfo(project_uuid_2, 0);
//                projectMap_2.put("id", project_2.getId());
//                projectMap_2.put("uuid", project_2.getUuid());
//                projectMap_2.put("name", project_2.getName());
//
//                map.put("project", projectMap_2);
//                map.put("building", buildingMap_2);
//                map.put("floor", floorMap_2);
//                break;
//        }
//        return map;
//    }
//
//    public List<String> getSlavesUuid(String uuid){
//        int buildingType = getBuildingType(uuid);
//        List<String> slavesUuidList = new ArrayList<>();
//        switch (buildingType){
//            case 3:
//                break;
//            case 2:
//                slavesUuidList = buildingEntityDao.getSlavesUuid(uuid, "r_floor_room_t");
//                break;
//            case 1:
//                slavesUuidList = buildingEntityDao.getSlavesUuid(uuid, "r_building_floor_t");
//                break;
//            case 0:
//                slavesUuidList = buildingEntityDao.getSlavesUuid(uuid, "r_project_building_t");
//                break;
//        }
//        return slavesUuidList;
//    }

}
