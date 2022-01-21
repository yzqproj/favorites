package com.favorites.service.impl;

import com.favorites.entity.Favorites;
import com.favorites.mapper.FavoritesMapper;
import com.favorites.service.IFavoritesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.favorites.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
public class FavoritesServiceImpl extends ServiceImpl<FavoritesMapper, Favorites> implements IFavoritesService {
    private final FavoritesMapper favoritesMapper;

    @Override
    public Favorites saveFavorites(Long userId, String name) {
        Favorites favorites = new Favorites();
        favorites.setName(name);
        favorites.setUserId(userId);
        favorites.setCount(0L);
        favorites.setPublicCount(10L);
        favorites.setCreateTime(new Timestamp(System.currentTimeMillis()));
        favorites.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
        favoritesMapper.insert(favorites);
        return favorites;
    }

    @Override
    public List<Favorites> findByUserIdOrderByLastModifyTimeDesc(long userId) {
        return null;
    }
}
