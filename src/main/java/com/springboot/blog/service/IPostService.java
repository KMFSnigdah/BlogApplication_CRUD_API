package com.springboot.blog.service;

import com.springboot.blog.DTO.PostDto;
import com.springboot.blog.response.CustomResponse;


public interface IPostService {
    PostDto createPost(PostDto postDto);

    CustomResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePostById(long id);
}
