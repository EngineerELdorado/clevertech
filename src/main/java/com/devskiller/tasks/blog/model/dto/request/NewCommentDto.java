package com.devskiller.tasks.blog.model.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class NewCommentDto {

	@NotBlank(message = "Please provide the author name")
	private String author;

	@NotBlank(message = "Please provide the content")
	private String content;
}
