package com.blueisfresh.blogger.repository;

import com.blueisfresh.blogger.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    // CRUD Automatically created

    @Query("SELECT b FROM Blog b WHERE " +
            "LOWER(b.category) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(b.content) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(b.title) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<Blog> findBlogsBySearchTerm(@Param("term") String term);
}
