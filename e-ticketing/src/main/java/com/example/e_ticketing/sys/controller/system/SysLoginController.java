package com.example.e_ticketing.sys.controller.system;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.palace.museum.common.constant.Constants;
import com.palace.museum.common.core.domain.AjaxResult;
import com.palace.museum.common.core.domain.entity.SysMenu;
import com.palace.museum.common.core.domain.entity.SysUser;
import com.palace.museum.common.core.domain.dto.LoginDto;
import com.palace.museum.common.core.domain.dto.LoginUser;
import com.palace.museum.common.utils.SecurityUtils;
import com.palace.museum.framework.web.service.SysLoginService;
import com.palace.museum.framework.web.service.SysPermissionService;
import com.palace.museum.framework.web.service.TokenService;
import com.palace.museum.system.service.ISysMenuService;

@RestController
@RequestMapping("/auth")
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginDto loginDto)
    {
        AjaxResult ajax = AjaxResult.success();
        String token = loginService.login(loginDto.getUsername(), loginDto.getPassword(), loginDto.getCode(),
                loginDto.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    @GetMapping("/getInfo")
    public AjaxResult getInfo()
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getUser();
        Set<String> roles = permissionService.getRolePermission(user);
        Set<String> permissions = permissionService.getMenuPermission(user);
        if (!loginUser.getPermissions().equals(permissions))
        {
            loginUser.setPermissions(permissions);
            tokenService.refreshToken(loginUser);
        }
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    @GetMapping("/getRouters")
    public AjaxResult getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
