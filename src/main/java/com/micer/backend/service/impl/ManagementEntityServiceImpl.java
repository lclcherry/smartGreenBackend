package com.micer.backend.service.impl;

import com.micer.backend.pojo.BuildingEntity;
import com.micer.backend.service.ManagementEntityService;
import com.micer.backend.dao.BuildingEntityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ManagementEntityServiceImpl implements ManagementEntityService{

    @Autowired
    private BuildingEntityDao buildingEntityDao = null;

    @Override
    public int getBuildingType(String uuid) {
        int buildingType = 0;

        //        uuid为5，查询 Project
        if (uuid.length() == 5){
            buildingType = 0;
        } else if (uuid.startsWith("S00012001")){ //uuid前缀为S00012001，查询 Building
            buildingType = 1;
        } else if (uuid.startsWith("S00012002")){ //uuid前缀为S00012002，查询 Floor
            buildingType = 2;
        } else if (uuid.startsWith("S00012003")){ //uuid前缀为S00012003，查询 Room
            buildingType = 3;
        }

        return buildingType;
    }

    @Override
    public BuildingEntity getBuildingEntityInfo(String uuid, int buildingType) {
        BuildingEntity buildingEntity = new BuildingEntity();

        switch (buildingType){
            case 0:
                buildingEntity = buildingEntityDao.getEntityInfo(uuid, "e_project_t");
                break;
            case 1:
                buildingEntity = buildingEntityDao.getEntityInfo(uuid, "e_building_t");
                break;
            case 2:
                buildingEntity = buildingEntityDao.getEntityInfo(uuid, "e_floor_t");
                break;
            case 3:
                buildingEntity = buildingEntityDao.getEntityInfo(uuid, "e_room_t");
                break;
        }

        return buildingEntity;
    }
}
