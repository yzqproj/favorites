package com.favorites.controller;

import com.favorites.comm.Const;
import com.favorites.comm.aop.LoggerManage;
import com.favorites.domain.Config;
import com.favorites.domain.Favorites;
import com.favorites.domain.User;
import com.favorites.domain.UserIsFollow;
import com.favorites.domain.enums.CollectType;
import com.favorites.domain.enums.FollowStatus;
import com.favorites.domain.enums.IsDelete;
import com.favorites.domain.view.CollectSummary;
import com.favorites.domain.view.IndexCollectorView;
import com.favorites.repository.*;
import com.favorites.service.CollectService;
import com.favorites.service.CollectorService;
import com.favorites.service.LookAroundService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * 指数控制器
 *
 * @author yanni
 * @date 2022/01/21
 */
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController extends BaseController {
     
     private final FavoritesRepository favoritesRepository;
     
     private final ConfigRepository configRepository;
     
     private final FollowRepository followRepository;
     
     private final CollectRepository collectRepository;
     
     private final NoticeRepository noticeRepository;
     
     private final CollectorService collectorService;
     
     private final CollectService collectService;
     
     private final UserRepository userRepository;
/*     
	 private final RedisService redisService;*/

    /**
     * 随便看看  added by chenzhimin
     */
     
     private final LookAroundService lookAroundService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @LoggerManage(description = "首页")
    public HashMap<String, Object> index() {
        HashMap<String, Object> hash = new HashMap<>(16);
//		IndexCollectorView indexCollectorView = collectorService.getCollectors();
        hash.put("collector", "");
        User user = super.getUser();
        if (null != user) {
            hash.put("user", user);
        }
        return hash;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @LoggerManage(description = "登陆后首页")
    public HashMap<String, Object> home() {
        long size = collectRepository.countByUserIdAndIsDelete(getUserId(), IsDelete.NO);
        Config config = configRepository.findByUserId(getUserId());
        Favorites favorites = favoritesRepository.findById(Long.parseLong(config.getDefaultFavorties()));
        List<String> followList = followRepository.findByUserId(getUserId());
        HashMap<String, Object> hash = new HashMap<>(16);
        hash.put("config", config);
        hash.put("favorites", favorites);
        hash.put("size", size);
        hash.put("followList", followList);
        hash.put("user", getUser());
        hash.put("newAtMeCount", noticeRepository.countByUserIdAndTypeAndReaded(getUserId(), "at", "unread"));
        hash.put("newCommentMeCount", noticeRepository.countByUserIdAndTypeAndReaded(getUserId(), "comment", "unread"));
        hash.put("newPraiseMeCount", noticeRepository.countPraiseByUserIdAndReaded(getUserId(), "unread"));
        logger.info("collect size=" + size + " userID=" + getUserId());
        return hash;
    }

    /**
     * 随便看看 标准模式显示
     *
     * @return
     */
    @GetMapping(value = "/lookAround")
    @LoggerManage(description = "随便看看页面")
    public HashMap<String, Object> lookAroundStandard(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                      @RequestParam(value = "size", defaultValue = "15") Integer size) {

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        HashMap<String, Object> hash = new HashMap<>(16);
        hash.put("type", "lookAround");
        List<CollectSummary> collects = lookAroundService.queryCollectExplore(pageable, getUserId(), null);
        User user = super.getUser();
        if (null != user) {
            hash.put("user", user);
        }
        hash.put("collects", collects);
        hash.put("userId", getUserId());
        hash.put("size", collects.size());
        return hash;
    }

    /**
     * 随便看看 简单模式显示  added by chenzhimin
     *
     * @return /lookAround/simple/ALL
     */
    @GetMapping(value = "/lookAround/simple/{category}")
    @LoggerManage(description = "随便看看页面")
    public HashMap<String, Object> lookAroundSimple(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(value = "size", defaultValue = "20") Integer size,
                                                    @PathVariable("category") String category) {

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        HashMap<String, Object> hash = new HashMap<>(16);
        hash.put("category", category);
        hash.put("type", "lookAround");
        Favorites favorites = new Favorites();
        List<CollectSummary> collects = null;
        List<CollectSummary> fivecollects = lookAroundService.scrollFiveCollect();
        List<UserIsFollow> fiveUsers = lookAroundService.queryFiveUser(this.getUserId());

        collects = lookAroundService.queryCollectExplore(pageable, getUserId(), category);
        User user = super.getUser();
        if (null != user) {
            hash.put("user", user);
        }
        hash.put("fiveCollects", fivecollects);
        hash.put("fiveUsers", fiveUsers);
        hash.put("collects", collects);
        hash.put("favorites", favorites);
        hash.put("userId", getUserId());
        hash.put("size", collects.size());
        return hash;
    }


    @GetMapping(value = "/tool")
    @LoggerManage(description = "工具页面")
    public HashMap<String, Object> tool() {
        HashMap<String, Object> hash = new HashMap<>(16);
        String path = "javascript:(function()%7Bvar%20description;var%20desString=%22%22;var%20metas=document.getElementsByTagName('meta');for(var%20x=0,y=metas.length;x%3Cy;x++)%7Bif(metas%5Bx%5D.name.toLowerCase()==%22description%22)%7Bdescription=metas%5Bx%5D;%7D%7Dif(description)%7BdesString=%22&amp;description=%22+encodeURIComponent(description.content);%7Dvar%20win=window.open(%22"
                + Const.BASE_PATH
                + "collect?from=webtool&url=%22+encodeURIComponent(document.URL)+desString+%22&title=%22+encodeURIComponent(document.title)+%22&charset=%22+document.charset,'_blank');win.focus();%7D)();";
        hash.put("path", path);
        return hash;
    }



    @PostMapping(value = "/feedback")
    @LoggerManage(description = "意见反馈页面")
    public HashMap<String, Object> feedback() {
        User user = null;
        user = userRepository.findById(getUserId());
        HashMap<String, Object> hash = new HashMap<>(16);
        hash.put("user", user);
        return hash;
    }

    @RequestMapping(value = "/collect", method = RequestMethod.GET)
    @LoggerManage(description = "收藏页面")
    public HashMap<String,Object> collect() {
        List<Favorites> favoritesList = favoritesRepository.findByUserIdOrderByLastModifyTimeDesc(getUserId());
        Config config = configRepository.findByUserId(getUserId());
        List<String> followList = followRepository.findByUserId(getUserId());
        logger.info("model：" + config.getDefaultModel());
        HashMap<String, Object> hash = new HashMap<>(16);
        hash.put("favoritesList", favoritesList);
        hash.put("configObj", config);
        hash.put("followList", followList);
        return hash;
    }

    @PostMapping(value = "/logout")
    @LoggerManage(description = "登出")
    public HashMap<String, Object> logout(HttpServletResponse response) {
        getSession().removeAttribute(Const.LOGIN_SESSION_KEY);
        getSession().removeAttribute(Const.LAST_REFERER);
        Cookie cookie = new Cookie(Const.LOGIN_SESSION_KEY, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        IndexCollectorView indexCollectorView = collectorService.getCollectors();
        HashMap<String, Object> hash = new HashMap<>(16);
        hash.put("collector", indexCollectorView);
        return hash;
    }


    /**
     * 首页收藏家个人首页
     *
     * @param userId      用户id
     * @param favoritesId 最喜欢id
     * @param page        页面
     * @param size        大小
     * @return {@link String}
     */
    @GetMapping(value = "/collector/{userId}/{favoritesId:[0-9]*}")
    @LoggerManage(description = "首页收藏家个人首页")
    public HashMap<String, Object> collectorPageShow(@PathVariable("userId") long userId, @PathVariable("favoritesId") Long favoritesId, @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                     @RequestParam(value = "size", defaultValue = "15") Integer size) {
        User user = userRepository.findById(userId);
        Long collectCount = 0L;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<CollectSummary> collects = null;
        HashMap<String, Object> hash = new HashMap<>(16);
        Integer isFollow = 0;
        if (getUserId() == userId) {
            hash.put("myself", IsDelete.YES.toString());
            collectCount = collectRepository.countByUserIdAndIsDelete(userId, IsDelete.NO);
            if (0 == favoritesId) {
                collects = collectService.getCollects("myself", userId, pageable, null, null);
            } else {
                collects = collectService.getCollects(String.valueOf(favoritesId), userId, pageable, 0L, null);
            }
        } else {
            hash.put("myself", IsDelete.NO.toString());
            collectCount = collectRepository.countByUserIdAndTypeAndIsDelete(userId, CollectType.PUBLIC, IsDelete.NO);
            if (favoritesId == 0) {
                collects = collectService.getCollects("others", userId, pageable, null, getUserId());
            } else {
                collects = collectService.getCollects("otherpublic", userId, pageable, favoritesId, getUserId());
            }
            isFollow = followRepository.countByUserIdAndFollowIdAndStatus(getUserId(), userId, FollowStatus.FOLLOW);
        }
        Integer follow = followRepository.countByUserIdAndStatus(userId, FollowStatus.FOLLOW);
        Integer followed = followRepository.countByFollowIdAndStatus(userId, FollowStatus.FOLLOW);
        List<Favorites> favoritesList = favoritesRepository.findByUserId(userId);
        List<String> followUser = followRepository.findFollowUserByUserId(userId);
        List<String> followedUser = followRepository.findFollowedUserByFollowId(userId);
        Config config = configRepository.findByUserId(getUserId());
        if (getUserId() == 0 || getUserId() == 0) {
            config = configRepository.findByUserId(userId);
        }
        hash.put("collectCount", collectCount);
        hash.put("follow", follow);
        hash.put("followed", followed);
        hash.put("user", user);
        hash.put("collects", collects);
        hash.put("favoritesList", favoritesList);
        hash.put("followUser", followUser);
        hash.put("followedUser", followedUser);
        hash.put("isFollow", isFollow);
        hash.put("loginUserInfo", getUser());
        hash.put("config", config);
        hash.put("configObj", config);
        return hash;
    }

}