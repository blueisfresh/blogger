package com.blueisfresh.blogger.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    @Column(name = "tag_name", nullable = false, length = 255)
    private String tagName;

    // 'mappedBy' indicates that the Blog entity owns the relationship via its 'tags' field
    @JsonBackReference
    @ManyToMany(mappedBy = "tags")
    @EqualsAndHashCode.Exclude // SOLVED THE BUG
    @ToString.Exclude // SOLVED THE BUG
    private Set<Blog> blogs = new HashSet<>();

    public Tag(String tagName) {
        this.tagName = tagName;
    }
}