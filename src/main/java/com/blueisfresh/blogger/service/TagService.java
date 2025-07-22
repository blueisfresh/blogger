package com.blueisfresh.blogger.service;

import com.blueisfresh.blogger.dto.TagCreateDto;
import com.blueisfresh.blogger.dto.TagUpdateDto;
import com.blueisfresh.blogger.entity.Tag;
import com.blueisfresh.blogger.exception.ResourceNotFoundException;
import com.blueisfresh.blogger.repository.TagRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public boolean checkTagIfExists(Long id) {
        return tagRepository.existsById(id);
    }

    @Transactional // Whole Operation is a single transaction
    public Tag createTag(TagCreateDto tagDto) {
        // Mapping Dto to Entity
        Tag tagEntity = new Tag();
        tagEntity.setTagName(tagDto.getTagName());

        // id will be generated automatically
        return tagRepository.save(tagEntity);
    }

    @Transactional
    public Tag updateTag(Long id, TagUpdateDto tagUpdateDtoDetails) {
        Tag existingTag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag with ID " + id + " not found."));

        // TagUpdateDto has @NotBlank; tagName will never be null or blank here
        existingTag.setTagName(tagUpdateDtoDetails.getTagName());

        return tagRepository.save(existingTag);
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Optional<Tag> getById(Long id) {
        return tagRepository.findById(id);
    }

    @Transactional
    public void deleteTag(Long id) {
        if (tagRepository.existsById(id)) {
            tagRepository.deleteById(id);
        } else {
            // Throw the custom exception
            throw new ResourceNotFoundException("Tag with ID " + id + " not found for deletion.");
        }
    }
}