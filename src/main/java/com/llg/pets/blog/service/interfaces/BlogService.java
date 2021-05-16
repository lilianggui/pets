package com.llg.pets.blog.service.interfaces;

import com.llg.pets.blog.entity.Blog;

import java.util.List;

public interface BlogService {
    void saveUser(Blog blog);

    List<Blog> listBlog();

    Blog blogDetail(Integer id);
}
