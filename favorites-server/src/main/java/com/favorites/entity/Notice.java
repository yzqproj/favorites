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
 * 消息
 * 
 * @author DingYS
 * 
 */
 @Data
 @Builder
 @NoArgsConstructor
 @AllArgsConstructor
public class Notice  implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	 @TableId(value = "id",type = IdType.ASSIGN_ID)
	private Long id;
	 
	private Long userId;
	 
	private String collectId;
	 
	private String type;
	 
	private String operId;
	 
	private String readed;
	 
	private Timestamp createTime;

}