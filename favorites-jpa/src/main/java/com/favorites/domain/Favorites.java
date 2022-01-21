package com.favorites.domain;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

/**
 * 收藏夹
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
public class Favorites extends Entitys implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private Long userId;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private Long count;
	@Column(nullable = false)
	private Timestamp createTime;
	@Column(nullable = false)
	private Timestamp lastModifyTime;
	@Column(nullable = false)
	private Long publicCount;
	 
}