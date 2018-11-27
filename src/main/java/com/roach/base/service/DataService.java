package com.roach.base.service;

import com.roach.base.bean.Connect;
import com.roach.base.bean.Setting;

import java.util.List;

/**
 * 数据服务接口
 *
 * @author jdktomcat
 */
public interface DataService {

    /**
     * 建表：t_connect
     */
    void createConnectTable();

    /**
     * 创建设置表t_setting
     */
    void createSettingTable();

    /**
     * 判断表名是否存在
     *
     * @param tableName 表名
     * @return 是否存在
     */
    int isExistsTable(String tableName);

    /**
     * 根据连接标识查询连接信息
     *
     * @param id 连接标识
     * @return 连接信息
     */
    Connect selectConnectById(String id);

    /**
     * 查询所有的连接信息
     *
     * @return 所有的连接信息
     */
    List<Connect> selectConnect();

    /**
     * 插入连接信息
     *
     * @param connect 连接信息
     * @return 结果
     */
    int insertConnect(Connect connect);

    /**
     * 更新连接信息
     *
     * @param connect 连接信息
     * @return 结果
     */
    int updateConnect(Connect connect);

    /**
     * 删除连接信息
     *
     * @param id 连接标识
     * @return 结果
     */
    int deleteConnectById(String id);

    /**
     * 查询设置信息
     *
     * @param keys 键值
     * @return 设置信息
     */
    Setting selectSetting(String keys);

    /**
     * 插入设置信息
     *
     * @param setting 设置信息
     */
    void insertSetting(Setting setting);

    /**
     * 更新设置信息
     *
     * @param setting 设置信息
     * @return 结果
     */
    int updateSetting(Setting setting);

}
