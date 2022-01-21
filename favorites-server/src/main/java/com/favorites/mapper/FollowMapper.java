package com.favorites.mapper;

import com.favorites.entity.Follow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yzq
 * @since 2022-01-20
 */
public interface FollowMapper extends BaseMapper<Follow> {

    List<String> getFollowsByUserId(Long userId);
}
