package com.favorites.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;

import java.io.Serializable;

/**
 * 随便看看右侧关注使用
 * Created by chenzhimin on 2017/1/19.
 */
 @Data
 @Builder
 @NoArgsConstructor
 @AllArgsConstructor
public class UserIsFollow implements Serializable{
    @Id
     @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;
    private String Username;
    private String profilePicture;
    private String isFollow;

}
