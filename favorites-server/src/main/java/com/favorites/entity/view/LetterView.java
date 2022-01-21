package com.favorites.entity.view;

/**
 * Created by DingYS on 2017/3/8.
 */
public interface LetterView {
    Long getId();
    Long getSendUserId();
    String getSendUsername();
    String getProfilePicture();
    String getContent();
    Long getCreateTime();
    Long getPid();
    String getType();
}
