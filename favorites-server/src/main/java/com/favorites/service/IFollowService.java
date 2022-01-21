package com.favorites.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.favorites.entity.Follow;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yzq
 * @since 2022-01-20
 */
public interface IFollowService extends IService<Follow> {

    List<String> getFollowsByUserId( Long userId);
}
