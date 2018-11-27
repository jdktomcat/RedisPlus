package com.roach.base.dao;

import com.roach.base.bean.Connect;
import com.roach.base.bean.Setting;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据访问接口
 *
 * @author jdktomcat
 */
@Repository
public interface DataMapper {
    /**
     * 建表：t_connect
     */
    @Update("create table t_connect(id varchar(100),text varchar(100),rhost varchar(100),rport varchar(100),rpass varchar(100),sname varchar(100),shost varchar(100),sport varchar(100),spass varchar(100),spkey varchar(900),onssl varchar(1),isha varchar(1),type varchar(1),time varchar(100),primary key (id))")
    void createConnectTable();

    /**
     * 判断表名是否存在
     *
     * @param tableName 表名
     * @return 是否存在（1：存在 0：不存在）
     */
    @Select("SELECT COUNT(T.TABLENAME) FROM SYS.SYSTABLES T, SYS.SYSSCHEMAS S WHERE S.SCHEMANAME = 'APP' AND S.SCHEMAID = T.SCHEMAID AND T.TABLENAME=#{tableName}")
    int isExistsTable(@Param("tableName") String tableName);

    /**
     * 根据连接标识查询连接信息
     *
     * @param id 连接标识
     * @return 连接信息
     */
    @Select("select * from t_connect where id=#{id}")
    Connect selectConnectById(@Param("id") String id);

    /**
     * 查询所有的连接信息
     *
     * @return 所有的连接信息
     */
    @Select("select * from t_connect")
    List<Connect> selectConnect();

    /**
     * 插入连接信息
     *
     * @param connect 连接信息
     * @return 结果
     */
    @Insert("insert into t_connect(id,text,rhost,rport,rpass,sname,shost,sport,spass,spkey,onssl,isha,type,time) values(#{o.id},#{o.text},#{o.rhost},#{o.rport},#{o.rpass},#{o.sname},#{o.shost},#{o.sport},#{o.spass},#{o.spkey},#{o.onssl},#{o.isha},#{o.type},#{o.time})")
    int insertConnect(@Param("o") Connect connect);

    /**
     * 更新连接信息
     *
     * @param connect 连接信息
     * @return 结果
     */
    @Update("update t_connect set text=#{o.text},rhost=#{o.rhost},rport=#{o.rport},rpass=#{o.rpass},sname=#{o.sname},shost=#{o.shost},sport=#{o.sport},spass=#{o.spass},spkey=#{o.spkey},onssl=#{o.onssl},isha=#{o.isha},type=#{o.type},time=#{o.time} where id=#{o.id}")
    int updateConnect(@Param("o") Connect connect);

    /**
     * 删除连接信息
     *
     * @param id 连接标识
     * @return 结果
     */
    @Delete("delete from t_connect where id=#{id}")
    int deleteConnectById(@Param("id") String id);

    /**
     * 创建设置表t_setting
     */
    @Update("create table t_setting(id varchar(100),keys varchar(100),vals varchar(100),primary key (id))")
    void createSettingTable();

    /**
     * 插入设置信息
     *
     * @param setting 设置信息
     */
    @Insert("insert into t_setting(id,keys,vals) values(#{o.id},#{o.keys},#{o.vals})")
    void insertSetting(@Param("o") Setting setting);

    /**
     * 更新设置信息
     *
     * @param setting 设置信息
     * @return 结果
     */
    @Update("update t_setting set vals=#{o.vals} where keys=#{o.keys}")
    int updateSetting(@Param("o") Setting setting);

    /**
     * 查询设置信息
     *
     * @param keys 键值
     * @return 设置信息
     */
    @Select("select * from t_setting where keys=#{keys}")
    Setting selectSetting(@Param("keys") String keys);

}
