package com.favorites.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import nonapi.io.github.classgraph.json.Id;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 属性设置
 *
 * @author DingYS
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Config implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private Long userId;

    private String defaultFavorties;

    private String defaultCollectType;

    private String defaultModel;

    private Timestamp createTime;

    private Timestamp lastModifyTime;

    private String collectTypeName;

    private String modelName;


    //public String getCollectTypeName() {
    //    return defaultCollectType.equals("private") ? "私密" : "公开";
    //}
	//
    //public void setCollectTypeName(String collectTypeName) {
    //    this.collectTypeName = collectTypeName;
    //}
	//
    //public String getModelName() {
    //    return defaultModel.equals("simple") ? "简单" : "专业";
    //}
	//
    //public void setModelName(String modelName) {
    //    this.modelName = modelName;
    //}

}