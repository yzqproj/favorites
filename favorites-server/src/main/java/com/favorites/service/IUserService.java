package com.favorites.service;

import com.favorites.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yzq
 * @since 2022-01-20
 */
public interface IUserService extends IService<User> {

    User findByUsernameOrEmail(String username, String username1);

    User findByEmail(String email);

    User findByUsername(String username);

    void setNewPassword(String pwd, String email);

    void setIntroduction(String introduction, String email);

    void setUsername(String username, String email);

    void setProfilePicture(String savePath, Long id);

    void setBackgroundPicture(String savePath, Long id);
}
