package com.favorites.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.favorites.entity.UrlLibrary;
import com.favorites.mapper.UrlLibraryMapper;
import com.favorites.utils.HtmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by DingYS on 2016/12/29.
 */
@Component
public class CacheService {

    private Map<String,String> maps = new ConcurrentHashMap<>();
    @Resource
    private UrlLibraryMapper urlLibraryMapper;

    public String getMap(String key){
        if(maps.isEmpty()){
           List<UrlLibrary> collectLibrarieList = urlLibraryMapper.selectList(new QueryWrapper<>());
            for(UrlLibrary urlLibrary : collectLibrarieList){
                maps.put(urlLibrary.getUrl(), urlLibrary.getLogoUrl());
            }
        }
        if(null == maps.get(key)){
            this.addMaps(key);
        }
        return maps.get(key);
    }


    public void addMaps(String key){
        if(key.contains("?")){
            key=key.substring(0,key.indexOf("?"));
        }
        String logoUrl = HtmlUtil.getImge(key);
        maps.put(key,logoUrl);
        UrlLibrary urlLibrary = new UrlLibrary();
        urlLibrary.setUrl(key);
        urlLibrary.setLogoUrl(logoUrl);
        urlLibraryMapper.insert(urlLibrary);
    }

    public boolean refreshOne(String key,String newValue){
        if(StringUtils.isNotBlank(key)){
            String value = getMap(key);
            if(StringUtils.isNotBlank(newValue) && !newValue.equals(value)){
                maps.put(key,newValue);
                return true;
            }
        }
        return false;
    }
}
