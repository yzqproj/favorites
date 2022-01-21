package com.favorites.entity.view;

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
public class CollectSummary implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long userId;
    private String profilePicture;
    private Long favoritesId;
    private String url;
    private String title;
    private String description;
    private String logoUrl;
    private String type;
    private String remark;
    private Timestamp lastModifyTime;
    private String username;
    private String favoriteName;
    private String collectTime;
    private String newFavorites;
    private Long praiseCount;
    private Long commentCount;
    private boolean isPraise;


}