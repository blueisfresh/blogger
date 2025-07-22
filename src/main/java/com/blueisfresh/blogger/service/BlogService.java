package com.blueisfresh.blogger.service;

import com.blueisfresh.blogger.dto.BlogCreateDto;
import com.blueisfresh.blogger.dto.BlogUpdateDto;
import com.blueisfresh.blogger.entity.Blog;
import com.blueisfresh.blogger.entity.Tag;
import com.blueisfresh.blogger.exception.ResourceNotFoundException;
import com.blueisfresh.blogger.repository.BlogRepository;
import com.blueisfresh.blogger.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private TagRepository tagRepository;

    public boolean checkBlogIfExists(Long id) {
        return blogRepository.existsById(id);
    }

    @Transactional // Whole Operation is a single transaction
    public Blog createBlog(BlogCreateDto blogCreateDto) {
        // Mapping Dto to Entity
        Blog blog = new Blog();
        blog.setTitle(blogCreateDto.getTitle());
        blog.setContent(blogCreateDto.getContent());
        blog.setCategory(blogCreateDto.getCategory());

        // Handle tags
        Set<Tag> tags = new HashSet<>();
        if (blogCreateDto.getTagNames() != null && !blogCreateDto.getTagNames().isEmpty()) {
            for (String tagName : blogCreateDto.getTagNames()) {
                Tag tag = tagRepository.findByTagName(tagName)
                        .orElseGet(() -> tagRepository.save(new Tag(tagName))); // Find or create new tag
                tags.add(tag); // Adding fully managed Tag entities with id
            }
        }
        blog.setTags(tags);

        // Set timestamps
        Instant now = Instant.now();
        blog.setCreatedAt(now);
        blog.setUpdatedAt(now);

        return blogRepository.save(blog); // JPA automatically knows how to populate the Mapping table since it has both ids
    }

    @Transactional
    public Blog updateBlog(Long id, BlogUpdateDto blogUpdateDto) {
        Optional<Blog> optionalBlog = blogRepository.findById(id);

        if (optionalBlog.isPresent()) {
            Blog existingBlog = optionalBlog.get();

            existingBlog.setTitle(blogUpdateDto.getTitle());
            existingBlog.setContent(blogUpdateDto.getContent());
            existingBlog.setCategory(blogUpdateDto.getCategory());

            // Handle tags for update: This replaces existing tags with the new set
            Set<Tag> newTags = new HashSet<>();
            if (blogUpdateDto.getTagNames() != null && !blogUpdateDto.getTagNames().isEmpty()) {
                for (String tagName : blogUpdateDto.getTagNames()) {
                    Tag tag = tagRepository.findByTagName(tagName)
                            .orElseGet(() -> tagRepository.save(new Tag(tagName)));
                    newTags.add(tag);
                }
            }
            existingBlog.setTags(newTags); // This will manage the join table

            // Update timestamp
            existingBlog.setUpdatedAt(Instant.now());

            return blogRepository.save(existingBlog);
        } else {
            throw new ResourceNotFoundException("Blog with ID " + id + " not found.");
        }
    }

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Optional<Blog> getById(Long id) {
        return blogRepository.findById(id);
    }

    @Transactional
    public void deleteBlog(Long id) {
        if (blogRepository.existsById(id)) {
            blogRepository.deleteById(id);
        } else {
            // Throw the custom exception
            throw new ResourceNotFoundException("Blog with ID " + id + " not found for deletion.");
        }
    }
}
