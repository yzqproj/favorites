package com.favorites.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.favorites.entity.Collect;
import com.baomidou.mybatisplus.extension.service.IService;
import com.favorites.entity.Favorites;
import com.favorites.entity.enums.CollectType;
import com.favorites.entity.enums.IsDelete;
import com.favorites.entity.view.CollectSummary;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yzq
 * @since 2022-01-20
 */
public interface ICollectService extends IService<Collect> {

    Long countByFavoritesIdAndTypeAndIsDelete(Long id, CollectType aPublic, IsDelete no);

    List<CollectSummary> getCollects(String otherpublic, long userId, IPage<Favorites> pageable, Long id, Object o);
}
