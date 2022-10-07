package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.common.Result;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result<String> register(@RequestBody User user) {
        //页面提交的密码进行md5加密
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //存入数据库的密码
        user.setPassword(password);

        //根据用户名查询数据库是否有重复
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserNumber, user.getUserNumber());
        User user1 = userService.getOne(wrapper);

        //如果查到有数据则表示用户已存在
        if (user1 != null) {
            return Result.error("注册失败,用户已存在");
        }

        //数据库中没有该用户数据,注册成功
        userService.save(user);
        return Result.success("注册成功");
    }

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

        //登陆成功,将员工id线程存储并返回登陆成功
        //BaseContext.setCurrentId(user1.getId());
        request.getSession().setAttribute("user", user1.getId());
        return Result.success(user1);
    }

    @PutMapping
    public Result<String> update(@RequestBody User user) {
        userService.updateById(user);
        return Result.success("修改成功");
    }

    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        //清理session中保存的登录员工id
        request.getSession().removeAttribute("user");
        return Result.success("退出成功");
    }
}
