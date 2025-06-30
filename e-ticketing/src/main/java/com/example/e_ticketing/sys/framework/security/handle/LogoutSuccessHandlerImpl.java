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
import com.palace.museum.common.constant.Constants;
import com.palace.museum.common.core.domain.AjaxResult;
import com.palace.museum.common.core.domain.dto.LoginUser;
import com.palace.museum.common.utils.MessageUtils;
import com.palace.museum.common.utils.ServletUtils;
import com.palace.museum.common.utils.StringUtils;
import com.palace.museum.framework.manager.AsyncManager;
import com.palace.museum.framework.manager.factory.AsyncFactory;
import com.palace.museum.framework.web.service.TokenService;

@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler
{
    @Autowired
    private TokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException
    {
        LoginUser loginUser = tokenService.getLoginUser((HttpServletRequest) request);
        if (StringUtils.isNotNull(loginUser))
        {
            String userName = loginUser.getUsername();
            tokenService.delLoginUser(loginUser.getToken());
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGOUT, MessageUtils.message("user.logout.success")));
        }
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.success(MessageUtils.message("user.logout.success"))));
    }
}
