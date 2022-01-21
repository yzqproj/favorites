package com.favorites.service.impl;

import com.favorites.entity.Follow;
import com.favorites.mapper.FollowMapper;
import com.favorites.service.IFollowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yzq
 * @since 2022-01-20
 */
@Service
@RequiredArgsConstructor
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements IFollowService {
    private final FollowMapper followMapper;

    @Override
    public List<String> getFollowsByUserId(Long userId) {
        return followMapper.getFollowsByUserId(userId);
    }
}
