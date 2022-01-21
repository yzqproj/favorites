package com.favorites.mapper;

import com.favorites.entity.Config;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

import java.sql.Timestamp;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yzq
 * @since 2022-01-20
 */
public interface ConfigMapper extends BaseMapper<Config> {

    int updateCollectTypeById(Long id, String value, Timestamp currentTime);

    int updateModelTypeById(Long id, String value, Timestamp currentTime);

    int updateFavoritesById(Long id, String defaultFavorites, Timestamp currentTime);
}
