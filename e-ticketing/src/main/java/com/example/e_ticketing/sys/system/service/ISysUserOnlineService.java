package com.example.e_ticketing.sys.system.service;

import com.example.e_ticketing.sys.common.core.domain.dto.LoginUser;
import com.example.e_ticketing.sys.system.domain.SysUserOnline;

public interface ISysUserOnlineService
{
    SysUserOnline selectOnlineByIpaddr(String ipaddr, LoginUser user);

    SysUserOnline selectOnlineByUserName(String userName, LoginUser user);

    SysUserOnline selectOnlineByInfo(String ipaddr, String userName, LoginUser user);

    SysUserOnline loginUserToUserOnline(LoginUser user);
}
