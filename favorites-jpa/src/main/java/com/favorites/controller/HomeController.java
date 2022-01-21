package com.favorites.controller;

import com.favorites.comm.aop.LoggerManage;
import com.favorites.domain.Favorites;
import com.favorites.domain.User;
import com.favorites.domain.enums.CollectType;
import com.favorites.domain.enums.FollowStatus;
import com.favorites.domain.enums.IsDelete;
import com.favorites.domain.view.CollectSummary;
import com.favorites.domain.view.LetterSummary;
import com.favorites.repository.*;
import com.favorites.service.CollectService;
import com.favorites.service.LetterService;
import com.favorites.service.LookRecordService;
import com.favorites.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 家控制器
 *
 * @author yanni
 * @date 2022/01/21
 */
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController extends BaseController {
	

    private final CollectService collectService;

    private final FavoritesRepository favoritesRepository;

    private final UserRepository userRepository;

    private final CollectRepository collectRepository;

    private final FollowRepository followRepository;

    private final NoticeService noticeService;

    private final LookRecordService lookRecordService;

    private final LetterService letterService;

    private final NoticeRepository noticeRepository;


    @GetMapping(value = "/standard/{type}/{userId}")
    @LoggerManage(description = "文章列表standard")
    public String standard(  @RequestParam(value = "page", defaultValue = "0") Integer page,
                           @RequestParam(value = "size", defaultValue = "15") Integer size, @PathVariable("type") String type, @PathVariable("userId") long userId) {
        Sort sort = Sort.by(Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        HashMap<String,Object> hash=new HashMap<>();
        hash.put("type", type);
        Favorites favorites = new Favorites();
        if (!"my".equals(type) && !"explore".equals(type) && !"garbage".equals(type)) {
            try {
                favorites = favoritesRepository.findById(Long.parseLong(type));
                favorites.setPublicCount(collectRepository.countByFavoritesIdAndTypeAndIsDelete(favorites.getId(), CollectType.PUBLIC, IsDelete.NO));
            } catch (Exception e) {
                logger.error("获取收藏夹异常：", e);
            }
        }
        List<CollectSummary> collects = null;
        if (0 != userId && 0 != userId && userId != getUserId()) {
            User user = userRepository.findById(userId);
            hash.put("otherPeople", user);
            collects = collectService.getCollects("otherpublic", userId, pageable, favorites.getId(), null);
        } else {
            collects = collectService.getCollects(type, getUserId(), pageable, null, null);
        }
        hash.put("collects", collects);
        hash.put("favorites", favorites);
        hash.put("userId", getUserId());
        hash.put("size", collects.size());
        logger.info("standard end :" + getUserId());
        return "collect/standard";
    }


    @GetMapping(value = "/simple/{type}/{userId}")
    @LoggerManage(description = "文章列表simple")
    public String simple(  @RequestParam(value = "page", defaultValue = "0") Integer page,
                         @RequestParam(value = "size", defaultValue = "20") Integer size, @PathVariable("type") String type,
                         @PathVariable("userId") long userId) {
        Sort sort = Sort.by(Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        HashMap<String,Object> hash=new HashMap<>();
        hash.put("type", type);
        Favorites favorites = new Favorites();
        if (!"my".equals(type) && !"explore".equals(type) && !"garbage".equals(type)) {
            try {
                favorites = favoritesRepository.findById(Long.parseLong(type));
                favorites.setPublicCount(collectRepository.countByFavoritesIdAndTypeAndIsDelete(favorites.getId(), CollectType.PUBLIC, IsDelete.NO));
            } catch (Exception e) {
                logger.error("获取收藏夹异常：", e);
            }
        }
        List<CollectSummary> collects = null;
        if (0 != userId && 0 != userId && userId != getUserId()) {
            User user = userRepository.findById(userId);
            hash.put("otherPeople", user);
            collects = collectService.getCollects("otherpublic", userId, pageable, favorites.getId(), null);
        } else {
            collects = collectService.getCollects(type, getUserId(), pageable, null, null);
        }
        hash.put("collects", collects);
        hash.put("favorites", favorites);
        hash.put("userId", getUserId());
        hash.put("size", collects.size());
        logger.info("simple end :" + getUserId());
        return "collect/simple";
    }

    /**
     *
     * 个人首页
     *
     * @param userId      用户id
     * @param favoritesId 最喜欢id
     * @param page        页面
     * @param size        大小
     * @return {@link HashMap}
     */
    @GetMapping(value = "/user/{userId}/{favoritesId}")
    @LoggerManage(description = "个人首页")
    public HashMap<String, Object> userPageShow(  @PathVariable("userId") long userId, @PathVariable("favoritesId") Long favoritesId, @RequestParam(value = "page", defaultValue = "0") Integer page,
                               @RequestParam(value = "size", defaultValue = "15") Integer size) {
        User user = userRepository.findById(userId);
        Long collectCount = 0L;
        Sort sort = Sort.by(Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<CollectSummary> collects = null;
        Integer isFollow = 0;
        HashMap<String,Object> hash=new HashMap<>();
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
        hash.put("collectCount", collectCount);
        hash.put("follow", follow);
        hash.put("followed", followed);
        hash.put("user", user);
        hash.put("collects", collects);
        hash.put("favoritesList", favoritesList);
        hash.put("followUser", followUser);
        hash.put("followedUser", followedUser);
        hash.put("isFollow", isFollow);
        User userTemp = null;
        User currentUser = getUser();
        if (this.getUser() == null) {
            userTemp = new User();
            userTemp.setId(0L);
        }
        hash.put("loginUser", currentUser == null ? userTemp : currentUser);
        return hash;
    }


    /**
     * 用户内容显示
     * 个人首页内容替换
     *
     * @param userId      用户id
     * @param favoritesId 最喜欢id
     * @param page        页面
     * @param size        大小
     * @return {@link HashMap}<{@link String}, {@link Object}>
     */
    @PostMapping(value = "/usercontent/{userId}/{favoritesId}")
    @LoggerManage(description = "个人首页内容替换")
    public HashMap<String, Object> userContentShow(@PathVariable("userId") long userId, @PathVariable("favoritesId") Long favoritesId, @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                   @RequestParam(value = "size", defaultValue = "15") Integer size) {
        User user = userRepository.findById(userId);
        Long collectCount = 0L;
        Sort sort = Sort.by(Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<CollectSummary> collects = null;
        HashMap<String,Object> hash=new HashMap<>();
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
        }
        List<Favorites> favoritesList = favoritesRepository.findByUserId(userId);
        hash.put("collectCount", collectCount);
        hash.put("user", user);
        hash.put("collects", collects);
        hash.put("favoritesList", favoritesList);
        hash.put("favoritesId", favoritesId);
        hash.put("loginUser", getUser());
        return hash;
    }


    /**
     * 搜索
     *
     * @param page 页面
     * @param size 大小
     * @param key  关键
     * @return {@link HashMap}<{@link String}, {@link Object}>
     * @author neo
     * @date 2016年8月25日
     */
    @PostMapping(value = "/search/{key}")
    @LoggerManage(description = "搜索")
    public HashMap<String, Object> search(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                          @RequestParam(value = "size", defaultValue = "20") Integer size, @PathVariable("key") String key) {
        Sort sort = Sort.by(Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        HashMap<String,Object> hash=new HashMap<>();
        List<CollectSummary> myCollects = collectService.searchMy(getUserId(), key, pageable);
        List<CollectSummary> otherCollects = collectService.searchOther(getUserId(), key, pageable);
        hash.put("myCollects", myCollects);
        hash.put("otherCollects", otherCollects);
        hash.put("userId", getUserId());

        hash.put("mysize", myCollects.size());
        hash.put("othersize", otherCollects.size());
        hash.put("key", key);

        logger.info("search end :" + getUserId());
        return hash;
    }

    /**
     *
     * 消息通知@我的
     *
     * @param page 页面
     * @param size 大小
     * @return {@link HashMap}
     */
    @PostMapping(value = "/notice/atMe")
    public HashMap<String, Object> atMe(  @RequestParam(value = "page", defaultValue = "0") Integer page,
                       @RequestParam(value = "size", defaultValue = "15") Integer size) {
        Sort sort = Sort.by(Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        HashMap<String,Object> hash=new HashMap<>();
        List<CollectSummary> collects = noticeService.getNoticeCollects("at", getUserId(), pageable);
        hash.put("collects", collects);
        noticeRepository.updateReadedByUserId("read", getUserId(), "at");
        logger.info("at end :" + getUserId());
        return hash;
    }

    /**
     *
     * 消息通知评论我的
     *
     * @param page 页面
     * @param size 大小
     * @return {@link HashMap}<{@link String}, {@link Object}>
     */
    @PostMapping(value = "/notice/commentMe")
    public HashMap<String,Object> commentMe(  @RequestParam(value = "page", defaultValue = "0") Integer page,
                            @RequestParam(value = "size", defaultValue = "15") Integer size) {
        Sort sort = Sort.by(Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<CollectSummary> collects = noticeService.getNoticeCollects("comment", getUserId(), pageable);
        HashMap<String,Object> hash=new HashMap<>();
        hash.put("collects", collects);
        noticeRepository.updateReadedByUserId("read", getUserId(), "comment");
        logger.info("at end :" + getUserId());
        return hash;
    }

    /**
     *
     * 消息通知赞我的
     *
     * @param page 页面
     * @param size 大小
     * @return {@link HashMap}<{@link String}, {@link Object}>
     */
    @PostMapping(value = "/notice/praiseMe")
    public HashMap<String, Object> praiseMe(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                            @RequestParam(value = "size", defaultValue = "15") Integer size) {
        Sort sort = Sort.by(Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<CollectSummary> collects = noticeService.getNoticeCollects("praise", getUserId(), pageable);
        HashMap<String,Object> hash=new HashMap<>();
        hash.put("collects", collects);
        noticeRepository.updateReadedByUserId("read", getUserId(), "praise");
        logger.info("praiseMe end :" + getUserId());
        return hash;
    }

    /**
     *
     * 浏览记录 标准显示 added by chenzhimin
     *
     * @param page   页面
     * @param size   大小
     * @param type   类型
     * @param userId 用户id
     * @return {@link HashMap}<{@link String}, {@link Object}>
     */
    @GetMapping(value = "/lookRecord/standard/{type}/{userId}")
    @LoggerManage(description = "浏览记录lookRecord")
    public HashMap<String, Object> getLookRecordStandard(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                         @RequestParam(value = "size", defaultValue = "15") Integer size,
                                                         @PathVariable("type") String type, @PathVariable("userId") long userId) {

        Sort sort = Sort.by(Direction.DESC, "lastModifyTime");
        Pageable pageable = PageRequest.of(page, size, sort);
        HashMap<String,Object> hash=new HashMap<>();
        hash.put("type", "lookRecord");
        Favorites favorites = new Favorites();

        List<CollectSummary> collects = null;
        User user = userRepository.findById(userId);
        hash.put("otherPeople", user);
        collects = lookRecordService.getLookRecords(this.getUserId(), pageable);

        hash.put("collects", collects);
        hash.put("favorites", favorites);
        hash.put("userId", getUserId());
        hash.put("size", collects.size());
        logger.info("LookRecord end :" + getUserId());
        return hash;
    }

    /**
     *
     * 浏览记录 简单显示 added by chenzhimin
     *
     * @param page   页面
     * @param size   大小
     * @param type   类型
     * @param userId 用户id
     * @return {@link HashMap}<{@link String}, {@link Object}>
     */
    @PostMapping(value = "/lookRecord/simple/{type}/{userId}")
    @LoggerManage(description = "浏览记录lookRecord")
    public HashMap<String, Object> getLookRecordSimple(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "20") Integer size,
                                                       @PathVariable("type") String type, @PathVariable("userId") long userId) {

        Sort sort = Sort.by(Direction.DESC, "lastModifyTime");
        Pageable pageable = PageRequest.of(page, size, sort);
        HashMap<String,Object> hash=new HashMap<>();
        hash.put("type", "lookRecord");
        Favorites favorites = new Favorites();

        List<CollectSummary> collects = null;
        User user = userRepository.findById(userId);
        hash.put("otherPeople", user);
        collects = lookRecordService.getLookRecords(this.getUserId(), pageable);

        hash.put("collects", collects);
        hash.put("favorites", favorites);
        hash.put("userId", getUserId());
        hash.put("size", collects.size());
        logger.info("LookRecord end :" + getUserId());
        return hash;
    }

    @PostMapping("/letter/letterMe")
    @LoggerManage(description = "私信我的页面展示")
    public HashMap<String, Object> letterMe(  @RequestParam(value = "page", defaultValue = "0") Integer page,
                           @RequestParam(value = "size", defaultValue = "15") Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<LetterSummary> letterList = letterService.findLetter(getUserId(), pageable);
        HashMap<String,Object> hash=new HashMap<>();
        hash.put("letterList", letterList);
        noticeRepository.updateReadedByUserId("read", getUserId(), "letter");
        return hash;
    }

}