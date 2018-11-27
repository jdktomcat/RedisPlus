package com.roach.base.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.roach.base.bean.Connect;
import com.roach.base.service.DataService;
import com.roach.core.desktop.Desktop;
import com.roach.tool.*;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.io.File;
import java.util.Date;
import java.util.List;

import static com.roach.base.bean.ResultInfo.*;
import static com.roach.core.desktop.Desktop.setEndsViewImage;
import static com.roach.core.desktop.Desktop.setEndsViewTitle;
import static com.roach.tool.ClusterUtil.getMasterSelf;
import static com.roach.tool.DataUtil.*;


@Component
public class ConnectController {

    @Autowired
    private DataService dataService;

    /**
     * 查询连接列表
     */
    public String selectConnect() {
        System.out.println("查询连接数据中...");
        return JSON.toJSONString(this.dataService.selectConnect());
    }

    /**
     * 新增连接数据
     */
    public String insertConnect(String json) {
        try {
            JSONObject data = JSON.parseObject(json);
            Connect connect = new Connect();
            connect.setText(data.getString("text"));
            connect.setType(data.getString("type"));
            connect.setIsha(data.getString("isha"));
            connect.setRhost(data.getString("rhost"));
            connect.setRport(data.getString("rport"));
            connect.setRpass(data.getString("rpass"));
            connect.setShost(data.getString("shost"));
            connect.setSport(data.getString("sport"));
            connect.setSpass(data.getString("sname"));
            connect.setSpass(data.getString("spass"));
            int insFlag = this.dataService.insertConnect(connect);
            if (insFlag == 1) {
                return getOkByJson("新增连接成功");
            } else {
                return getNoByJson("新增连接失败");
            }
        } catch (Exception e) {
            return exception(e);
        }
    }

    /**
     * 更新连接数据
     */
    public String updateConnect(String json) {
        try {
            JSONObject data = JSON.parseObject(json);
            Connect connect = new Connect();
            connect.setId(data.getString("id"));
            connect.setText(data.getString("text"));
            connect.setType(data.getString("type"));
            connect.setIsha(data.getString("isha"));
            connect.setRhost(data.getString("rhost"));
            connect.setRport(data.getString("rport"));
            connect.setRpass(data.getString("rpass"));
            connect.setShost(data.getString("shost"));
            connect.setSport(data.getString("sport"));
            connect.setSpass(data.getString("sname"));
            connect.setSpass(data.getString("spass"));
            int updFlag = this.dataService.updateConnect(connect);
            if (updFlag == 1) {
                return getOkByJson("修改连接成功");
            } else {
                return getNoByJson("修改连接失败");
            }
        } catch (Exception e) {
            return exception(e);
        }
    }

    /**
     * 删除连接数据
     */
    public String deleteConnect(String id) {
        try {
            int delFlag = this.dataService.deleteConnectById(id);
            if (delFlag == 1) {
                return getOkByJson("删除连接成功");
            } else {
                return getNoByJson("删除连接失败");
            }
        } catch (Exception e) {
            return exception(e);
        }
    }

    /**
     * 查询连接数据
     */
    public String querysConnect(String id) {
        return JSON.toJSONString(this.dataService.selectConnectById(id));
    }


