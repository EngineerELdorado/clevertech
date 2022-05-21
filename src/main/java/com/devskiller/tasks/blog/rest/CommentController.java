package com.devskiller.tasks.blog.rest;

import com.devskiller.tasks.blog.model.dto.response.CommentDto;
import com.devskiller.tasks.blog.model.dto.request.NewCommentDto;
import com.devskiller.tasks.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@PostMapping(value = "/{id}/comments", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> addComment(@PathVariable("id") Long postId, @Valid @RequestBody NewCommentDto request) {

		return new ResponseEntity<>(commentService.addComment(postId, request), CREATED);
	}

	@GetMapping(value = "/{id}/comments", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CommentDto>> getComments(@PathVariable("id") Long postId) {

		return new ResponseEntity<>(commentService.getCommentsForPost(postId), OK);
	}
}
