package com.blueisfresh.blogger.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
//@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Only 'id' is included in equals/hashCode
//@ToString(exclude = {"tags"})
// Exclude 'tags' from toString to prevent LazyInitializationException and cycles
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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "tbl_blog_tag_mapping", // Name of the join table
            joinColumns = @JoinColumn(name = "blog_id"), // FK column referring to Blog
            inverseJoinColumns = @JoinColumn(name = "tag_id") // FK column referring to Tag
    )
    @Size(min = 1, message = "A blog must have at least one tag")

//    @EqualsAndHashCode.Exclude // Exclude from equals/hashCode
//    @ToString.Exclude // Exclude from toString
    private Set<Tag> tags = new HashSet<>();


    @NotNull(message = "Creation timestamp cannot be null")
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @NotNull(message = "Update timestamp cannot be null")
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

}
//
//{
//        "id":1,
//        "title":"My First Blog Post",
//        "content":"This is the content of my first blog post.",
//        "category":"Technology",
//        "tags":["Tech","Programming"],
//        "createdAt":"2021-09-01T12:00:00Z",
//        "updatedAt":"2021-09-01T12:00:00Z"
//        }