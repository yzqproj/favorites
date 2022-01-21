package com.favorites.mapper;

import com.favorites.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yzq
 * @since 2022-01-20
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 找到用户名或电子邮件
     *
     * @param username  用户名
     * @param username1 username1
     * @return {@link User}
     */
    User findByUsernameOrEmail(String username, String username1);

    User findByEmail(String email);

    User findByUsername(String username);

    void setNewPassword(String pwd, String email);

    void setIntroduction(String introduction, String email);

    void setUsername(String username, String email);

    void setProfilePicture(String savePath, Long id);

    void setBackgroundPicture(String savePath, Long id);
}
