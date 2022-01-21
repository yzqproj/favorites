package com.favorites.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.favorites.entity.enums.CollectType;
import com.favorites.entity.enums.IsDelete;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Collect  implements Serializable {

	private static final long serialVersionUID = 1L;
@TableId(value = "id",type = IdType.ASSIGN_ID)
	private Long id;
	 
	private Long userId;
	 
	private Long favoritesId;
	private String url;
	 
	private String title;
	private String description;
	private String logoUrl;
	 
	private String charset;

	private CollectType type;
	 
	private String remark;
	 
	private IsDelete isDelete;
	 
	private Timestamp createTime;
	 
	private Timestamp lastModifyTime;
	 
	private String category;
	 
	private String collectTime;
	 
	private String newFavorites;

}