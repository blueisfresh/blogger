package com.blueisfresh.blogger.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.processing.Exclude;


import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_blogs")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    @EqualsAndHashCode.Include // Include id in Equals/ Hashcode
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @NotBlank(message = "Content cannot be blank")
    @Size(min = 10, message = "Content must be at least 10 characters long")
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @NotBlank(message = "Category cannot be blank")
    @Size(max = 50, message = "Category must not exceed 50 characters")
    @Column(name = "category", nullable = false, length = 50)
    private String category;

    @JsonManagedReference
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "tbl_blog_tag_mapping", // Name of the join table
            joinColumns = @JoinColumn(name = "blog_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();


    @NotNull(message = "Creation timestamp cannot be null")
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @NotNull(message = "Update timestamp cannot be null")
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    // Custom Constructor for DTOs
    public Blog(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.tags = new HashSet<>();
        // Timestamps will be set by the service
    }

}