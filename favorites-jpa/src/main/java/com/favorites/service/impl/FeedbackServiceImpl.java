package com.favorites.service.impl;

import com.favorites.domain.Feedback;
import com.favorites.repository.FeedbackRepository;
import com.favorites.service.FeedbackService;
import com.favorites.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;

/**
 * Created by chenzhimin on 2017/2/23.
 */
@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {


    private final FeedbackRepository feedbackRepository;

    @Override
    public void saveFeeddback(  Feedback feedback, Long userId) {
        feedback.setUserId(userId == null || userId == 0L ? null : userId);
        feedback.setCreateTime(new Timestamp(System.currentTimeMillis()));
        feedback.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
        feedbackRepository.save(feedback);
    }
}
