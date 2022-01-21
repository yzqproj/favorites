package com.favorites.controller;

import com.favorites.cache.CacheService;
import com.favorites.comm.Const;
import com.favorites.comm.aop.LoggerManage;
import com.favorites.domain.Collect;
import com.favorites.domain.Favorites;
import com.favorites.domain.Praise;
import com.favorites.domain.enums.CollectType;
import com.favorites.domain.enums.IsDelete;
import com.favorites.domain.result.ExceptionMsg;
import com.favorites.domain.result.Response;
import com.favorites.domain.view.CollectSummary;
import com.favorites.repository.CollectRepository;
import com.favorites.repository.FavoritesRepository;
import com.favorites.repository.PraiseRepository;
import com.favorites.service.CollectService;
import com.favorites.service.FavoritesService;
import com.favorites.service.LookAroundService;
import com.favorites.service.LookRecordService;
import com.favorites.utils.DateUtils;
import com.favorites.utils.HtmlUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@RestController
@RequestMapping("/collect")
@RequiredArgsConstructor
public class CollectController extends BaseController {
     
    private final CollectRepository collectRepository;
     
    private final FavoritesService favoritesService;
     
    private final CollectService collectService;
     
    private final FavoritesRepository favoritesRepository;
     
    private final PraiseRepository praiseRepository;

     
    private final CacheService cacheService;

    /**
     * 随便看看  added by chenzhimin
     */
     
    private final LookAroundService lookAroundService;

    /**
     * 浏览记录  added by chenzhimin
     */
     
    private final LookRecordService lookRecordService;

