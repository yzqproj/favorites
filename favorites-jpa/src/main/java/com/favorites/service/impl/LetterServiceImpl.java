package com.favorites.service.impl;

import com.favorites.domain.Letter;
import com.favorites.domain.User;
import com.favorites.domain.enums.LetterType;
import com.favorites.domain.view.LetterSummary;
import com.favorites.domain.view.LetterView;
import com.favorites.repository.LetterRepository;
import com.favorites.repository.UserRepository;
import com.favorites.service.LetterService;
import com.favorites.service.NoticeService;
import com.favorites.utils.DateUtils;
import com.favorites.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DingYS on 2017/3/8.
 */
@Service
@RequiredArgsConstructor
public class LetterServiceImpl implements LetterService{


    private final LetterRepository letterRepository;

    private final NoticeService noticeService;

    private final UserRepository userRepository;

    /**
     * 发送私信
     * @param letter
     */
    @Override
    public void sendLetter(Letter letter){
        if("original".equals(letter.getSendType())){
            letter.setType(LetterType.ORIGINAL);
        }else{
            letter.setType(LetterType.REPLY);
            List<String> usernameList = StringUtil.getAtUser(letter.getContent());
            if(usernameList.size() > 0){
                User receiveUser = userRepository.findByUsername(usernameList.get(0));
                if(null != receiveUser){
                    letter.setReceiveUserId(receiveUser.getId());
                }
                String content = letter.getContent().substring(0,letter.getContent().indexOf("@"));
                if(StringUtils.isBlank(content)){
                    content = letter.getContent().substring(letter.getContent().indexOf("@")+receiveUser.getUsername().length()+1,letter.getContent().length());
                    letter.setContent(content);
                }
            }
        }
        letter.setCreateTime(new Timestamp(System.currentTimeMillis()));
        letterRepository.save(letter);
        if(null == letter.getPid()){
            letter.setPid(letter.getId());
            letterRepository.updatePidById(letter.getId(),letter.getId());
        }
        // 添加消息通知
        noticeService.saveNotice(null,"letter",letter.getReceiveUserId(),String.valueOf(letter.getId()));
    }

    /**
     * 私信信息查询
     * @param userId
     * @param pageable
     * @return
     */
    @Override
    public List<LetterSummary> findLetter(Long userId, Pageable pageable){
        List<LetterView> viewList = letterRepository.findLetterByReceiveUserId(userId,pageable);
        List<LetterSummary> summaryList = new ArrayList<>();
        for(LetterView view : viewList){
            LetterSummary summary = new LetterSummary(view);
            summaryList.add(summary);
        }
        return summaryList;
    }


}
