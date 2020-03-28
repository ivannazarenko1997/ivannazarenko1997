package com.wowapp.game.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wowapp.game.dto.User ;
import com.wowapp.game.repository.IGameDataSourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  GameDataSourceRepositoryImpl class contains
 functionality for retrieve metadata and user info from  local Data Source
 */
@Repository("dataRepository")
public class GameDataSourceRepositoryImpl implements IGameDataSourceRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameDataSourceRepositoryImpl.class.getName());
    private static final String DATASOURCE_FILE_NAME = "datasource.txt";
    private static final String METADATA_FILE_NAME = "metadata.txt";
    private static final String CHARSET = "UTF-8";
    private static final String ERROR_FILE_LOADING = " Can not initialize data from file ";
    private static final String ERROR_FILE_SAVING = " Can not save data to file ";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private Map<String, User> mapUserInfo = new ConcurrentHashMap<>();
    private Map<String, String> mapMetadataInfo = new ConcurrentHashMap<>();

    /* load metadata and user info Data Source  files on application startup*/
    @PostConstruct
    public void initializeDataSourceInfo() {
        LOGGER.info("<< initializeDataSourceInfo");
        loadUserInfo();
        loadMetaDataInfo();
    }

    public Boolean saveDataOnGameClose() {
        LOGGER.info("<< saveDataOnGameClose");
        try {
            Path resourceDirectory = Paths.get("src", "main", "resources");
            File fileDataSource = new File(resourceDirectory.toAbsolutePath() + "/" + DATASOURCE_FILE_NAME);
            OBJECT_MAPPER.writeValue(fileDataSource, mapUserInfo);

            return true;
        } catch (Exception e) {
            LOGGER.debug(ERROR_FILE_SAVING);
            return false;
        }
    }

    private void loadUserInfo() {
        LOGGER.info("<< loadDatasourceInfo");
        try {
            if (getFileContent(DATASOURCE_FILE_NAME).length() > 0) {
                mapUserInfo = OBJECT_MAPPER.readValue(getFileContent(DATASOURCE_FILE_NAME),
                        new TypeReference<ConcurrentHashMap<String, User>>() {
                        });
            }
        } catch (IOException e) {
            LOGGER.debug(ERROR_FILE_LOADING + DATASOURCE_FILE_NAME);
        }
    }

    private String getFileContent(String fileName) {
        LOGGER.info("<< getFileContent");
        try {
            byte[] byteResources = StreamUtils.copyToByteArray(new ClassPathResource(fileName).getInputStream());
            return new String(byteResources, Charset.forName(CHARSET));
        } catch (IOException e) {
            LOGGER.debug(ERROR_FILE_LOADING + fileName);
        }
        return new String();
    }

    private void loadMetaDataInfo() {
        LOGGER.info("<< loadMetaDataInfo");
        try {
            mapMetadataInfo = OBJECT_MAPPER.readValue(getFileContent(METADATA_FILE_NAME),
                    new TypeReference<ConcurrentHashMap<String, String>>() {
                    });
        } catch (IOException e) {
            LOGGER.info(ERROR_FILE_LOADING + METADATA_FILE_NAME);
        }
    }

    @Override
    @Transactional
    public Map<String,User> getDataSourceUserMap() {
        return mapUserInfo;
    }

    @Override
    @Transactional
    public Map<String,String> getMetaDataMap() {
        return mapMetadataInfo;
    }
}
