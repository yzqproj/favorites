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
 *  浏览记录entity
 * Created by chenzhimin on 2017/1/5.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LookRecord   implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
     @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;
     
    private Long userId;
     
    private Long collectId;
     
    private Timestamp createTime;
     
    private Timestamp lastModifyTime;

}
