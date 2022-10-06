package com.example.demo.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 公共字段自动填充
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    /*@Autowired
    private HttpServletRequest request;

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充[insert]");
        log.info(metaObject.toString());

        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        Long employee = (Long) request.getSession().getAttribute("employee");
        metaObject.setValue("createUser", employee);
        metaObject.setValue("updateUser", employee);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充[update]");
        log.info(metaObject.toString());

        long id = Thread.currentThread().getId();
        log.info("线程为{}",id);

        metaObject.setValue("updateTime", LocalDateTime.now());
        Long employee = (Long) request.getSession().getAttribute("employee");
        metaObject.setValue("updateUser", employee);
    }*/

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充[insert]");
        log.info(metaObject.toString());

        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
//        Long id = BaseContext.getCurrentId();
//        metaObject.setValue("createUser", id);
//        metaObject.setValue("updateUser", id);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充[update]");
        log.info(metaObject.toString());

        /*long id = Thread.currentThread().getId();
        log.info("线程为{}",id);*/

        metaObject.setValue("updateTime", LocalDateTime.now());
//        Long id = BaseContext.getCurrentId();
//        metaObject.setValue("updateUser", id);
    }
}
