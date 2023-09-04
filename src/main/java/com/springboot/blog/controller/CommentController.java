package com.springboot.blog.controller;

import com.springboot.blog.DTO.CommentDto;
import com.springboot.blog.response.ResponseHandler;
import com.springboot.blog.service.ICommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final ICommentService commentService;

    public CommentController(ICommentService commentService){
        this.commentService = commentService;
    }

    // Create a new comment
    // http://localhost:8080/api/post/1/comments
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<Object> createComment(@PathVariable(value = "postId") long postId,
                                                @RequestBody CommentDto commentDto){
        CommentDto response = commentService.CreateCommment(postId, commentDto);
        return ResponseHandler.generateResponse("Successfully Created", HttpStatus.CREATED, response);
    }

    // Get comments by post id
    // http://localhost:8080/api/post/1/comments
    @GetMapping("/post/{postId}/comments")
    public ResponseEntity<Object> getCommentByPostId(@PathVariable(value = "postId") long postId){
        List<CommentDto> comments = commentService.getCommentByPostId(postId);
        return  ResponseHandler.generateResponse("Fetch All Successfully", HttpStatus.OK, comments);
    }

    // Get comments by CommentId
    // http://localhost:8080/api/posts/1/comments/1
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<Object> getCommentBYId(@PathVariable(value = "postId") long postId,
                                                     @PathVariable(value = "id") long commentId){
        CommentDto commentDto = commentService.getCommentById(postId, commentId);
        return ResponseHandler.generateResponse("Fetch Data SuccessFully",HttpStatus.OK, commentDto);
    }

    // Update a comment
    // http://localhost:8080/api/posts/1/comments/1
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<Object> updateComment(@PathVariable(value = "postId") long postId,
                                                    @PathVariable(value = "id") long commentId,
                                                    @RequestBody CommentDto commentDto){
       CommentDto updatedComment = commentService.updateComment(postId,commentId, commentDto);
       return ResponseHandler.generateResponse("Updated Successfully",HttpStatus.OK,updatedComment);
    }

    // Delete a comment
    // http://localhost:8080/api/posts/1/comments/1
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<Object> deleteComment(@PathVariable(value = "postId") long postId,
                                                @PathVariable(value = "id") long commentId){
        commentService.deleteComment(postId, commentId);
        return ResponseHandler.generateResponse("Resource Deleted Successfully", HttpStatus.NO_CONTENT);
    }
}
