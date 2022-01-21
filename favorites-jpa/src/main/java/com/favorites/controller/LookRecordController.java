package com.favorites.controller;

import com.favorites.domain.result.Response;
import com.favorites.service.LookRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by chenzhimin on 2017/2/14.
 */
@RestController
@RequestMapping("/lookRecord")
public class LookRecordController extends BaseController{

    @Autowired
    private LookRecordService lookRecordService;

    /**
     * @author chenzhimin
     * @date 2017年1月23日
     * @param collectId 收藏ID
     * @return
     */
    @PostMapping(value="/save/{collectId}")
    public Response saveLookRecord(@PathVariable("collectId") long collectId) {
        lookRecordService.saveLookRecord(this.getUserId(),collectId);
        return result();
    }

    /**
     * @author chenzhimin
     * @date 2017年1月23日
     * @param collectId 收藏ID
     * @return
     */
    @DeleteMapping(value="/delete/{collectId}")
    public Response deleteLookRecord(@PathVariable("collectId") long collectId) {
        lookRecordService.deleteLookRecord(this.getUserId(),collectId);
        return result();
    }

    /**
     * @author chenzhimin
     * @date 2017年1月23日
     * @return
     */
    @DeleteMapping(value="/deleteAll")
    public Response deleteAll() {
        lookRecordService.deleteLookRecordByUserID(this.getUserId());
        return result();
    }
}
