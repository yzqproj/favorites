package com.favorites.service.impl;

import com.favorites.entity.Config;
import com.favorites.mapper.ConfigMapper;
import com.favorites.service.IConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.favorites.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

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
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {

    private final ConfigMapper configMapper;

    @Override
    public Config saveConfig(Long userId, String favoritesId) {
        Config config = new Config();
        config.setUserId(userId);
        config.setDefaultModel("simple");
        config.setDefaultFavorties(favoritesId);
        config.setDefaultCollectType("public");
        config.setCreateTime(new Timestamp(System.currentTimeMillis()));
        config.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
        configMapper.insert(config);
        return config;
    }

    @Override
    public void updateConfig(Long id, String type, String defaultFavorites) {
        Config config = configMapper.selectById(id);
        String value="";
        if("defaultCollectType".equals(type)){
            if("public".equals(config.getDefaultCollectType())){
                value = "private";
            }else{
                value = "public";
            }
            configMapper.updateCollectTypeById(id, value, new Timestamp(System.currentTimeMillis()));
        }else if("defaultModel".equals(type)){
            if("simple".equals(config.getDefaultModel())){
                value = "major";
            }else{
                value="simple";
            }
            configMapper.updateModelTypeById(id, value, new Timestamp(System.currentTimeMillis()));
        }else if("defaultFavorites".equals(type)){
            configMapper.updateFavoritesById(id, defaultFavorites, new Timestamp(System.currentTimeMillis()));
        }

    }
}
