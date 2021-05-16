package com.llg.pets.blog.service.impl;

import com.llg.pets.blog.entity.Blog;
import com.llg.pets.blog.repository.BlogRepository;
import com.llg.pets.blog.service.interfaces.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {


    @Autowired
    BlogRepository blogRepository;

    @Override
    public void saveUser(Blog blog) {
        blogRepository.save(blog);
    }

    @Override
    public List<Blog> listBlog() {
        return blogRepository.findAll();
    }

    @Override
    public Blog blogDetail(Integer id) {
        return blogRepository.getOne(id);
    }
}
