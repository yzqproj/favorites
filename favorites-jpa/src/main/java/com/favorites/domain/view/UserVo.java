package com.favorites.domain.view;

import lombok.Builder;
import lombok.Data;

/**
 * @author yanni
 * @date time 2022/1/21 1:51
 * @modified By:
 */
@Data
@Builder

public class UserVo {
    private String username;
    private String password;
    private String email;
}