    /**
     * 文章收集
     *
     * @param collect
     * @return
     */
    @RequestMapping(value = "/collect", method = RequestMethod.POST)
    @LoggerManage(description = "文章收集")
    public Response collect(@ParameterObject Collect collect) {
        try {
            if (StringUtils.isBlank(collect.getLogoUrl()) || collect.getLogoUrl().length() > 300) {
                collect.setLogoUrl(Const.BASE_PATH + Const.default_logo);
            }
            collect.setUserId(getUserId());
            if (collectService.checkCollect(collect)) {
                Collect exist = collectRepository.findByIdAndUserId(collect.getId(), collect.getUserId());
                if (collect.getId() == null) {
                    collectService.saveCollect(collect);
                } else if (exist == null) {
                    //收藏别人的文章
                    collectService.otherCollect(collect);
                } else {
                    collectService.updateCollect(collect);
                }
            } else {
                return result(ExceptionMsg.CollectExist);
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("collect failed, ", e);
            return result(ExceptionMsg.FAILED);
        }
        return result();
    }

    @RequestMapping(value = "/getCollectLogoUrl", method = RequestMethod.POST)
    @LoggerManage(description = "获取收藏页面的LogoUrl")
    public String getCollectLogoUrl(String url) {
        if (StringUtils.isNotBlank(url)) {
            String logoUrl = cacheService.getMap(url);
            if (StringUtils.isNotBlank(logoUrl)) {
                return logoUrl;
            } else {
                return Const.default_logo;
            }
        } else {
            return Const.default_logo;
        }
    }

    /**
     * 根据收藏的文章标题和描述推荐收藏夹
     */
    @RequestMapping(value = "/getFavoriteResult", method = RequestMethod.POST)
    @LoggerManage(description = "获取推荐收藏夹")
    public Map<String, Object> getFavoriteResult(String title, String description) {
        Long result = null;
        int faultPosition = 0;
        Map<String, Object> maps = new HashMap<String, Object>();
        List<Favorites> favoritesList = this.favoritesRepository.findByUserIdOrderByLastModifyTimeDesc(getUserId());
        for (int i = 0; i < favoritesList.size(); i++) {
            Favorites favorites = favoritesList.get(i);
            if (favorites.getName().indexOf(title) > 0 || favorites.getName().indexOf(description) > 0) {
                result = favorites.getId();
            }
            if ("未读列表".equals(favorites.getName())) {
                faultPosition = i;
            }
        }
        result = result == null ? favoritesList.get(faultPosition).getId() : result;
        maps.put("favoritesResult", result == null ? 0 : result);
        maps.put("favoritesList", favoritesList);
        return maps;
    }

    /**
     * @param page
     * @param size
     * @param type
     * @return
     * @author neo
     * @date 2016年8月25日
     */
    @GetMapping(value = "/standard/{type}/{favoritesId}/{userId}/{category}")
    @LoggerManage(description = "文章列表standard")
    public List<CollectSummary> standard(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "15") Integer size, @PathVariable("type") String type,
                                         @PathVariable("favoritesId") Long favoritesId, @PathVariable("userId") Long userId,
                                         @PathVariable("category") String category) {
        Sort sort = Sort.by(Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<CollectSummary> collects = null;
        if ("otherpublic".equalsIgnoreCase(type)) {
            if (null != favoritesId && 0 != favoritesId) {
                collects = collectService.getCollects(type, userId, pageable, favoritesId, getUserId());
            } else {
                collects = collectService.getCollects("others", userId, pageable, null, getUserId());
            }
        } else if (category != null && !"".equals(category) && !"NO".equals(category)) {//用于随便看看功能中收藏列表显示
            collects = lookAroundService.queryCollectExplore(pageable, getUserId(), category);
        } else if (type != null && !"".equals(type) && "lookRecord".equals(type)) {//用于浏览记录功能中收藏列表显示
            collects = lookRecordService.getLookRecords(this.getUserId(), pageable);
        } else {
            if (null != favoritesId && 0 != favoritesId) {
                collects = collectService.getCollects(String.valueOf(favoritesId), getUserId(), pageable, null, null);
            } else {
                collects = collectService.getCollects(type, getUserId(), pageable, null, null);
            }
        }
        return collects;
    }

    @GetMapping(value = "/lookAround")
    @LoggerManage(description = "查看更多lookAround")
    public List<CollectSummary> lookAround(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                           @RequestParam(value = "size", defaultValue = "15") Integer size) {
        Sort sort = Sort.by(Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<CollectSummary> collects = lookAroundService.queryCollectExplore(pageable, getUserId(), null);
        return collects;
    }

    /**
     * @param page
     * @param size
     * @param type
     * @return
     * @author neo
     * @date 2016年8月25日
     */
    @GetMapping(value = "/simple/{type}/{favoritesId}/{userId}/{category}")
    @LoggerManage(description = "文章列表simple")
    public List<CollectSummary> simple(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                       @RequestParam(value = "size", defaultValue = "15") Integer size, @PathVariable("type") String type,
                                       @PathVariable("favoritesId") Long favoritesId, @PathVariable("userId") Long userId
            , @PathVariable("category") String category) {
        Sort sort = Sort.by(Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<CollectSummary> collects = null;
        if ("otherpublic".equalsIgnoreCase(type)) {
            if (null != favoritesId && 0 != favoritesId) {
                collects = collectService.getCollects(type, userId, pageable, favoritesId, getUserId());
            } else {
                collects = collectService.getCollects("others", userId, pageable, null, getUserId());
            }
        } else if (category != null && !"".equals(category) && !"NO".equals(category)) {//用于随便看看功能中收藏列表显示
            collects = lookAroundService.queryCollectExplore(pageable, getUserId(), category);
        } else {
            if (null != favoritesId && 0 != favoritesId) {
                collects = collectService.getCollects(String.valueOf(favoritesId), getUserId(), pageable, null, null);
            } else {
                collects = collectService.getCollects(type, getUserId(), pageable, null, null);
            }
        }
        return collects;
    }

    /**
     * @param id
     * @param type
     * @author neo
     * @date 2016年8月24日
     */
    @PostMapping(value = "/changePrivacy/{id}/{type}")
    public Response changePrivacy(@PathVariable("id") long id, @PathVariable("type") CollectType type) {
        collectRepository.modifyByIdAndUserId(type, id, getUserId());
        return result();
    }

    /**
     * like and unlike
     *
     * @param id
     * @return
     * @author neo
     * @date 2016年8月24日
     */
    @PostMapping(value = "/like/{id}")
    @LoggerManage(description = "文章点赞或者取消点赞")
    public Response like(@PathVariable("id") long id) {
        try {
            collectService.like(getUserId(), id);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("文章点赞或者取消点赞异常：", e);
        }
        return result();

    }


    /**
     * @param id
     * @return
     * @author neo
     * @date 2016年8月24日
     */
    @DeleteMapping(value = "/delete/{id}")
    public Response delete(@PathVariable("id") long id) {
        Collect collect = collectRepository.findById(id);
        if (null != collect && getUserId() == collect.getUserId()) {
            collectRepository.deleteById(id);
            if (null != collect.getFavoritesId() && !IsDelete.YES.equals(collect.getIsDelete())) {
                favoritesRepository.reduceCountById(collect.getFavoritesId(), new Timestamp(System.currentTimeMillis()));
            }
        }
        return result();
    }

    /**
     * @param id
     * @return
     * @author neo
     * @date 2016年8月24日
     */
    @GetMapping(value = "/detail/{id}")
    public Collect detail(@PathVariable("id") long id) {
        Collect collect = collectRepository.findById(id);
        return collect;
    }


    /**
     * 导入收藏夹
     */
    @PostMapping("/import")
    @LoggerManage(description = "导入收藏夹操作")
    public void importCollect(@RequestParam("htmlFile") MultipartFile htmlFile, String structure, String type) {
        try {
            if (StringUtils.isNotBlank(structure) && IsDelete.YES.toString().equals(structure)) {
                // 按照目录结构导入
                Map<String, Map<String, String>> map = HtmlUtil.parseHtmlTwo(htmlFile.getInputStream());
                if (null == map || map.isEmpty()) {
                    logger.info("未获取到url连接");
                    return;
                }
                for (Entry<String, Map<String, String>> entry : map.entrySet()) {
                    String favoritesName = entry.getKey();
                    Favorites favorites = favoritesRepository.findByUserIdAndName(getUserId(), favoritesName);
                    if (null == favorites) {
                        favorites = favoritesService.saveFavorites(getUserId(), favoritesName);
                    }
                    collectService.importHtml(entry.getValue(), favorites.getId(), getUserId(), type);
                }
            } else {
                Map<String, String> map = HtmlUtil.parseHtmlOne(htmlFile.getInputStream());
                if (null == map || map.isEmpty()) {
                    logger.info("未获取到url连接");
                    return;
                }
                // 全部导入到<导入自浏览器>收藏夹
                Favorites favorites = favoritesRepository.findByUserIdAndName(getUserId(), "导入自浏览器");
                if (null == favorites) {
                    favorites = favoritesService.saveFavorites(getUserId(), "导入自浏览器");
                }
                collectService.importHtml(map, favorites.getId(), getUserId(), type);
            }
        } catch (Exception e) {
            logger.error("导入html异常:", e);
        }
    }

    /**
     * 导出收藏夹
     *
     * @param favoritesId
     * @return
     */
    @PostMapping("/export")
    @LoggerManage(description = "导出收藏夹操作")
    public void export(String favoritesId, HttpServletResponse response) {
        if (StringUtils.isNotBlank(favoritesId)) {
            try {
                String[] ids = favoritesId.split(",");
                String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                String fileName = "favorites_" + date + ".html";
                StringBuilder sb = new StringBuilder();
                for (String id : ids) {
                    try {
                        sb = sb.append(collectService.exportToHtml(Long.parseLong(id)));
                    } catch (Exception e) {
                        logger.error("异常：", e);
                    }
                }
                sb = HtmlUtil.exportHtml("云收藏夹", sb);
                response.setCharacterEncoding("UTF-8");
                response.setHeader("Content-disposition", "attachment; filename=" + fileName);
                response.getWriter().print(sb);
            } catch (Exception e) {
                logger.error("异常：", e);
            }
        }
    }


    @PostMapping(value = "/searchMy/{key}")
    public List<CollectSummary> searchMy(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "20") Integer size, @PathVariable("key") String key) {
        Sort sort = Sort.by(Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<CollectSummary> myCollects = collectService.searchMy(getUserId(), key, pageable);
        model.addAttribute("myCollects", myCollects);
        logger.info("searchMy end :");
        return myCollects;
    }

    @PostMapping(value = "/searchOther/{key}")
    public List<CollectSummary> searchOther(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page,
                                            @RequestParam(value = "size", defaultValue = "20") Integer size, @PathVariable("key") String key) {
        Sort sort = Sort.by(Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<CollectSummary> otherCollects = collectService.searchOther(getUserId(), key, pageable);
        logger.info("searchOther end :");
        return otherCollects;
    }

    /**
     * 查询点赞状态及该文章的点赞数量
     */
    @GetMapping(value = "/getPaiseStatus/{collectId}")
    public Map<String, Object> getPraiseStatus(Model model, @PathVariable("collectId") Long collectId) {
        Map<String, Object> maps = new HashMap<String, Object>();
        Praise praise = praiseRepository.findByUserIdAndCollectId(getUserId(), collectId);
        Long praiseCount = praiseRepository.countByCollectId(collectId);
        maps.put("status", praise != null ? "praise" : "unpraise");
        maps.put("praiseCount", praiseCount);
        return maps;
    }

}