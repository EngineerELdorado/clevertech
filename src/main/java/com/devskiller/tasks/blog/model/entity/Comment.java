package com.devskiller.tasks.blog.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

	@Id
	@GeneratedValue
	private Long id;
	private String author;
	private String content;
	private LocalDateTime creationDate;

	@ManyToOne
	private Post post;
}
