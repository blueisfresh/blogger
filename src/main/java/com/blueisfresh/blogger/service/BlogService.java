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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private TagRepository tagRepository;

    @PersistenceContext
    private EntityManager entityManager;

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
        Blog existingBlog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog with ID " + id + " not found."));

        // Ensure the tags collection is fully loaded before modification
        existingBlog.getTags().size();

        // Update scalar fields
        existingBlog.setTitle(blogUpdateDto.getTitle());
        existingBlog.setContent(blogUpdateDto.getContent());
        existingBlog.setCategory(blogUpdateDto.getCategory());

        existingBlog.getTags().clear(); // Clears associations in join table

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
        if (blogRepository.existsById(id)) {
            blogRepository.deleteById(id);
        } else {
            // Throw the custom exception
            throw new ResourceNotFoundException("Blog with ID " + id + " not found for deletion.");
        }
    }
}
