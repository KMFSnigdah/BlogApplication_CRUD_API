package com.springboot.blog.service.impl;

import com.springboot.blog.DTO.CommentDto;
import com.springboot.blog.DTO.PostDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.response.CustomResponse;
import com.springboot.blog.response.PaginationResponse;
import com.springboot.blog.service.IPostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements IPostService {

    private final PostRepository postRepository;
    private final ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //Convert DTO to entity
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);

        // convert Entity inito DTO
        PostDto postResponse = mapToDto(newPost);
        return postResponse;
    }

    @Override
    public CustomResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        //get content for page object
        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content = listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        CustomResponse<PostDto> customResponse = new CustomResponse<>();
        customResponse.setContent(content);

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setPageNumber(posts.getNumber());
        paginationResponse.setPageSize(posts.getSize());
        paginationResponse.setTotalElements(posts.getTotalElements());
        paginationResponse.setTotalPages(posts.getTotalPages());
        paginationResponse.setHasNext(!posts.isLast());
        paginationResponse.setHasPrevious(posts.getNumber() > 0);

        customResponse.setPagination(paginationResponse);

        return customResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        // get post bby id from the database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        // get post by id from the database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    // convert Entity into DTO
    private PostDto mapToDto(Post post){
       // PostDto postDto = mapper.map(post, PostDto.class);

        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());

        Set<CommentDto> commentDtos = post.getComments().stream()
                .map(this::mapCommentToDto) // Use the existing method to map Comment to CommentDto
                .collect(Collectors.toSet());

        postDto.setComments(commentDtos);
        return  postDto;
    }

    // convert DTO to Entity
    private Post mapToEntity(PostDto postDto){
        //Post post = mapper.map(postDto, Post.class);
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return  post;
    }

    private CommentDto mapCommentToDto(Comment comment){
        return mapper.map(comment, CommentDto.class);
    }
}
