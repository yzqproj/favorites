package com.favorites.service;


import com.favorites.entity.UserIsFollow;
import com.favorites.entity.view.CollectSummary;

import java.util.List;

/**
 * 随便看看Service
 * Created by chenzhimin on 2017/1/4.
 */
public interface ILookAroundService {
    /**
     * 随便看看中滚动条随机显示5条收藏
     * @return
     */
    public List<CollectSummary> scrollFiveCollect();

    /**
     * 按照用户收藏量倒序查询5个用户
     * @param userId
     * @return
     */
    public List<UserIsFollow> queryFiveUser(Long userId);

    /**
     * 随便看看查询收藏列表中（同发现中列表，另加类别过滤）
     * @return
     */
    public List<CollectSummary> queryCollectExplore( Long userId,String category);


}
