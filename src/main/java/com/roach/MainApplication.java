package com.roach;

import com.roach.core.desktop.Desktop;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 程序主入口
 *
 * @author jdktomcat
 */
@SpringBootApplication
@MapperScan("com.roach.base.dao")
public class MainApplication extends Desktop {

    public static void main(String[] args) {
        launch(args);
    }
}
