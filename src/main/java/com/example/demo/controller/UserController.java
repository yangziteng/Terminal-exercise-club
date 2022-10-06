package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.terminalex.common.Result;
import com.terminalex.domain.User;
import com.terminalex.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<User> login(HttpServletRequest request, @RequestBody User user) {
        //页面提交的密码进行md5加密
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //根据用户名查询数据库
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserNumber, user.getUserNumber());
        User user1 = userService.getOne(wrapper);

        //如果没有查到则返回登陆失败
        if (user1 == null) {
            return Result.error("登陆失败");
        }

        //比对密码
        if (!user1.getPassword().equals(password)) {
            return Result.error("登陆失败");
        }

        //登陆成功,将员工id放入session并返回登陆成功
        request.getSession().setAttribute("employee", user1.getId());
        return Result.success(user1);
    }
}
