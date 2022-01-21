package com.favorites.service;

import com.favorites.entity.Favorites;
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
public interface IFavoritesService extends IService<Favorites> {

    Favorites saveFavorites(Long id, String name);

    List<Favorites> findByUserIdOrderByLastModifyTimeDesc(long userId);
}
