package com.favorites.domain.view;

import java.sql.Timestamp;

public interface CollectView{
	Long getId();
	Long getUserId();
	String getProfilePicture();
	String getTitle();
	String getType();
	String getUrl();
	String getLogoUrl();
	String getRemark();
	String getDescription();
	Timestamp getLastModifyTime();
	Timestamp getCreateTime();
	String getUserName();
	Long getFavoritesId();
	String getFavoriteName();
	String getOperId();
}