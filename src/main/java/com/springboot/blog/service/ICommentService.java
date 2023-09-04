package com.springboot.blog.service;

import com.springboot.blog.DTO.CommentDto;

import java.util.List;

public interface ICommentService {
    CommentDto CreateCommment(long postId, CommentDto commentDto);
    List<CommentDto> getCommentByPostId(long postId);
    CommentDto getCommentById(long postId, long commentId);
    CommentDto updateComment(long postId, long commentId, CommentDto commentDto);
    void deleteComment(long postId, long commentId);
}
