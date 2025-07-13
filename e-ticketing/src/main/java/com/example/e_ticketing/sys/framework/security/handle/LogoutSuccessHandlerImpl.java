package com.example.e_ticketing.sys.framework.security.handle;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import com.alibaba.fastjson2.JSON;
import com.example.e_ticketing.sys.common.constant.Constants;
import com.example.e_ticketing.sys.common.core.domain.AjaxResult;
import com.example.e_ticketing.sys.common.core.domain.dto.LoginUser;
import com.example.e_ticketing.sys.common.utils.MessageUtils;
import com.example.e_ticketing.sys.common.utils.ServletUtils;
import com.example.e_ticketing.sys.common.utils.StringUtils;
import com.example.e_ticketing.sys.framework.manager.AsyncManager;
import com.example.e_ticketing.sys.framework.manager.factory.AsyncFactory;
import com.example.e_ticketing.sys.framework.web.service.TokenService;

@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler
{
    @Autowired
    private TokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException
    {
        LoginUser loginUser = tokenService.getLoginUser((jakarta.servlet.http.HttpServletRequest) request);
        if (StringUtils.isNotNull(loginUser))
        {
            String userName = loginUser.getUsername();
            tokenService.delLoginUser(loginUser.getToken());
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGOUT, MessageUtils.message("user.logout.success")));
        }
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.success(MessageUtils.message("user.logout.success"))));
    }
}
