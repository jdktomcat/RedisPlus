package com.roach.base.service;

import com.roach.base.bean.Connect;
import com.roach.base.bean.Setting;
import com.roach.base.dao.DataMapper;
import com.roach.tool.DateUtil;
import com.roach.tool.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private DataMapper dataMapper;

    @Override
    public void createConnectTable() {
        this.dataMapper.createConnectTable();
    }

    @Override
    public void createSettingTable() {
        this.dataMapper.createSettingTable();
    }

    @Override
    public int isExistsTable(String tableName) {
        return this.dataMapper.isExistsTable(tableName);
    }

    @Override
    public Connect selectConnectById(String id) {
        return this.dataMapper.selectConnectById(id);
    }

    @Override
    public List<Connect> selectConnect() {
        return this.dataMapper.selectConnect();
    }

    @Override
    public int insertConnect(Connect connect) {
        connect.setId(KeyUtil.getUUIDKey());
        connect.setOnssl("0");
        connect.setSpkey("");
        connect.setTime(DateUtil.formatDateTime(new Date()));
        if ("0".equals(connect.getType())) {
            connect.setSname("--");
        } else {
            connect.setRhost("127.0.0.1");
        }
        return this.dataMapper.insertConnect(connect);
    }

    @Override
    public int updateConnect(Connect connect) {
        connect.setOnssl("0");
        connect.setSpkey("");
        connect.setTime(DateUtil.formatDateTime(new Date()));
        if ("0".equals(connect.getType())) {
            connect.setSname("--");
        } else {
            connect.setRhost("127.0.0.1");
        }
        return this.dataMapper.updateConnect(connect);
    }

    @Override
    public int deleteConnectById(String id) {
        return this.dataMapper.deleteConnectById(id);
    }

    @Override
    public void insertSetting(Setting setting) {
        this.dataMapper.insertSetting(setting);
    }

    @Override
    public Setting selectSetting(String keys) {
        return this.dataMapper.selectSetting(keys);
    }

    @Override
    public int updateSetting(Setting setting) {
        return this.dataMapper.updateSetting(setting);
    }
}
