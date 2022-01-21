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
 * 用户反馈javaBean类
 * Created by chenzhimin on 2017/2/23.
 */
 @Data
 @Builder
 @NoArgsConstructor
 @AllArgsConstructor
public class Feedback    implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
     @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;
     
    private Long userId;
     
    private String feedbackAdvice;
     
    private String feedbackName;
     
    private String phone;
     
    private Timestamp createTime;
     
    private Timestamp lastModifyTime;

}
