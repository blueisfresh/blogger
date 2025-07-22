package com.blueisfresh.blogger.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogResponseDto {
    private Long id;
    private String title;
    private String content;
    private String category;
    private Set<TagResponseDto> tags;
    private Instant createdAt;
    private Instant updatedAt;
}
