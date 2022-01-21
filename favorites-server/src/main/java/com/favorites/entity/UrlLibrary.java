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
 * Created by DingYS on 2016/12/29.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlLibrary   implements Serializable{

    @Id
     @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;
    private String url;
    private String logoUrl;
    private int count;

}
