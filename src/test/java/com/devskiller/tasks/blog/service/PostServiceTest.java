package com.devskiller.tasks.blog.service;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.devskiller.tasks.blog.model.entity.Post;
import com.devskiller.tasks.blog.model.dto.response.PostDto;
import com.devskiller.tasks.blog.repository.PostRepository;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {

	@Autowired
	PostRepository postRepository;

	@Autowired
	PostService postService;

	@Test
	public void shouldReturnCreatedPost() {
		Post post = new Post();
		post.setTitle("Test title");
		post.setContent("Test content");
		LocalDateTime creationDate = LocalDateTime.of(2018, 5, 20, 20, 51, 16);
		post.setCreationDate(creationDate);
		postRepository.save(post);

		PostDto postDto = postService.getPost(post.getId());

		assertNotNull("Post shouldn't be null", postDto);
		assertEquals(postDto.getContent(), "Test content");
		assertEquals(postDto.getTitle(), "Test title");
		assertEquals(postDto.getCreationDate(), creationDate);
	}

	@Test
	public void shouldReturnNullForNotExistingPost() {
		PostDto postDto = postService.getPost(123L);

		assertNull(postDto);
	}
}