    /**
     * 打开连接数据
     */
    public String createConnect(String id) {
        try {
            Connect connect = this.dataService.selectConnectById(id);
            if ("1".equals(connect.getType())) {
                JschUtil.openSSH(connect);
            }
            if (connect.getIsha().equals("0")) {
                Jedis jedis = RedisUtil.openJedis(connect);
                if (null != jedis) {
                    //关闭原来连接
                    Jedis oldJedis = getCurrentJedisObject();
                    if (null != oldJedis && oldJedis.isConnected()) {
                        oldJedis.close();
                    }
                    JedisCluster oldCluster = getJedisClusterObject();
                    if (null != oldCluster) {
                        oldCluster.close();
                    }
                    DataUtil.setConfig("currentOpenConnect", connect);
                    DataUtil.setConfig("currentJedisObject", jedis);
                    setEndsViewTitle(ItemUtil.DESKTOP_STATUS_OK + connect.getText(), "ok");
                    setEndsViewImage(ItemUtil.DESKTOP_STATUS_IMAGE_OK);
                    return getOkByJson("打开连接成功");
                } else {
                    return getNoByJson("打开连接失败");
                }
            } else {
                JedisCluster cluster = ClusterUtil.openCluster(connect);
                if (null == cluster || cluster.getClusterNodes().size() == 0) {
                    return getNoByJson("打开连接失败");
                } else {
                    //关闭原来连接
                    Jedis oldJedis = getCurrentJedisObject();
                    if (null != oldJedis && oldJedis.isConnected()) {
                        oldJedis.close();
                    }
                    JedisCluster oldCluster = getJedisClusterObject();
                    if (null != oldCluster) {
                        oldCluster.close();
                    }
                    DataUtil.setConfig("currentOpenConnect", connect);
                    DataUtil.setConfig("jedisClusterObject", cluster);
                    DataUtil.setConfig("currentJedisObject", getMasterSelf());
                    setEndsViewTitle(ItemUtil.DESKTOP_STATUS_OK + connect.getText(), "ok");
                    setEndsViewImage(ItemUtil.DESKTOP_STATUS_IMAGE_OK);
                    return getOkByJson("打开连接成功");
                }
            }
        } catch (Exception e) {
            return exception(e);
        }
    }

    /**
     * 断开连接数据
     */
    public String disconConnect(String id) {
        try {
            Connect connect = getCurrentOpenConnect();
            if (connect.getIsha().equals("0")) {
                Jedis jedis = RedisUtil.openJedis(connect);
                if (null != jedis) {
                    RedisUtil.closeJedis(jedis);
                    DataUtil.clearConfig();
                }
            } else {
                ClusterUtil.closeCluster();
                DataUtil.clearConfig();
            }
            setEndsViewTitle(ItemUtil.DESKTOP_STATUS_NO, "no");
            setEndsViewImage(ItemUtil.DESKTOP_STATUS_IMAGE_NO);
            return getOkByJson("关闭连接成功");
        } catch (Exception e) {
            return exception(e);
        }
    }


    /**
     * 检测连接状态
     */
    public Integer isopenConnect(String id) {
        Connect connect = getCurrentOpenConnect();
        if (null != connect) {
            if (!StringUtils.isEmpty(id) && !connect.getId().equals(id)) {
                return 0;
            }
            if (connect.getIsha().equals("0")) {
                Jedis jedis = getCurrentJedisObject();
                if (null != jedis) {
                    return 1;
                } else {
                    return 0;
                }
            } else {
                Object jedisCluster = getJedisClusterObject();
                if (null != jedisCluster) {
                    return 1;
                } else {
                    return 0;
                }
            }
        } else {
            return 0;
        }
    }


    public String backupConnect() {
        try {
            String baseUrl = System.getProperty("user.home");
            String fileName = "redisplus-connect-" + DateUtil.formatDate(new Date(), DateUtil.DATE_STR_FILE) + ".bak";
            String filePath = baseUrl + "/" + fileName;
            List<Connect> connectList = this.dataService.selectConnect();
            if (null == connectList || connectList.isEmpty()) {
                return getNoByJson("暂无需要备的份数据");
            }
            StringBuffer dataBuffer = new StringBuffer("");
            for (Connect connect : connectList) {
                dataBuffer.append(JSON.toJSONString(connect));
                dataBuffer.append("\r\n");
            }
            boolean flag = FileUtil.writeStringToFile(filePath, dataBuffer.toString());
            if (flag) {
                return getOkByJson("数据成功备份至当前用户目录中");
            } else {
                return getNoByJson("备份数据失败");
            }
        } catch (Exception e) {
            return exception(e);
        }
    }


    public String recoveConnect() {
        try {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(Desktop.getRootStage());
            if (null != file) {
                String[] jsons = FileUtil.readFileToString(file.toString()).split("\r\n", -1);
                for (String json : jsons) {
                    Connect connect = JSON.parseObject(json, Connect.class);
                    if (null != connect) {
                        this.dataService.insertConnect(connect);
                    }
                }
                return getOkByJson("还原数据成功");
            } else {
                return getNoByJson("取消还原操作");
            }
        } catch (Exception e) {
            return exception(e);
        }
    }

}
