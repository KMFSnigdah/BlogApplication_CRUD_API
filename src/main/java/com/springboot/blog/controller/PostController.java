package com.springboot.blog.controller;

import com.springboot.blog.DTO.PostDto;
import com.springboot.blog.response.CustomResponse;
import com.springboot.blog.response.ResponseHandler;
import com.springboot.blog.service.IPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.springboot.blog.utils.AppConstants.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final IPostService postService;
    public PostController(IPostService postService) {
        this.postService = postService;
    }

    // Create a new Post
    // http://localhost:8080/api/posts/create
    @PostMapping("/create")
    public ResponseEntity<Object> createPost(@RequestBody PostDto postDto){
        //return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
       PostDto createdPost = postService.createPost(postDto);
        return ResponseHandler.generateResponse("Successfully Created", HttpStatus.OK, createdPost);
    }

    // Get All Post
    // http://localhost:8080/api/posts/getAll
    // http://localhost:8080/api/posts/getAll?pageNo=1&pageSize=10&sortBy=title&sortDir=asc
    @GetMapping("/getAll")
    public ResponseEntity<Object> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir
            ){
        CustomResponse customResponse = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return ResponseHandler.generateResponse("Fetch All Data Successfully", HttpStatus.OK,customResponse);
    }

    // Get Post by ID
    // http://localhost:8080/api/posts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Object> getPostById(@PathVariable(name = "id") long id){
        PostDto postDto = postService.getPostById(id);
        return ResponseHandler.generateResponse("Fetch Post Successfully", HttpStatus.OK,postDto);
    }

    // Update post by ID
    // http://localhost:8080/api/posts/update/{id}
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updatePost(@RequestBody PostDto postDto,@PathVariable(name = "id") long id){

       PostDto postResponse =  postService.updatePost(postDto, id);
       return ResponseHandler.generateResponse("Post updated successfully", HttpStatus.OK,postResponse);
    }

    // Delete a post by its id
    // http://localhost:8080/api/posts/delete/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable(name = "id") long id){
        postService.deletePostById(id);
        return ResponseHandler.generateResponse("Delete Successfully", HttpStatus.OK);
    }

}
