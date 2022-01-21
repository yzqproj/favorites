package com.favorites.domain.view;

import com.favorites.domain.enums.LetterType;

import java.sql.Timestamp;

/**
 * Created by DingYS on 2017/3/8.
 */
public interface LetterView {
    Long getId();
    Long getSendUserId();
    String getSendUserName();
    String getProfilePicture();
    String getContent();
   Timestamp getCreateTime();
    Long getPid();
    String getType();
}
