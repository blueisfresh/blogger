package com.blueisfresh.blogger.repository;

import com.blueisfresh.blogger.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    // CRUD Automatically created
}
