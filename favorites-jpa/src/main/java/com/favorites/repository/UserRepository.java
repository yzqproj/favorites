package com.favorites.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.favorites.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 找到用户名
     *
     * @param username 用户名
     * @return {@link User}
     */
    User findByUsername(String username);

    /**
     * 找到用户名或电子邮件
     *
     * @param username 用户名
     * @param email    电子邮件
     * @return {@link User}
     */
    User findByUsernameOrEmail(String username, String email);

    /**
     * 通过电子邮件找到
     *
     * @param email 电子邮件
     * @return {@link User}
     */
    User findByEmail(String email);

    User findById(long  id);

    @Modifying(clearAutomatically=true)
    @Transactional
    @Query("update User set outDate=:outDate, validataCode=:validataCode where email=:email") 
    int setOutDateAndValidataCode(@Param("outDate") String outDate, @Param("validataCode") String validataCode, @Param("email") String email);
    
    @Modifying(clearAutomatically=true)
    @Transactional
    @Query("update User set password=:passWord where email=:email")
    int setNewPassword(@Param("passWord") String passWord, @Param("email") String email);

    /**
     * 设置介绍
     *
     * @param introduction 介绍
     * @param email        电子邮件
     * @return int
     */
    @Modifying(clearAutomatically=true)
    @Transactional
    @Query("update User set introduction=:introduction where email=:email") 
    int setIntroduction(@Param("introduction") String introduction, @Param("email") String email);

    /**
     * 设置用户名
     *
     * @param username 用户名
     * @param email    电子邮件
     * @return int
     */
    @Modifying(clearAutomatically=true)
    @Transactional
    @Query("update User set username=:username where email=:email")
    int setUsername(@Param("username") String username, @Param("email") String email);

    /**
     * 设置配置文件图片
     *
     * @param profilePicture 资料图片
     * @param id             id
     * @return int
     */
    @Modifying(clearAutomatically=true)
    @Transactional
    @Query("update User set profilePicture=:profilePicture where id=:id") 
    int setProfilePicture(@Param("profilePicture") String profilePicture, @Param("id") Long id);

//    @Query("from User u where u.name=:name")
//    User findUser(@Param("name") String name);

    /**
     * 设置背景图片
     *
     * @param backgroundPicture 背景图片
     * @param id                id
     * @return int
     */
    @Modifying(clearAutomatically=true)
    @Transactional
    @Query("update User set backgroundPicture=:backgroundPicture where id=:id")
    int setBackgroundPicture(@Param("backgroundPicture") String backgroundPicture, @Param("id") Long id);
}