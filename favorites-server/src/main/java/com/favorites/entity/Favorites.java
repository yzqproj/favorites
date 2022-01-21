package com.favorites.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 收藏夹
 *
 * @author DingYS
 */
 @Data
 @Builder
 @NoArgsConstructor
 @AllArgsConstructor
public class Favorites   implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
   @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;
     
    private Long userId;
     
    private String name;
     
    private Long count;
     
    private Timestamp createTime;
     
    private Timestamp lastModifyTime;
     
    private Long publicCount;


}