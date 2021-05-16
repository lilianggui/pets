package com.llg.pets.users.controller;

import com.llg.pets.users.entity.Users;
import com.llg.pets.users.service.interfaces.UserService;
import com.llg.pets.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class UsersController {

    @Autowired
    private UserService userService;

    public static final String USER_SESSION_KEY = "user";

    @PostMapping("/login")
    public ResponseResult<Users> login(@RequestBody Users user, HttpServletRequest request){
        Users users = userService.login(user);
        if(users == null){
            return ResponseResult.buildFail("用户不存在");
        } else if(user.getPassword() == null) {
            return ResponseResult.buildFail("密码不能为空");
        } else if(!user.getPassword().equals(users.getPassword())) {
            return ResponseResult.buildFail("用户名或密码不正确");
        }
        request.getSession().setAttribute(USER_SESSION_KEY, users);
        return ResponseResult.buildSuccess(users);
    }

    @PostMapping("/logout")
    public ResponseResult<Boolean> logout(HttpServletRequest request){
        request.getSession().invalidate();
        return ResponseResult.buildSuccess(true);
    }
}
