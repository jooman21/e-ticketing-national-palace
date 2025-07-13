package com.example.e_ticketing.sys.controller.system;

import com.example.e_ticketing.sys.common.annotation.Log;
import com.example.e_ticketing.sys.common.constant.UserConstants;
import com.example.e_ticketing.sys.common.core.controller.BaseController;
import com.example.e_ticketing.sys.common.core.domain.AjaxResult;
import com.example.e_ticketing.sys.common.core.domain.entity.SysDept;
import com.example.e_ticketing.sys.common.enums.RequestType;
import com.example.e_ticketing.sys.common.utils.StringUtils;
import com.example.e_ticketing.sys.system.service.ISysDeptService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController
{
    @Autowired
    private ISysDeptService deptService;

    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @GetMapping("/list")
    public AjaxResult list(SysDept dept)
    {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return success(depts);
    }

    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @GetMapping("/list/exclude/{deptId}")
    public AjaxResult excludeChild(@PathVariable(value = "deptId", required = false) Long deptId)
    {
        List<SysDept> depts = deptService.selectDeptList(new SysDept());
        depts.removeIf(d -> d.getDeptId().intValue() == deptId || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + ""));
        return success(depts);
    }

    @PreAuthorize("@ss.hasPermi('system:dept:query')")
    @GetMapping(value = "/{deptId}")
    public AjaxResult getInfo(@PathVariable Long deptId)
    {
        deptService.checkDeptDataScope(deptId);
        return success(deptService.selectDeptById(deptId));
    }

    @PreAuthorize("@ss.hasPermi('system:dept:add')")
    @Log(title = "Department Management", businessType = RequestType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysDept dept)
    {
        if (!deptService.checkDeptNameUnique(dept))
        {
            return error("Adding department '" + dept.getDeptName() + "' failed. The department name already exists.");
        }
        dept.setCreateBy(getUsername());
        return toAjax(deptService.insertDept(dept));
    }

    @PreAuthorize("@ss.hasPermi('system:dept:edit')")
    @Log(title = "Department Management", businessType = RequestType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysDept dept)
    {
        Long deptId = dept.getDeptId();
        deptService.checkDeptDataScope(deptId);
        if (!deptService.checkDeptNameUnique(dept)) {
            return error("Updating department '" + dept.getDeptName() + "' failed. The department name already exists.");
        } else if (dept.getParentId().equals(deptId)) {
            return error("Updating department '" + dept.getDeptName() + "' failed. The parent department cannot be itself.");
        } else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus()) && deptService.selectNormalChildrenDeptById(deptId) > 0) {
            return error("This department contains active sub-departments that haven't been disabled!");
        }
        dept.setUpdateBy(getUsername());
        return toAjax(deptService.updateDept(dept));
    }

    @PreAuthorize("@ss.hasPermi('system:dept:remove')")
    @Log(title = "Department Management", businessType = RequestType.DELETE)
    @DeleteMapping("/{deptId}")
    public AjaxResult remove(@PathVariable Long deptId)
    {
        if (deptService.hasChildByDeptId(deptId)) {
            return warn("Sub-departments exist. Deletion is not allowed.");
        }
        if (deptService.checkDeptExistUser(deptId)) {
            return warn("Users exist in the department. Deletion is not allowed.");
        }
        deptService.checkDeptDataScope(deptId);
        return toAjax(deptService.deleteDeptById(deptId));
    }
}
