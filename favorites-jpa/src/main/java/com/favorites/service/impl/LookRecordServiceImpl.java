package com.favorites.service.impl;

import com.favorites.domain.LookRecord;
import com.favorites.domain.Praise;
import com.favorites.domain.view.CollectSummary;
import com.favorites.domain.view.CollectView;
import com.favorites.repository.CommentRepository;
import com.favorites.repository.LookRecordRepository;
import com.favorites.repository.PraiseRepository;
import com.favorites.service.CollectService;
import com.favorites.service.LookRecordService;
import com.favorites.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by louie on 2017/1/12.
 */
@Service("lookRecordService")
@RequiredArgsConstructor
public class LookRecordServiceImpl implements LookRecordService {

    
    private final LookRecordRepository lookRecordRepository;

    
    private final PraiseRepository praiseRepository;

    
    private final CommentRepository commentRepository;

    
    private final CollectService collectService;




    @Override
    public void saveLookRecord(Long userId,Long collectId) {
        if(userId != null && userId > 0 && collectId != null) {
            Integer count = lookRecordRepository.countByUserIdAndCollectId(userId, collectId);
            if (count > 0) {
                lookRecordRepository.updatelastModifyTime(userId, collectId, new Timestamp(System.currentTimeMillis()));
            } else {
                LookRecord lookRecord = new LookRecord();
                lookRecord.setUserId(userId);
                lookRecord.setCollectId(collectId);
                lookRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
                lookRecord.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
                lookRecordRepository.save(lookRecord);
            }
        }

    }

    @Override
    public void deleteLookRecord(Long userId, Long collectId) {
        lookRecordRepository.deleteByUserIdAndCollectId(userId,collectId);
    }

    @Override
    public void deleteLookRecordByUserID(Long userId) {
        lookRecordRepository.deleteByUserId(userId);
    }

    @Override
    public List<CollectSummary> getLookRecords(Long userId, Pageable pageable) {
        Page<CollectView> views = null;

        views = lookRecordRepository.findViewByUserId(userId, pageable);

        return convertCollect(views,userId);
    }

    /**
     * @author neo
     * @date 2016???8???11???
     * @return
     */
    private List<CollectSummary> convertCollect(Page<CollectView> views,Long userId) {
        List<CollectSummary> summarys= new ArrayList<>();
        for (CollectView view : views) {
            CollectSummary summary=new CollectSummary(view);
            summary.setPraiseCount(praiseRepository.countByCollectId(view.getId()));
            summary.setCommentCount(commentRepository.countByCollectId(view.getId()));
            Praise praise=praiseRepository.findByUserIdAndCollectId(userId, view.getId());
            if(praise!=null){
                summary.setPraise(true);
            }else{
                summary.setPraise(false);
            }
            summary.setCollectTime(DateUtils.getTimeFormatText(view.getCreateTime()));
            summarys.add(summary);
        }
        return summarys;
    }


}
