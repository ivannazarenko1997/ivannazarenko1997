package com.wowapp.game.repository;

import com.wowapp.game.dto.User;
import java.util.Map;

/**
 * IGameDataSourceRepository - class contains
 *  functionality for retrive metadata and user info from  local Data Source
 */
public interface IGameDataSourceRepository {
    Boolean saveDataOnGameClose();

    Map<String, User> getDataSourceUserMap();

    Map<String, String> getMetaDataMap();
}
