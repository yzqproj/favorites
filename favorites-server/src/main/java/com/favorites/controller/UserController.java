package com.favorites.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.favorites.comm.Const;
import com.favorites.comm.aop.LoggerManage;
import com.favorites.entity.Config;
import com.favorites.entity.Favorites;
import com.favorites.entity.User;
import com.favorites.entity.result.ExceptionMsg;
import com.favorites.entity.result.Response;
import com.favorites.entity.result.ResponseData;
import com.favorites.entity.view.UserVo;
import com.favorites.service.IConfigService;
import com.favorites.service.IFavoritesService;
import com.favorites.service.IFollowService;
import com.favorites.service.IUserService;
import com.favorites.utils.DateUtils;
import com.favorites.utils.FileUtil;
import com.favorites.utils.MD5Util;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yzq
 * @since 2022-01-20
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController extends BaseController {


    @Value("${favorites.static.url}")
    private String staticUrl;
    @Value("${favorites.file.profile-pic}")
    private String profilePic;
    @Value("${favorites.file.background-pic}")
    private String backgroundPic;


    private final IFollowService followService;
    private final IUserService userService;
    private final IConfigService configService;
    private final IFavoritesService favoritesService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @LoggerManage(description = "登陆")
    public ResponseData login(User user, HttpServletResponse response) {
        try {
            //这里不是bug，前端Username有可能是邮箱也有可能是昵称。
            User loginUser = userService.findByUsernameOrEmail(user.getUsername(), user.getUsername());
            if (loginUser == null) {
                return new ResponseData(ExceptionMsg.LoginNameNotExists);
            } else if (!loginUser.getPassword().equals(getPwd(user.getPassword()))) {
                return new ResponseData(ExceptionMsg.LoginNameOrPassWordError);
            }
            Cookie cookie = new Cookie(Const.LOGIN_SESSION_KEY, cookieSign(loginUser.getId().toString()));
            cookie.setMaxAge(Const.COOKIE_TIMEOUT);
            cookie.setPath("/");
            response.addCookie(cookie);
            getSession().setAttribute(Const.LOGIN_SESSION_KEY, loginUser);
            String preUrl = "/";
            if (null != getSession().getAttribute(Const.LAST_REFERER)) {
                preUrl = String.valueOf(getSession().getAttribute(Const.LAST_REFERER));
                if (preUrl.indexOf("/collect?") < 0 && preUrl.indexOf("/lookAround/standard/") < 0
                        && preUrl.indexOf("/lookAround/simple/") < 0) {
                    preUrl = "/";
                }
            }
            if (preUrl.indexOf("/lookAround/standard/") >= 0) {
                preUrl = "/lookAround/standard/ALL";
            }
            if (preUrl.indexOf("/lookAround/simple/") >= 0) {
                preUrl = "/lookAround/simple/ALL";
            }
            return new ResponseData(ExceptionMsg.SUCCESS, preUrl);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("login failed, ", e);
            return new ResponseData(ExceptionMsg.FAILED);
        }
    }

    @PostMapping(value = "/reg")
    @LoggerManage(description = "注册")
    public Object create(@RequestBody UserVo userVo) {
        try {
            User registUser = userService.findByEmail(userVo.getEmail());
            if (null != registUser) {
                return result(ExceptionMsg.EmailUsed);
            }
            User UsernameUser = userService.findByUsername(userVo.getUsername());
            if (null != UsernameUser) {
                return result(ExceptionMsg.UsernameUsed);
            }
            User user = new User();
            user.setUsername(userVo.getUsername());
            user.setEmail(userVo.getEmail());
            user.setPassword(getPwd(userVo.getPassword()));
            user.setCreateTime(new Timestamp(System.currentTimeMillis()));
            user.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
            user.setProfilePicture("img/favicon.png");
            userService.save(user);
            // 添加默认收藏夹
            Favorites favorites = favoritesService.saveFavorites(user.getId(), "未读列表");
            // 添加默认属性设置
            configService.saveConfig(user.getId(), String.valueOf(favorites.getId()));
            getSession().setAttribute(Const.LOGIN_SESSION_KEY, user);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("create user failed, ", e);
            return result(ExceptionMsg.FAILED);
        }
        return result();
    }

    @PostMapping(value = "/getFavorites" )
    @LoggerManage(description = "获取收藏夹")
    public List<Favorites> getFavorites() {
        List<Favorites> favorites = null;
        try {
            favorites = favoritesService.findByUserIdOrderByLastModifyTimeDesc(getUserId());
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("getFavorites failed, ", e);
        }
        return favorites;
    }

    /**
     * 获取属性设置
     *
     * @return
     */
    @RequestMapping(value = "/getConfig", method = RequestMethod.POST)
    @LoggerManage(description = "获取属性设置")
    public Config getConfig() {
        Config config = new Config();
        try {
            LambdaQueryWrapper<Config> queryWrapper = Wrappers.<Config>lambdaQuery().eq(Config::getUserId, getUserId());
            config = configService.getOne(queryWrapper);
        } catch (Exception e) {
            logger.error("异常：", e);
        }
        return config;
    }

    /**
     * 属性修改
     *
     * @param id
     * @param type
     * @return
     */
    @RequestMapping(value = "/updateConfig", method = RequestMethod.POST)
    @LoggerManage(description = "属性修改")
    public Object updateConfig(Long id, String type, String defaultFavorites) {
        if (null != id && StringUtils.isNotBlank(type)) {
            try {
                configService.updateConfig(id, type, defaultFavorites);
            } catch (Exception e) {
                logger.error("属性修改异常：", e);
            }
        }
        return null;
    }

    @GetMapping(value = "/getFollows")
    @LoggerManage(description = "获取关注列表")
    public List<String> getFollows() {

        List<String> followList = followService.getFollowsByUserId( getUserId());
        return followList;
    }

    /**
     * 忘记密码-发送重置邮件
     *
     * @param email
     * @return
     */
    @RequestMapping(value = "/sendForgotPasswordEmail", method = RequestMethod.POST)
    @LoggerManage(description = "发送忘记密码邮件")
    public Response sendForgotPasswordEmail(String email) {
        try {
            User registUser = userService.findByEmail(email);
            if (null == registUser) {
                return result(ExceptionMsg.EmailNotRegister);
            }
            // 密钥
            String secretKey = UUID.randomUUID().toString();
            // 30分钟后过期
            Timestamp outDate = new Timestamp(System.currentTimeMillis() + 30 * 60 * 1000);
            long date = outDate.getTime() / 1000 * 1000;
            //userService.setOutDateAndValidataCode(outDate + "", secretKey, email);
            String key = email + "$" + date + "$" + secretKey;
            String digitalSignature = MD5Util.encrypt(key);// 数字签名

//            String resetPassHref = forgotpasswordUrl + "?sid="
//                    + digitalSignature + "&email=" + email;
//            String emailContent = MessageUtil.getMessage(mailContent, resetPassHref);
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//            helper.setFrom(mailFrom);
//            helper.setTo(email);
//            helper.setSubject(mailSubject);
//            helper.setText(emailContent, true);
//            mailSender.send(mimeMessage);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("sendForgotPasswordEmail failed, ", e);
            return result(ExceptionMsg.FAILED);
        }
        return result();
    }

    /**
     *
     * 忘记密码-设置新密码
     *
     * @param newpwd newpwd
     * @param email  电子邮件
     * @param sid    sid
     * @return {@link Response}
     */
    @RequestMapping(value = "/setNewPassword", method = RequestMethod.POST)
    @LoggerManage(description = "设置新密码")
    public Response setNewPassword(String newpwd, String email, String sid) {
        try {
            User user = userService.findByEmail(email);
            Timestamp outDate = Timestamp.valueOf(user.getOutDate());
            //表示已经过期
            if (outDate.getTime() <= System.currentTimeMillis()) {
                return result(ExceptionMsg.LinkOutdated);
            }
            String key = user.getEmail() + "$" + outDate.getTime() / 1000 * 1000 + "$" + user.getValidateCode();//数字签名
            String digitalSignature = MD5Util.encrypt(key);
            if (!digitalSignature.equals(sid)) {
                return result(ExceptionMsg.LinkOutdated);
            }
            userService.setNewPassword(getPwd(newpwd), email);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("setNewPassword failed, ", e);
            return result(ExceptionMsg.FAILED);
        }
        return result();
    }

    /**
     *
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return {@link Response}
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @LoggerManage(description = "修改密码")
    public Response updatePassword(String oldPassword, String newPassword) {
        try {
            User user = getUser();
            String password = user.getPassword();
            String newpwd = getPwd(newPassword);
            if (password.equals(getPwd(oldPassword))) {
                userService.setNewPassword(newpwd, user.getEmail());
                user.setPassword(newpwd);
                getSession().setAttribute(Const.LOGIN_SESSION_KEY, user);
            } else {
                return result(ExceptionMsg.PassWordError);
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("updatePassword failed, ", e);
            return result(ExceptionMsg.FAILED);
        }
        return result();
    }

    /**
     *
     * 修改个人简介
     *
     * @param introduction 介绍
     * @return {@link ResponseData}
     */
    @RequestMapping(value = "/updateIntroduction", method = RequestMethod.POST)
    @LoggerManage(description = "修改个人简介")
    public ResponseData updateIntroduction(String introduction) {
        try {
            User user = getUser();
            userService.setIntroduction(introduction, user.getEmail());
            user.setIntroduction(introduction);
            getSession().setAttribute(Const.LOGIN_SESSION_KEY, user);
            return new ResponseData(ExceptionMsg.SUCCESS, introduction);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("updateIntroduction failed, ", e);
            return new ResponseData(ExceptionMsg.FAILED);
        }
    }

    /**
     * 更新用户名
     *
     *
     * @param Username 用户名
     * @return {@link ResponseData}
     */
    @RequestMapping(value = "/updateUsername", method = RequestMethod.POST)
    @LoggerManage(description = "修改昵称")
    public ResponseData updateUsername(String Username) {
        try {
            User loginUser = getUser();
            if (Username.equals(loginUser.getUsername())) {
                return new ResponseData(ExceptionMsg.UsernameSame);
            }
            User user = userService.findByUsername(Username);
            if (null != user && user.getUsername().equals(Username)) {
                return new ResponseData(ExceptionMsg.UsernameUsed);
            }
            if (Username.length() > 12) {
                return new ResponseData(ExceptionMsg.UsernameLengthLimit);
            }
            userService.setUsername(Username, loginUser.getEmail());
            loginUser.setUsername(Username);
            getSession().setAttribute(Const.LOGIN_SESSION_KEY, loginUser);
            return new ResponseData(ExceptionMsg.SUCCESS, Username);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("updateUsername failed, ", e);
            return new ResponseData(ExceptionMsg.FAILED);
        }
    }

    /**
     * 上传头像
     *
     * @param dataUrl 数据url
     * @return {@link ResponseData}
     */
    @RequestMapping(value = "/uploadHeadPortrait", method = RequestMethod.POST)
    public ResponseData uploadHeadPortrait(String dataUrl) {
        logger.info("执行 上传头像 开始");
        try {
            String filePath = staticUrl + profilePic;
            String fileName = UUID.randomUUID().toString() + ".png";
            String savePath = profilePic + fileName;
            String image = dataUrl;
            String header = "data:image";
            String[] imageArr = image.split(",");
            if (imageArr[0].contains(header)) {
                image = imageArr[1];
                Base64.Decoder decoder = Base64.getDecoder();
                byte[] decodedBytes = decoder.decode(image);
                FileUtil.uploadFile(decodedBytes, filePath, fileName);
                User user = getUser();
                userService.setProfilePicture(savePath, user.getId());
                user.setProfilePicture(savePath);
                getSession().setAttribute(Const.LOGIN_SESSION_KEY, user);
            }
            logger.info("头像地址：" + savePath);
            logger.info("执行 上传头像 结束");
            return new ResponseData(ExceptionMsg.SUCCESS, savePath);
        } catch (Exception e) {
            logger.error("upload head portrait failed, ", e);
            return new ResponseData(ExceptionMsg.FAILED);
        }
    }

    /**
     * 上传背景
     *
     * @param dataUrl 数据url
     * @return {@link ResponseData}
     */
    @RequestMapping(value = "/uploadBackground", method = RequestMethod.POST)
    @LoggerManage(description = "上传背景")
    public ResponseData uploadBackground(String dataUrl) {
        try {
            String filePath = staticUrl + backgroundPic;
            String fileName = UUID.randomUUID() + ".png";
            String savePath = backgroundPic + fileName;
            String image = dataUrl;
            String header = "data:image";
            String[] imageArr = image.split(",");
            if (imageArr[0].contains(header)) {
                image = imageArr[1];
                Base64.Decoder decoder = Base64.getDecoder();
                byte[] decodedBytes = decoder.decode(image);
                FileUtil.uploadFile(decodedBytes, filePath, fileName);
                User user = getUser();
                userService.setBackgroundPicture(savePath, user.getId());
                user.setBackgroundPicture(savePath);
                getSession().setAttribute(Const.LOGIN_SESSION_KEY, user);
            }
            logger.info("背景地址：" + savePath);
            return new ResponseData(ExceptionMsg.SUCCESS, savePath);
        } catch (Exception e) {
            logger.error("upload background picture failed, ", e);
            return new ResponseData(ExceptionMsg.FAILED);
        }
    }
}

