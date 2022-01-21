package com.favorites.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import com.favorites.domain.enums.CollectType;
import com.favorites.domain.enums.IsDelete;
import lombok.*;

/**
 * 收集
 *
 * @author yanni
 * @date 2022/01/21
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Collect  implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Long userId;
	@Column(nullable = false)
	private Long favoritesId;
	@Column(nullable = false, columnDefinition = "varchar(600)")
	private String url;
	@Column(nullable = false)
	private String title;
	@Column(nullable = true, length = 65535, columnDefinition = "Text")
	private String description;
	@Column(nullable = true,columnDefinition = "varchar(300)")
	private String logoUrl;
	@Column(nullable = true)
	private String charset;
	@Enumerated(EnumType.STRING) 
	@Column(nullable = true)
	private CollectType type;
	@Column(nullable = true)
	private String remark;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING) 
	private IsDelete isDelete;
	@Column(nullable = false)
	private Timestamp createTime;
	@Column(nullable = false)
	private Timestamp lastModifyTime;
	@Column(nullable = true)
	private String category;
	@Transient
	private String collectTime;
	@Transient
	private String newFavorites;

}