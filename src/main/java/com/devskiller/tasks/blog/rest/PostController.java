package com.devskiller.tasks.blog.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devskiller.tasks.blog.model.dto.response.PostDto;
import com.devskiller.tasks.blog.service.PostService;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PostDto> getPost(@PathVariable Long id) {

		return new ResponseEntity<>(postService.getPost(id), HttpStatus.OK);
	}
}
