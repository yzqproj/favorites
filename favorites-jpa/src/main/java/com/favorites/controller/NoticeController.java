package com.favorites.controller;

import com.favorites.comm.aop.LoggerManage;
import com.favorites.domain.Comment;
import com.favorites.domain.Notice;
import com.favorites.domain.result.ExceptionMsg;
import com.favorites.domain.result.Response;
import com.favorites.domain.result.ResponseData;
import com.favorites.repository.CommentRepository;
import com.favorites.repository.NoticeRepository;
import com.favorites.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * @author YY
 * @version 1.0
 * @ClassName: NoticeController
 * @Description:
 * @date 2016年8月31日  上午9:59:47
 */

@RestController
@RequestMapping("/notice")
public class NoticeController extends BaseController {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private CommentRepository commentRepository;

    /**
     * 回复
     *
     * @param comment
     * @return
     */
    @RequestMapping(value = "/reply", method = RequestMethod.POST)
    public Response reply(Comment comment) {
        logger.info("reply begin");
        try {
            comment.setUserId(getUserId());
            comment.setCreateTime(new Timestamp(System.currentTimeMillis()));
            Comment saveCommon = commentRepository.save(comment);
            Notice notice = new Notice();
            notice.setCollectId(comment.getCollectId() + "");
            notice.setUserId(comment.getReplyUserId());
            notice.setType("comment");
            notice.setReaded("unread");
            notice.setOperId(saveCommon.getId().toString());
            notice.setCreateTime(new Timestamp(System.currentTimeMillis()));
            noticeRepository.save(notice);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("reply failed, ", e);
            return result(ExceptionMsg.FAILED);
        }
        return result();
    }

    @GetMapping(value = "/getNoticeNum")
    @LoggerManage(description = "获取新消息数量")
    public ResponseData getNoticeNum() {
        Map<String, Long> result = new HashMap<String, Long>();
        Long newAtMeCount = noticeRepository.countByUserIdAndTypeAndReaded(getUserId(), "at", "unread");
        Long newCommentMeCount = noticeRepository.countByUserIdAndTypeAndReaded(getUserId(), "comment", "unread");
        Long newPraiseMeCount = noticeRepository.countPraiseByUserIdAndReaded(getUserId(), "unread");
        Long newLetterNotice = noticeRepository.countByUserIdAndTypeAndReaded(getUserId(), "letter", "unread");
        result.put("newAtMeCount", newAtMeCount);
        result.put("newCommentMeCount", newCommentMeCount);
        result.put("newPraiseMeCount", newPraiseMeCount);
        result.put("newLetterNotice", newLetterNotice);
        return new ResponseData(ExceptionMsg.SUCCESS, result);
    }

}
