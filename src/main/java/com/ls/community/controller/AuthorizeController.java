package com.ls.community.controller;

import com.ls.community.model.User;
import com.ls.community.service.UserService;
import com.ls.community.strategy.LoginUserInfo;
import com.ls.community.strategy.UserStrategy;
import com.ls.community.strategy.UserStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Slf4j
@Controller
public class AuthorizeController {

    @Autowired
    private UserStrategyFactory userStrategyFactory;

    @Autowired
    private UserService userService;


    @RequestMapping("/callback/{type}")
    public String callback(
            @PathVariable("type") String type,
            @RequestParam("code") String code,
            @RequestParam(value = "state",required = false) String state,
            HttpServletResponse response
           ){
        UserStrategy userStrategy = userStrategyFactory.getStrategy(type);
        LoginUserInfo loginUserInfo = userStrategy.getUser(code, state);
        if (loginUserInfo != null && loginUserInfo.getId() != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(loginUserInfo.getName());
            user.setAccountId(String.valueOf(loginUserInfo.getId()));
            user.setType(type);
            user.setAvatarUrl(loginUserInfo.getAvatarUrl());
            userService.createOrUpdate(user);
            Cookie cookie = new Cookie("token",token);
            cookie.setMaxAge(60*60*24*30*6);
            cookie.setPath("/");
            response.addCookie(cookie);
            log.info("user login,{}",user.getId());
            return "redirect:/";
        }else{
            log.error("callback get github error,{}", loginUserInfo);
            // 登录失败，重新登录
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        session.invalidate();
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        log.info("user logout,{}", user);
        return "redirect:/";

    }
}
