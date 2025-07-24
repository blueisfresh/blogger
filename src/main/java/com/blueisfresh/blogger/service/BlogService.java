package com.blueisfresh.blogger.service;

import com.blueisfresh.blogger.dto.BlogCreateDto;
import com.blueisfresh.blogger.dto.BlogUpdateDto;
import com.blueisfresh.blogger.entity.Blog;
import com.blueisfresh.blogger.entity.Tag;
import com.blueisfresh.blogger.exception.ResourceNotFoundException;
import com.blueisfresh.blogger.repository.BlogRepository;
import com.blueisfresh.blogger.repository.TagRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private TagRepository tagRepository;

    @Transactional
    public Blog createBlog(BlogCreateDto blogCreateDto) {
        // Mapping Dto to Entity
        Blog blog = new Blog();
        blog.setTitle(blogCreateDto.getTitle());
        blog.setContent(blogCreateDto.getContent());
        blog.setCategory(blogCreateDto.getCategory());

        // Handle tags
        Set<Tag> tags = new HashSet<>();
        // Check that Tags is not empty
        if (blogCreateDto.getTagNames() != null && !blogCreateDto.getTagNames().isEmpty()) {
            // Map through all the tags
            for (String tagName : blogCreateDto.getTagNames()) {
                // Does Tag exist IF NOT create new tag
                Tag tag = tagRepository.findByTagName(tagName)
                        .orElseGet(() -> tagRepository.save(new Tag(tagName)));
                tags.add(tag); // Adding fully managed Tag entities with id to Tag table and the mapping table
            }
        }
        blog.setTags(tags); // create the new blog

        // Set timestamps
        Instant now = Instant.now();
        blog.setCreatedAt(now);
        blog.setUpdatedAt(now);

        return blogRepository.save(blog); // JPA automatically knows how to populate the Mapping table since it has both ids
    }

    @Transactional
    public Blog updateBlog(Long id, BlogUpdateDto blogUpdateDto) {
        // Does Blog already exist?
        Blog existingBlog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog with ID " + id + " not found."));

        // Update scalar fields
        existingBlog.setTitle(blogUpdateDto.getTitle());
        existingBlog.setContent(blogUpdateDto.getContent());
        existingBlog.setCategory(blogUpdateDto.getCategory());

        existingBlog.getTags().clear(); // Clears associations in join table

        // Updates the tag table and resets the mapping table
        if (blogUpdateDto.getTagNames() != null && !blogUpdateDto.getTagNames().isEmpty()) {
            for (String tagName : blogUpdateDto.getTagNames()) {
                Tag tag = tagRepository.findByTagName(tagName)
                        .orElseGet(() -> tagRepository.save(new Tag(tagName))); // Find or create
                existingBlog.getTags().add(tag); // Add to the managed collection
            }
        }

        existingBlog.setUpdatedAt(Instant.now());

        return blogRepository.save(existingBlog);
    }

    @Transactional(readOnly = true)
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    @Transactional
    public Optional<Blog> getById(Long id) {
        return blogRepository.findById(id);
    }

    @Transactional
    public void deleteBlog(Long id) {
        // Check if it exists before deleting
        if (blogRepository.existsById(id)) {
            blogRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Blog with ID " + id + " not found for deletion.");
        }
    }

    // Search Term
    public List<Blog> searchBlogs(String searchTerm) {
        return blogRepository.findBlogsBySearchTerm(searchTerm);
    }

}
