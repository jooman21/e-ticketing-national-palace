package com.example.e_ticketing.sys.controller.system;

import com.example.e_ticketing.sys.common.annotation.Log;
import com.example.e_ticketing.sys.common.core.controller.BaseController;
import com.example.e_ticketing.sys.common.core.domain.AjaxResult;
import com.example.e_ticketing.sys.common.core.page.TableDataInfo;
import com.example.e_ticketing.sys.common.enums.RequestType;
import com.example.e_ticketing.sys.common.utils.poi.ExcelUtil;
import com.example.e_ticketing.sys.system.domain.SysPost;
import com.example.e_ticketing.sys.system.service.ISysPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/system/post")
public class SysPostController extends BaseController
{
    @Autowired
    private ISysPostService postService;

    @PreAuthorize("@ss.hasPermi('system:post:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysPost post)
    {
        startPage();
        List<SysPost> list = postService.selectPostList(post);
        return getDataTable(list);
    }
    
    @Log(title = "Post Management", businessType = RequestType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:post:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysPost post)
    {
        List<SysPost> list = postService.selectPostList(post);
        ExcelUtil<SysPost> util = new ExcelUtil<SysPost>(SysPost.class);
        util.exportExcel(response, list, "Post Data");
    }

    @PreAuthorize("@ss.hasPermi('system:post:query')")
    @GetMapping(value = "/{postId}")
    public AjaxResult getInfo(@PathVariable Long postId)
    {
        return success(postService.selectPostById(postId));
    }

    @PreAuthorize("@ss.hasPermi('system:post:add')")
    @Log(title = "Post Management", businessType = RequestType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysPost post)
    {
        if (!postService.checkPostNameUnique(post)) {
            return error("Adding post '" + post.getPostName() + "' failed. The post name already exists.");
        } else if (!postService.checkPostCodeUnique(post)) {
            return error("Adding post '" + post.getPostName() + "' failed. The post code already exists.");
        }
        post.setCreateBy(getUsername());
        return toAjax(postService.insertPost(post));
    }

    @PreAuthorize("@ss.hasPermi('system:post:edit')")
    @Log(title = "Post Management", businessType = RequestType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysPost post)
    {
        if (!postService.checkPostNameUnique(post)) {
            return error("Updating post '" + post.getPostName() + "' failed. The post name already exists.");
        } else if (!postService.checkPostCodeUnique(post)) {
            return error("Updating post '" + post.getPostName() + "' failed. The post code already exists.");
        }
        post.setUpdateBy(getUsername());
        return toAjax(postService.updatePost(post));
    }

    @PreAuthorize("@ss.hasPermi('system:post:remove')")
    @Log(title = "Post Management", businessType = RequestType.DELETE)
    @DeleteMapping("/{postIds}")
    public AjaxResult remove(@PathVariable Long[] postIds)
    {
        return toAjax(postService.deletePostByIds(postIds));
    }

    @GetMapping("/optionselect")
    public AjaxResult optionselect()
    {
        List<SysPost> posts = postService.selectPostAll();
        return success(posts);
    }
}
