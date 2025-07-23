package com.blueisfresh.blogger.controller;

import com.blueisfresh.blogger.dto.BlogCreateDto;
import com.blueisfresh.blogger.dto.BlogResponseDto;
import com.blueisfresh.blogger.dto.BlogUpdateDto;
import com.blueisfresh.blogger.entity.Blog;
import com.blueisfresh.blogger.exception.ResourceNotFoundException;
import com.blueisfresh.blogger.repository.BlogRepository;
import com.blueisfresh.blogger.service.BlogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/blog")
public class BlogController {

    private final BlogRepository blogRepository;
    private final BlogService blogService;

    public BlogController(BlogRepository blogRepository, BlogService blogService) {
        this.blogRepository = blogRepository;
        this.blogService = blogService;
    }

    @GetMapping
    public ResponseEntity<List<Blog>> getBlogs() {
        // List<Blog> blogs = blogService.getAllBlogs();
        // return ResponseEntity.ok(blogs);
        List<Blog> blogs = blogRepository.findAll(); // Get the list of blogs
        return ResponseEntity.ok(blogs);
    }

    @PostMapping
    public ResponseEntity<Blog> saveBlog(@Valid @RequestBody BlogCreateDto blog) {
        Blog createdBlog = blogService.createBlog((blog));
        return new ResponseEntity<>(blogService.getById(createdBlog.getId()).orElseThrow(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable Long id, @Valid @RequestBody BlogUpdateDto blog) {
        Blog updatedBlog = blogService.updateBlog(id, blog);
        return ResponseEntity.ok(blogService.getById(updatedBlog.getId()).orElseThrow());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204 HTTP for successful deletion
    public void deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Blog> getBlogById(@PathVariable Long id) { // Return DTO
//        BlogResponseDto blog = blogService.getById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Blog with ID " + id + " not found."));
//        return ResponseEntity.ok(blog);
//    }
}
