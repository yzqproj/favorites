package com.favorites.entity.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yanni
 * @date time 2022/1/20 16:44
 * @modified By:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
    private String Username;
    private String email;
    private String password;
}
