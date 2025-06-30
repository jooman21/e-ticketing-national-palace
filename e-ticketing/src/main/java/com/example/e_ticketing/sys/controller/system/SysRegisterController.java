package com.example.e_ticketing.sys.controller.system;

import com.palace.museum.common.core.controller.BaseController;
import com.palace.museum.common.core.domain.AjaxResult;
import com.palace.museum.common.core.domain.dto.RegisterDto;
import com.palace.museum.common.utils.StringUtils;
import com.palace.museum.framework.web.service.SysRegisterService;
import com.palace.museum.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysRegisterController extends BaseController
{
    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;

    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterDto user)
    {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
        {
            return error("The registration feature is not enabled in the current system!");
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}
