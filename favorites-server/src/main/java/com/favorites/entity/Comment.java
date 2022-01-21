package com.favorites.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 评论
 * 
 * @author DingYS
 * 
 */

 @Data
 @Builder
 @NoArgsConstructor
 @AllArgsConstructor
public class Comment   implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	 @TableId(value = "id",type = IdType.ASSIGN_ID)
	private Long id;
	 
	private Long collectId;
	private String content;
	 
	private Long userId;
	 
	private Long replyUserId;
	 
	private Timestamp createTime;
	 
	private String commentTime;
	 
	private String Username;
	 
	private String replyUsername;
	 
	private String profilePicture;

}