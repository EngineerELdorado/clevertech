package com.devskiller.tasks.blog.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.devskiller.tasks.blog.exceptions.CleverTech404Exception;
import com.devskiller.tasks.blog.exceptions.CleverTech5xxException;
import com.devskiller.tasks.blog.model.entity.Comment;
import com.devskiller.tasks.blog.model.entity.Post;
import com.devskiller.tasks.blog.repository.CommentRepository;
import com.devskiller.tasks.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.devskiller.tasks.blog.model.dto.response.CommentDto;
import com.devskiller.tasks.blog.model.dto.request.NewCommentDto;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

	private final PostRepository postRepository;
	private final CommentRepository commentRepository;

	/**
	 * Returns a list of all comments for a blog post with passed id.
	 *
	 * @param postId id of the post
	 * @return list of comments sorted by creation date descending - most recent first
	 */
	public List<CommentDto> getCommentsForPost(Long postId) {

		List<Comment> comments = commentRepository.findByPostId(postId);

		return comments.stream().map(comment -> CommentDto.builder()
			.comment(comment.getContent())
			.author(comment.getAuthor())
			.id(comment.getId())
			.creationDate(comment.getCreationDate())
			.build()).collect(Collectors.toList());
	}

	/**
	 * Creates a new comment
	 *
	 * @param postId        the id of the post we want to add a comment to
	 * @param newCommentDto data of new comment
	 * @return id of the created comment
	 * @throws IllegalArgumentException if there is no blog post for passed newCommentDto.postId
	 *                                  I think throwing an IllegalArgumentException is not the best solution for this case. Maybe we need to throw a
	 *                                  custom exception with status code 404 attached to it. That's what I am doing there
	 */

	//here there is nothing that really requires the transactional behavior but I put it to demonstrate that it could be
	//needed in the future when the requirement get a bit complex on the entities to be updated on the DB
	@Transactional
	public Long addComment(Long postId, NewCommentDto newCommentDto) {

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new CleverTech404Exception(String.format("post with id %s not found", postId)));

		Comment comment = Comment.builder()
			.author(newCommentDto.getAuthor())
			.content(newCommentDto.getContent())
			.post(post)
			.creationDate(LocalDateTime.now())
			.build();

		// This exception handling here is not necessary but I just put it to show that
		// anytime we call an external resource (in this case a DB. it could be an external API) anything can go wrong.
		// So we have to handle it gracefully instead of showing the user some weird exception stuff.
		try {
			commentRepository.save(comment);
		} catch (Exception e) {

			String errorMessageToLog = String.format("An error has occurred while trying to persist comment %s",
				comment.getContent());
			log.error(errorMessageToLog, e.getCause());

			// TODO: 5/21/22 it would be good to notify the engineering team so that they investigate the issue
			//mailService.sendMail("engineering@clevertech.com", "URGENT PRODUCTION ERROR", errorMessageToLog)

			throw new CleverTech5xxException("Oops... it's not you. it's us. Something went wrong. We are working on it." +
				"Please try again after some time. If it still fails please contact our support team");
		}

		return comment.getId();
	}
}
