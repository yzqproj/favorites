package com.favorites.domain.view;

import java.sql.Timestamp;

/**
*@InterfaceName: CommentView
*@Description: 
*@author YY 
*@date 2016年9月1日  下午4:03:20
*@version 1.0
*/
public interface CommentView {
	Long getUserId();
	String getUserName();
	String getProfilePicture();
	String getContent();
	Timestamp getCreateTime();
	Long getReplyUserId();
}
