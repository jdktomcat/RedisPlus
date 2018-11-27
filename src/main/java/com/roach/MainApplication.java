package com.roach;

import com.roach.core.desktop.Desktop;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author roach
 */
@SpringBootApplication
@MapperScan("com.roach.base.dao")
public class MainApplication extends Desktop {

    public static void main(String[] args) {
        launch(args);
    }
}
