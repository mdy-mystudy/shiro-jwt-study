package com.datealive.shiro;

import com.datealive.pojo.User;
import com.datealive.service.UserService;
import com.datealive.utils.JwtUtils;
import com.datealive.utils.SpringUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: JwtFilter
 * @Description: TODO
 * @author: datealive
 * @date: 2021/3/5  21:13
 */
@Component
public class JwtFilter extends BasicHttpAuthenticationFilter {



//    private UserService userService;
//
//    @Autowired
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if(isLoginAttempt(request,response)){
            try {
                executeLogin(request,response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 判断header是否存在authorization字段
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String jwt = req.getHeader("Authorization");

        return jwt != null;
    }

    /**
     * 执行登录
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        JwtToken token = new JwtToken(authorization);
        getSubject(request,response).login(token);
        return true;
    }

    /**
     * 对跨域的支持
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(org.springframework.http.HttpStatus.OK.value());
            return false;
        }
        //判断是否要续签
        String token = httpServletRequest.getHeader("authorization");
        if(token!=null){
            boolean isExpiredHours = JwtUtils.isTokenExpiredSubHours(token);
            boolean isExpired = JwtUtils.isTokenExpired(token);
            //如果要过期了，进行续签操作，已经过期了的，不进行续签操作
            if(isExpiredHours&&!isExpired){
                try{
                    UserService userService = (UserService) SpringUtils.getBean("UserServiceImpl");
                    String username = JwtUtils.getUsername(token);
                    User userByPwd = userService.getUserByPwd(username);
                    System.out.println("filter里的"+username);
                    String newToken = JwtUtils.getToken(userByPwd.getUsername(), userByPwd.getPassword());
                    System.out.println("新的token===>"+newToken);
                    httpServletResponse.setHeader("Authorization", newToken);
                    httpServletResponse.setHeader("Access-control-Expose-Headers", "Authorization");
                    System.out.println("进行了续签操作");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return super.preHandle(request, response);
    }
}
