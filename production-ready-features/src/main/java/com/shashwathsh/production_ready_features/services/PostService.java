package com.shashwathsh.production_ready_features.services;

import com.shashwathsh.production_ready_features.dtos.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    List<PostDto>  getAllPosts();
    PostDto createNewPost(PostDto inputPost);

    PostDto getPostById(Long id);

    PostDto updatePost(Long postId, PostDto postDto);
}
