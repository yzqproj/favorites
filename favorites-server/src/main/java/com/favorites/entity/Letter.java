package com.favorites.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.favorites.entity.enums.LetterType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 私信
 * 
 * @author DingYS
 * 
 */
 @Data
 @Builder
 @NoArgsConstructor
 @AllArgsConstructor
public class Letter   implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	 @TableId(value = "id",type = IdType.ASSIGN_ID)
	private Long id;
	 
	private Long sendUserId;
	private String content;
	 
	private Long receiveUserId;
	 
	private Long pid;

	private LetterType type;
	 
	private Timestamp createTime;
	 
	private String sendType;

}