package com.example.e_ticketing.sys.controller.system;

import com.example.e_ticketing.sys.common.config.NationalPalaceConfig;
import com.example.e_ticketing.sys.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysIndexController
{
    @Autowired
    private NationalPalaceConfig npiConfig;

    @RequestMapping("/")
    public String index()
    {
        return StringUtils.format("Welcome to the {} backend management framework. Current version: v{}. Please access it via the frontend address.", npiConfig.getName(), npiConfig.getVersion());    }
}
