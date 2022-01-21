package com.favorites.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 属性设置
 * 
 * @author DingYS
 * 
 */
@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Config extends Entitys implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private Long userId;
	@Column(nullable = false)
	private String defaultFavorties;
	@Column(nullable = false)
	private String defaultCollectType;
	@Column(nullable = false)
	private String defaultModel;
	@Column(nullable = false)
	private Timestamp createTime;
	@Column(nullable = false)
	private Timestamp lastModifyTime;
	@Transient
	private String collectTypeName;
	@Transient
	private String modelName;


}