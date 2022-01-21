package com.favorites.service;

import com.favorites.entity.Config;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yzq
 * @since 2022-01-20
 */
public interface IConfigService extends IService<Config> {

    Config saveConfig(Long userId, String favoritesId);

    void updateConfig(Long id, String type, String defaultFavorites);
}
