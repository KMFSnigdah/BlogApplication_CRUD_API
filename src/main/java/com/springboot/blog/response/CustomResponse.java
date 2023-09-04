package com.springboot.blog.response;

import com.springboot.blog.DTO.PostDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomResponse<T> {
    private List<T> content;
    private PaginationResponse pagination;
}
