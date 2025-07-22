// src/main/java/com/blueisfresh/blogger/dto/BlogUpdateDto.java
package com.blueisfresh.blogger.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogUpdateDto {
    // Fields can be Null
    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    @Size(min = 10, message = "Content must be at least 10 characters long")
    private String content;

    @NotBlank(message = "Category cannot be blank")
    @Size(max = 50, message = "Category must not exceed 50 characters")
    private String category;

    // For tags during update, you might replace all existing tags or add/remove specific ones.
    // A common approach is to provide the full set of desired tag names.
    @Size(min = 1, message = "A blog must have at least one tag name")
    private Set<String> tagNames; // Use Set to avoid duplicate tag names
}