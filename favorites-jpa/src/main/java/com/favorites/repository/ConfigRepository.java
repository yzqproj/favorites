package com.favorites.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.favorites.domain.Config;

import java.sql.Timestamp;

public interface ConfigRepository extends JpaRepository<Config, Long> {

    /**
     * 找到用户id
     *
     * @param userId 用户id
     * @return {@link Config}
     */
    Config findByUserId(Long userId);

    /**
     * 发现通过id
     *
     * @param id id
     * @return {@link Config}
     */
    Config findById(long id);

    /**
     * 找到用户id和favorties违约
     *
     * @param userId           用户id
     * @param defaultFavorites 默认收藏
     * @return {@link Config}
     */
    Config findByUserIdAndDefaultFavorties(Long userId, String defaultFavorites);

    /**
     * 更新收集通过id类型
     *
     * @param id             id
     * @param value          价值
     * @param lastModifyTime 最后修改时间
     * @return int
     */
    @Transactional
    @Modifying
    @Query("update Config set defaultCollectType=?2,lastModifyTime =?3 where id = ?1")
    int updateCollectTypeById(Long id, String value, Timestamp lastModifyTime);

    /**
     * 更新模型通过id类型
     *
     * @param id             id
     * @param value          价值
     * @param lastModifyTime 最后修改时间
     * @return int
     */
    @Transactional
    @Modifying
    @Query("update Config set defaultModel=?2,lastModifyTime =?3 where id = ?1")
    int updateModelTypeById(Long id, String value, Timestamp lastModifyTime);

    /**
     * 更新最喜欢通过id
     *
     * @param id             id
     * @param value          价值
     * @param lastModifyTime 最后修改时间
     * @return int
     */
    @Transactional(rollbackOn = Exception.class)
    @Modifying
    @Query("update Config set defaultFavorties=?2,lastModifyTime =?3 where id = ?1")
    int updateFavoritesById(Long id, String value, Timestamp lastModifyTime);

}