package com.favorites.service.impl;

import com.favorites.domain.Collect;
import com.favorites.domain.enums.CollectType;
import com.favorites.domain.enums.IsDelete;
import com.favorites.repository.CollectRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.favorites.domain.Favorites;
import com.favorites.repository.FavoritesRepository;
import com.favorites.service.FavoritesService;
import com.favorites.utils.DateUtils;

import java.sql.Timestamp;

@Service("favoritesService")
@RequiredArgsConstructor
public class FavoritesServiceImpl implements FavoritesService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FavoritesRepository favoritesRepository;
    private final CollectRepository collectRepository;


    @Override
    public Favorites saveFavorites(Long userId, String name) {
        Favorites favorites = new Favorites();
        favorites.setName(name);
        favorites.setUserId(userId);
        favorites.setCount(0L);
        favorites.setPublicCount(10L);
        favorites.setCreateTime(new Timestamp(System.currentTimeMillis()));
        favorites.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
        favoritesRepository.save(favorites);
        return favorites;
    }

    /**
     * 保存
     *
     * @return
     */
    @Override
    public Favorites saveFavorites(Collect collect) {
        Favorites favorites = new Favorites();
        favorites.setName(collect.getNewFavorites());
        favorites.setUserId(collect.getUserId());
        favorites.setCount(1L);
        if (CollectType.PUBLIC.name().equals(collect.getType())) {
            favorites.setPublicCount(1L);
        } else {
            favorites.setPublicCount(10L);
        }
        favorites.setCreateTime(new Timestamp(System.currentTimeMillis()));
        favorites.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
        favoritesRepository.save(favorites);
        return favorites;
    }


    @Override
    public void countFavorites(long id) {
        Favorites favorite = favoritesRepository.findById(id);
        Long count = collectRepository.countByFavoritesIdAndIsDelete(id, IsDelete.NO);
        favorite.setCount(count);
        Long pubCount = collectRepository.countByFavoritesIdAndTypeAndIsDelete(id, CollectType.PUBLIC, IsDelete.NO);
        favorite.setPublicCount(pubCount);
        favorite.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
        favoritesRepository.save(favorite);

    }
}
