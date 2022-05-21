package com.devskiller.tasks.blog.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CommentDto {

	private Long id;
	private String comment;
	private String author;
	private LocalDateTime creationDate;
}
