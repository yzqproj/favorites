package com.favorites.entity.view;


import lombok.Builder;
import lombok.Data;

/**
 * @Description:
 * @Auth: yuyang
 * @Date: 2017/1/17 12:25
 * @Version: 1.0
 **/
 @Data
 @Builder
public class CollectorView {

    private Long id;
    private Long userId;
    private Long counts;


}
