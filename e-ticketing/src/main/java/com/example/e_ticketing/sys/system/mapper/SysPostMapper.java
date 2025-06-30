package com.example.e_ticketing.sys.system.mapper;

import com.palace.museum.system.domain.SysPost;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysPostMapper
{
    List<SysPost> selectPostList(SysPost post);

    List<SysPost> selectPostAll();

    SysPost selectPostById(Long postId);

    List<Long> selectPostListByUserId(Long userId);

    List<SysPost> selectPostsByUserName(String userName);

    int deletePostById(Long postId);

    int deletePostByIds(Long[] postIds);

    int updatePost(SysPost post);

    int insertPost(SysPost post);

    SysPost checkPostNameUnique(String postName);

    SysPost checkPostCodeUnique(String postCode);
}
