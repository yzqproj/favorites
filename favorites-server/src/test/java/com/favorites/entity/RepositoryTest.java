//package com.favorites.entity;
//
//import com.favorites.mapper.CommentMapper;
//import com.favorites.mapper.FollowMapper;
//import com.favorites.mapper.PraiseMapper;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.List;
//
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//public class RepositoryTest {
//
//	@Autowired
//	private PraiseMapper praiseMapper;
//
//	@Autowired
//	private CommentMapper commentMapper;
//
//	@Autowired
//	private FollowMapper followMapper;
//
//	@Test
//	public void testPraise() throws Exception {
//		long count= praiseMapper.countByCollectId(1l);
//		System.out.println("count===="+count);
//		Praise praise= praiseMapper.findByUserIdAndCollectId(1l, 1l);
//		System.out.println("exists===="+praise);
//
//	}
//
//
//	@Test
//	public void testComment() throws Exception {
//		long count= commentMapper.countByCollectId(1l);
//		System.out.println("count===="+count);
//
//	}
//
//
//	@Test
//	public void testFollow() throws Exception {
//		List<Long> userIds= followMapper.findMyFollowIdByUserId(1l);
//		for(Long userId:userIds){
//			System.out.println("userId===="+userId);
//		}
//
//	}
//
//
//}