package com.favorites.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 消息
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
public class Notice extends Entitys implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Long userId;
	@Column(nullable = true)
	private String collectId;
	@Column(nullable = false)
	private String type;
	@Column(nullable = true)
	private String operId;
	@Column(nullable = false)
	private String readed;
	@Column(nullable = false)
	private Timestamp createTime;


}