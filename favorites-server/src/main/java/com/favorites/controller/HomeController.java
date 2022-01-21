package com.favorites.controller;

import com.favorites.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanni
 * @date time 2022/1/20 21:10
 * @modified By:
 */
@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController extends BaseController {


    private final ICollectService collectService;
private final IUserService userService;

    private final IFavoritesService favoritesService;
    private final INoticeService noticeService;

    private final ILookRecordService lookRecordService;

    private final ILetterService letterService;

}
