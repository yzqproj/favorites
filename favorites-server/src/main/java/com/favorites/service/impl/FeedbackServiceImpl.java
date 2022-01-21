package com.favorites.service.impl;

import com.favorites.entity.Feedback;
import com.favorites.mapper.FeedbackMapper;
import com.favorites.service.IFeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yzq
 * @since 2022-01-20
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements IFeedbackService {

}
