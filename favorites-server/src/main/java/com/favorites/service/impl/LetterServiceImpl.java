package com.favorites.service.impl;

import com.favorites.entity.Letter;
import com.favorites.mapper.LetterMapper;
import com.favorites.service.ILetterService;
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
public class LetterServiceImpl extends ServiceImpl<LetterMapper, Letter> implements ILetterService {

}
