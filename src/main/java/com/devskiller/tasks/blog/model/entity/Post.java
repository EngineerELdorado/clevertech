package com.devskiller.tasks.blog.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

	@Id
	@GeneratedValue
	private Long id;
	private String title;

	@Column(length = 4096)
	private String content;

	private LocalDateTime creationDate;
}
