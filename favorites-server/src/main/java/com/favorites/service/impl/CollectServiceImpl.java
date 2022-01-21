package com.favorites.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.favorites.entity.Collect;
import com.favorites.entity.Favorites;
import com.favorites.entity.enums.CollectType;
import com.favorites.entity.enums.IsDelete;
import com.favorites.entity.view.CollectSummary;
import com.favorites.mapper.CollectMapper;
import com.favorites.service.ICollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yzq
 * @since 2022-01-20
 */
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements ICollectService {

    @Override
    public Long countByFavoritesIdAndTypeAndIsDelete(Long id, CollectType aPublic, IsDelete no) {
        return null;
    }

    @Override
    public List<CollectSummary> getCollects(String otherpublic, long userId, IPage<Favorites> pageable, Long id, Object o) {
        return null;
    }
}
