package com.example.e_ticketing.sys.system.service;

import com.palace.museum.common.core.domain.TreeSelect;
import com.palace.museum.common.core.domain.entity.SysDept;

import java.util.List;

public interface ISysDeptService
{
    List<SysDept> selectDeptList(SysDept dept);

    List<TreeSelect> selectDeptTreeList(SysDept dept);

    List<SysDept> buildDeptTree(List<SysDept> depts);

    List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts);

    List<Long> selectDeptListByRoleId(Long roleId);

    SysDept selectDeptById(Long deptId);

    int selectNormalChildrenDeptById(Long deptId);

    boolean hasChildByDeptId(Long deptId);

    boolean checkDeptExistUser(Long deptId);

    boolean checkDeptNameUnique(SysDept dept);

    void checkDeptDataScope(Long deptId);

    int insertDept(SysDept dept);

    int updateDept(SysDept dept);

    int deleteDeptById(Long deptId);
}
