package com.favorites.domain;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

/**
 * 点赞
 * 
 * @author DingYS
 * 
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Praise extends Entitys implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Long collectId;
	@Column(nullable = false)
	private Long userId;
	@Column(nullable = false)
	private Timestamp createTime;

}