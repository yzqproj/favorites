package com.favorites.entity.view;

import com.favorites.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndexCollectorView {
    //收藏文章最多的用户
    private User mostCollectUser;
    //被关注最多的用户
    private User mostFollowedUser;
    //文章被赞最多的用户
    private User mostPraisedUser;
    //文章被评论最多的用户
    private User mostCommentedUser;
    //最受欢迎的用户
    private User mostPopularUser;
    //近一个月最活跃用户
    private User mostActiveUser;

}
