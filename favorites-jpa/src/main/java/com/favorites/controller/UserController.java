package com.favorites.controller;

import cn.hutool.core.lang.Console;
import com.favorites.comm.Const;
import com.favorites.comm.aop.LoggerManage;
import com.favorites.domain.Config;
import com.favorites.domain.Favorites;
import com.favorites.domain.User;
import com.favorites.domain.result.ExceptionMsg;
import com.favorites.domain.result.Response;
import com.favorites.domain.result.ResponseData;
import com.favorites.domain.view.UserVo;
import com.favorites.repository.ConfigRepository;
import com.favorites.repository.FavoritesRepository;
import com.favorites.repository.FollowRepository;
import com.favorites.repository.UserRepository;
import com.favorites.service.ConfigService;
import com.favorites.service.FavoritesService;
import com.favorites.utils.FileUtil;
import com.favorites.utils.MD5Util;
import com.favorites.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController extends BaseController {
    
    private final UserRepository userRepository;
    private final ConfigService configService;
    private final FavoritesService favoritesService;
    private final JavaMailSender mailSender;
    
    private final ConfigRepository configRepository;
    
    private final FollowRepository followRepository;
    
    private final FavoritesRepository favoritesRepository;
    @Value("${spring.mail.username}")
    private String mailFrom;
    @Value("${mail.subject.forgotpassword}")
    private String mailSubject;
    @Value("${mail.content.forgotpassword}")
    private String mailContent;
    @Value("${forgotpassword.url}")
    private String forgotpasswordUrl;
    @Value("${static.url}")
    private String staticUrl;
    @Value("${file.profilepictures.url}")
    private String fileProfilepicturesUrl;
    @Value("${file.backgroundpictures.url}")
    private String fileBackgroundpicturesUrl;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @LoggerManage(description = "登陆")
    public ResponseData login(@ParameterObject UserVo user, HttpServletResponse response) {
        try {
            //这里不是bug，前端username有可能是邮箱也有可能是昵称。
            User loginUser = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
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
                if (!preUrl.contains("/collect?") && !preUrl.contains("/lookAround/standard/")
                        && !preUrl.contains("/lookAround/simple/")) {
                    preUrl = "/";
                }
            }
            if (preUrl.contains("/lookAround/standard/")) {
                preUrl = "/lookAround/standard/ALL";
            }
            if (preUrl.contains("/lookAround/simple/")) {
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
    public Response create( @ParameterObject UserVo userVo) {
        try {
            Console.log(userRepository.findByEmail(userVo.getEmail()));
            if (null != userRepository.findByEmail(userVo.getEmail())) {
                return result(ExceptionMsg.EmailUsed);
            }
            if (null != userRepository.findByUsername(userVo.getUsername())) {
                return result(ExceptionMsg.UserNameUsed);
            }
            User user=new User();
            BeanUtils.copyProperties(userVo,user);
            user.setPassword(getPwd(user.getPassword()));
            user.setCreateTime(new Timestamp(System.currentTimeMillis()));
            user.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
            user.setProfilePicture("img/favicon.png");
            User newUser = userRepository.save(user);
            // 添加默认收藏夹
            Favorites favorites = favoritesService.saveFavorites(user.getId(), "未读列表");
            // 添加默认属性设置
            configService.saveConfig(newUser.getId(), String.valueOf(favorites.getId()));
            getSession().setAttribute(Const.LOGIN_SESSION_KEY, user);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("create user failed, ", e);
            return result(ExceptionMsg.FAILED);
        }
        return result();
    }

    @RequestMapping(value = "/getFavorites", method = RequestMethod.POST)
    @LoggerManage(description = "获取收藏夹")
    public List<Favorites> getFavorites() {
        List<Favorites> favorites = null;
        try {
            favorites = favoritesRepository.findByUserIdOrderByLastModifyTimeDesc(getUserId());
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
            config = configRepository.findByUserId(getUserId());
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
    public Response updateConfig(Long id, String type, String defaultFavorites) {
        if (null != id && StringUtils.isNotBlank(type)) {
            try {
                configService.updateConfig(id, type, defaultFavorites);
            } catch (Exception e) {
                logger.error("属性修改异常：", e);
            }
        }
        return result();
    }

    @GetMapping(value = "/getFollows")
    @LoggerManage(description = "获取关注列表")
    public List<String> getFollows() {
        List<String> followList = followRepository.findByUserId(getUserId());
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
            User registUser = userRepository.findByEmail(email);
            if (null == registUser) {
                return result(ExceptionMsg.EmailNotRegister);
            }
            String secretKey = UUID.randomUUID().toString(); // 密钥
            Timestamp outDate = new Timestamp(System.currentTimeMillis() + 30 * 60 * 1000);// 30分钟后过期
            long date = outDate.getTime() / 1000 * 1000;
            userRepository.setOutDateAndValidataCode(outDate + "", secretKey, email);
            String key = email + "$" + date + "$" + secretKey;
            String digitalSignature = MD5Util.encrypt(key);// 数字签名
//            String basePath = this.getRequest().getScheme() + "://" + this.getRequest().getServerName() + ":" + this.getRequest().getServerPort() + this.getRequest().getContextPath() + "/newPassword";
            String resetPassHref = forgotpasswordUrl + "?sid="
                    + digitalSignature + "&email=" + email;
            String emailContent = MessageUtil.getMessage(mailContent, resetPassHref);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(mailFrom);
            helper.setTo(email);
            helper.setSubject(mailSubject);
            helper.setText(emailContent, true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("sendForgotPasswordEmail failed, ", e);
            return result(ExceptionMsg.FAILED);
        }
        return result();
    }

    /**
     * 忘记密码-设置新密码
     *
     * @param newpwd
     * @param email
     * @param sid
     * @return
     */
    @RequestMapping(value = "/setNewPassword", method = RequestMethod.POST)
    @LoggerManage(description = "设置新密码")
    public Response setNewPassword(String newpwd, String email, String sid) {
        try {
            User user = userRepository.findByEmail(email);
            Timestamp outDate = Timestamp.valueOf(user.getOutDate());
            if (outDate.getTime() <= System.currentTimeMillis()) { //表示已经过期
                return result(ExceptionMsg.LinkOutdated);
            }
            String key = user.getEmail() + "$" + outDate.getTime() / 1000 * 1000 + "$" + user.getValidataCode();//数字签名
            String digitalSignature = MD5Util.encrypt(key);
            if (!digitalSignature.equals(sid)) {
                return result(ExceptionMsg.LinkOutdated);
            }
            userRepository.setNewPassword(getPwd(newpwd), email);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("setNewPassword failed, ", e);
            return result(ExceptionMsg.FAILED);
        }
        return result();
    }

    /**
     * 修改密码
     *
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @LoggerManage(description = "修改密码")
    public Response updatePassword(String oldPassword, String newPassword) {
        try {
            User user = getUser();
            String password = user.getPassword();
            String newpwd = getPwd(newPassword);
            if (password.equals(getPwd(oldPassword))) {
                userRepository.setNewPassword(newpwd, user.getEmail());
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
     * 修改个人简介
     *
     * @param introduction
     * @return
     */
    @RequestMapping(value = "/updateIntroduction", method = RequestMethod.POST)
    @LoggerManage(description = "修改个人简介")
    public ResponseData updateIntroduction(String introduction) {
        try {
            User user = getUser();
            userRepository.setIntroduction(introduction, user.getEmail());
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
     * 修改昵称
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/updateUserName", method = RequestMethod.POST)
    @LoggerManage(description = "修改昵称")
    public ResponseData updateUserName(String username) {
        try {
            User loginUser = getUser();
            if (username.equals(loginUser.getUsername())) {
                return new ResponseData(ExceptionMsg.UserNameSame);
            }
            User user = userRepository.findByUsername(username);
            if (null != user && user.getUsername().equals(username)) {
                return new ResponseData(ExceptionMsg.UserNameUsed);
            }
            if (username.length() > 12) {
                return new ResponseData(ExceptionMsg.UserNameLengthLimit);
            }
            userRepository.setUsername(username, loginUser.getEmail());
            loginUser.setUsername(username);
            getSession().setAttribute(Const.LOGIN_SESSION_KEY, loginUser);
            return new ResponseData(ExceptionMsg.SUCCESS, username);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("updateUserName failed, ", e);
            return new ResponseData(ExceptionMsg.FAILED);
        }
    }

    /**
     * 上传头像
     *
     * @param dataUrl
     * @return
     */
    @RequestMapping(value = "/uploadHeadPortrait", method = RequestMethod.POST)
    public ResponseData uploadHeadPortrait(String dataUrl) {
        logger.info("执行 上传头像 开始");
        try {
            String filePath = staticUrl + fileProfilepicturesUrl;
            String fileName = UUID.randomUUID().toString() + ".png";
            String savePath = fileProfilepicturesUrl + fileName;
            String image = dataUrl;
            String header = "data:image";
            String[] imageArr = image.split(",");
            if (imageArr[0].contains(header)) {
                image = imageArr[1];
                Base64.Decoder decoder = Base64.getDecoder();
                byte[] decodedBytes = decoder.decode(image);
                FileUtil.uploadFile(decodedBytes, filePath, fileName);
                User user = getUser();
                userRepository.setProfilePicture(savePath, user.getId());
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
     * @param dataUrl
     * @return
     */
    @RequestMapping(value = "/uploadBackground", method = RequestMethod.POST)
    @LoggerManage(description = "上传背景")
    public ResponseData uploadBackground(String dataUrl) {
        try {
            String filePath = staticUrl + fileBackgroundpicturesUrl;
            String fileName = UUID.randomUUID().toString() + ".png";
            String savePath = fileBackgroundpicturesUrl + fileName;
            String image = dataUrl;
            String header = "data:image";
            String[] imageArr = image.split(",");
            if (imageArr[0].contains(header)) {
                image = imageArr[1];
                Base64.Decoder decoder = Base64.getDecoder();
                byte[] decodedBytes = decoder.decode(image);
                FileUtil.uploadFile(decodedBytes, filePath, fileName);
                User user = getUser();
                userRepository.setBackgroundPicture(savePath, user.getId());
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
