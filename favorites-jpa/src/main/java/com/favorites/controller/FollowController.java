package com.favorites.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.favorites.comm.aop.LoggerManage;
import com.favorites.domain.Follow;
import com.favorites.domain.enums.FollowStatus;
import com.favorites.domain.result.ExceptionMsg;
import com.favorites.domain.result.Response;
import com.favorites.repository.FollowRepository;
import com.favorites.utils.DateUtils;

import java.sql.Timestamp;

@RestController
@RequestMapping("/follow")
public class FollowController extends BaseController {

    @Autowired
    private FollowRepository followRepository;

    /**
     *
     * 关注&取消关注
     *
     * @param status 状态
     * @param userId 用户id
     * @return {@link Response}
     */
    @PostMapping("/changeFollowStatus")
    @LoggerManage(description = "关注&取消关注")
    public Response changeFollowStatus(String status, Long userId) {
        try {
            FollowStatus followStatus = FollowStatus.FOLLOW;
            if (!"follow".equals(status)) {
                followStatus = FollowStatus.UNFOLLOW;
            }
            Follow follow = followRepository.findByUserIdAndFollowId(getUserId(), userId);
            if (null != follow) {
                followRepository.updateStatusById(followStatus, new Timestamp(System.currentTimeMillis()), follow.getId());
            } else {
                follow = new Follow();
                follow.setFollowId(userId);
                follow.setUserId(getUserId());
                follow.setStatus(followStatus);
                follow.setCreateTime(new Timestamp(System.currentTimeMillis()));
                follow.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
                followRepository.save(follow);
            }
        } catch (Exception e) {
            logger.error("关注&取消关注异常：", e);
            return result(ExceptionMsg.FAILED);
        }
        return result();
    }

}
