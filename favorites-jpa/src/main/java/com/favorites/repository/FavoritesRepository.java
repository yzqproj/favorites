package com.favorites.repository;


import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import com.favorites.domain.Collect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.favorites.domain.Favorites;

public interface FavoritesRepository extends JpaRepository<Favorites, Long> {

    /**
     * 发现通过id
     *
     * @param id id
     * @return {@link Favorites}
     */
    Favorites findById(long id);

    /**
     * 找到用户id
     *
     * @param userId 用户id
     * @return {@link List}<{@link Favorites}>
     */
    List<Favorites> findByUserId(Long userId);

    /**
     * 找到用户id desc按最后修改时间
     *
     * @param userId 用户id
     * @return {@link List}<{@link Favorites}>
     */
    List<Favorites> findByUserIdOrderByLastModifyTimeDesc(Long userId);

    /**
     * 找到用户id以最后修改时间asc
     *
     * @param userId 用户id
     * @return {@link List}<{@link Favorites}>
     */
    List<Favorites> findByUserIdOrderByLastModifyTimeAsc(Long userId);

    /**
     * 找到用户id和名称
     *
     * @param userId 用户id
     * @param name   名字
     * @return {@link Favorites}
     */
    Favorites findByUserIdAndName(Long userId, String name);

    /**
     * 数减少id
     *
     * @param id             id
     * @param lastModifyTime 最后修改时间
     */
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Favorites f set f.count=(f.count-1),f.lastModifyTime =:lastModifyTime where f.id =:id")
    void reduceCountById(@Param("id") Long id, @Param("lastModifyTime") Timestamp lastModifyTime);

    /**
     * 更新id名称
     *
     * @param id             id
     * @param lastModifyTime 最后修改时间
     * @param name           名字
     */
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Favorites set name=:name ,lastModifyTime=:lastModifyTime where id=:id")
    void updateNameById(@Param("id") Long id, @Param("lastModifyTime") Timestamp lastModifyTime, @Param("name") String name);

    @Query("select id from Favorites where name=?1")
    List<Long> findIdByName(String name);
}