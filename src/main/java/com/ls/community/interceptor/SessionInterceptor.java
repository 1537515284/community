package com.ls.community.interceptor;

import com.ls.community.enums.AdPosEnum;
import com.ls.community.mapper.UserMapper;
import com.ls.community.model.Ad;
import com.ls.community.model.User;
import com.ls.community.model.UserExample;
import com.ls.community.service.AdService;
import com.ls.community.service.NavService;
import com.ls.community.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AdService adService;

    @Value("${github.client.id}")
    private String githubClientId;

    @Value("${github.redirect-uri}")
    private String githubRedirectUri;

    @Value("${gitee.client.id}")
    private String giteeClientId;

    @Value("${gitee.redirect-uri}")
    private String giteeRedirectUri;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //设置 context 级别的属性
        request.getServletContext().setAttribute("githubClientId", githubClientId);
        request.getServletContext().setAttribute("githubRedirectUri", githubRedirectUri);
        request.getServletContext().setAttribute("giteeClientId", giteeClientId);
        request.getServletContext().setAttribute("giteeRedirectUri", giteeRedirectUri);


        // 没有登录的时候也可以查看导航
        for (AdPosEnum adPos : AdPosEnum.values()) {
            request.getServletContext().setAttribute(adPos.name(), adService.list(adPos.name()));
        }
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0) {   // 防止浏览器禁用cookie，而出现空指针异常
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName()) && StringUtils.hasLength(cookie.getValue())) {
                    String token = cookie.getValue();
                    UserExample example = new UserExample();
                    example.createCriteria()
                                    .andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(example);
                    if (users.size() != 0) {
                        HttpSession session = request.getSession();
                        session.setAttribute("user", users.get(0));
                        Long unreadCount = notificationService.unreadCount(users.get(0).getId());
                        session.setAttribute("unreadCount", unreadCount);
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
