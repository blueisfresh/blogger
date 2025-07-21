package com.blueisfresh.blogger.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//@ToString(exclude = {"blogs"})
@Table(name = "tbl_tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include // Include id in equals/hashCode
    @Column(name = "tag_id")
    private Long id;


    @NotBlank(message = "Name cannot be blank")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    @Column(name = "tag_name", nullable = false, length = 255)
    private String name;

    // 'mappedBy' indicates that the Blog entity owns the relationship via its 'tags' field
    @ManyToMany(mappedBy = "tags")

//    @EqualsAndHashCode.Exclude // Exclude from equals/hashCode
//    @ToString.Exclude // Exclude from toString
    private Set<Blog> blogs = new HashSet<>();
}