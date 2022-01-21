package com.favorites.entity.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LetterSummary implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long sendUserId;
    private String sendUsername;
    private String profilePicture;
    private String content;
    private String createTime;
    private Long pid;
    private String type;

}
