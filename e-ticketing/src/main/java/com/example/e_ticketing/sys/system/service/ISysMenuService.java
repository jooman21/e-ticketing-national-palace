package com.example.e_ticketing.sys.system.service;

import com.palace.museum.common.core.domain.TreeSelect;
import com.palace.museum.common.core.domain.entity.SysMenu;
import com.palace.museum.system.domain.vo.RouterVo;

import java.util.List;
import java.util.Set;

public interface ISysMenuService
{
    List<SysMenu> selectMenuList(Long userId);

    List<SysMenu> selectMenuList(SysMenu menu, Long userId);

    Set<String> selectMenuPermsByUserId(Long userId);

    Set<String> selectMenuPermsByRoleId(Long roleId);

    List<SysMenu> selectMenuTreeByUserId(Long userId);

    List<Long> selectMenuListByRoleId(Long roleId);

    List<RouterVo> buildMenus(List<SysMenu> menus);

    List<SysMenu> buildMenuTree(List<SysMenu> menus);

    List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus);

    SysMenu selectMenuById(Long menuId);

    boolean hasChildByMenuId(Long menuId);

    boolean checkMenuExistRole(Long menuId);

    int insertMenu(SysMenu menu);

    int updateMenu(SysMenu menu);

   int deleteMenuById(Long menuId);

    boolean checkMenuNameUnique(SysMenu menu);
}
