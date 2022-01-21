package com.favorites.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 用户
 *
 * @author DingYS
 */

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder

@AllArgsConstructor
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String profilePicture;
    private String introduction;
    private Timestamp createTime;
    private Timestamp lastModifyTime;
    private String outDate;
    private String validateCode;
    private String backgroundPicture;


}