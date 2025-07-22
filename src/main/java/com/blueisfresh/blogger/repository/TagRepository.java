package com.blueisfresh.blogger.repository;

import com.blueisfresh.blogger.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    // Crud Operations automatically generated

    Optional<Tag> findByTagName(String tagName);
}
