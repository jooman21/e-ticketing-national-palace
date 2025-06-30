package com.example.e_ticketing.sys.system.service;

import com.palace.museum.common.core.domain.dto.LoginUser;
import com.palace.museum.system.domain.SysUserOnline;

public interface ISysUserOnlineService
{
    SysUserOnline selectOnlineByIpaddr(String ipaddr, LoginUser user);

    SysUserOnline selectOnlineByUserName(String userName, LoginUser user);

    SysUserOnline selectOnlineByInfo(String ipaddr, String userName, LoginUser user);

    SysUserOnline loginUserToUserOnline(LoginUser user);
}
