package com.roach.base.service;

import com.roach.base.bean.Connect;
import com.roach.base.bean.Setting;

import java.util.List;

public interface DataService {

    void createConnectTable();

    void createSettingTable();

    int isExistsTable(String tableName);

    Connect selectConnectById(String id);

    List<Connect> selectConnect();

    int insertConnect(Connect obj);

    int updateConnect(Connect obj);

    int deleteConnectById(String id);

    Setting selectSetting(String keys);

    void insertSetting(Setting setting);

    int updateSetting(Setting setting);

}
