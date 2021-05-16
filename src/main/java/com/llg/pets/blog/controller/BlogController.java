package com.llg.pets.blog.controller;


import com.llg.pets.blog.entity.Blog;
import com.llg.pets.blog.service.interfaces.BlogService;
import com.llg.pets.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class BlogController {

    @Autowired
    BlogService blogService;

    @PostMapping("/saveBlog")
    public ResponseResult<Boolean> saveBlog(@RequestBody Blog blog){
        blogService.saveUser(blog);
        return ResponseResult.buildSuccess(true);
    }

    @GetMapping("/listBlog")
    public ResponseResult<List<Blog>> listBlog(){
        List<Blog> blogList = blogService.listBlog();
        return ResponseResult.buildSuccess(blogList);
    }
    @GetMapping("/blogDetail")
    public ResponseResult<Blog> blogDetail(@RequestParam Integer id){
        Blog b = blogService.blogDetail(id);
        return ResponseResult.buildSuccess(b);
    }
}
