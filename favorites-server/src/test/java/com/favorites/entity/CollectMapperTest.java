//package com.favorites.entity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.favorites.entity.enums.IsDelete;
//
//import com.favorites.mapper.CollectMapper;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//
//
//import org.springframework.data.domain.Sort.Direction;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import com.favorites.entity.view.CollectView;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//public class CollectMapperTest {
//
//	@Autowired
//	private CollectMapper collectMapper;
//
//	@Test
//	public void test() throws Exception {
//	/*	List<CollectView> views=collectMapper.findByUserId(1l);
//	    for(CollectView view:views){
//	    	System.out.println("collect title ：" +view.getTitle());
//	    }	*/
//		collectMapper.deleteById(3l);
//	}
//
//
//	@Test
//	public void testFindView() throws Exception {
//	 /*   Page<CollectView> views=collectMapper.findByUserId(1l, PageRequest.of(0, 10, Direction.ASC, "title"));
//	    for(CollectView view:views){
//	    	System.out.println("collect title==" +view.getTitle());
//	    }*/
//	}
//
//
//	@Test
//	public void testFindAllView() throws Exception {
//	    Page<CollectView> views= collectMapper.findExploreView(1L,  PageRequest.of(0, 10, Direction.ASC, "title"));
//	    for(CollectView view:views){
//	    	System.out.print("   collect title==" +view.getTitle());
//	    	System.out.print("   FavoriteName==" +view.getFavoriteName());
//	    	System.out.print("   Username==" +view.getUsername());
//	    	System.out.println("   Url==" +view.getUrl());
//	    }
//	}
//
//
//	@Test
//	public void testFindViewByUserId() throws Exception {
//	    Page<CollectView> views= collectMapper.findViewByUserId(2L, PageRequest.of(0, 10, Sort.by(Direction.ASC, "title")));
//	    for(CollectView view:views){
//	    	System.out.print("   collect title==" +view.getTitle());
//	    	System.out.print("   FavoriteName==" +view.getFavoriteName());
//	    	System.out.print("   Username==" +view.getUsername());
//	    	System.out.println("   Url==" +view.getUrl());
//	    }
//	}
//
//	@Test
//	public void testFindViewByUserIdAndFollows() throws Exception {
//		List<Long> userIds=new ArrayList<Long>();
//		userIds.add(2l);
//		userIds.add(3l);
//	    Page<CollectView> views= collectMapper.findViewByUserIdAndFollows(2l,userIds,  PageRequest.of(0, 10, Direction.ASC, "title"));
//	    for(CollectView view:views){
//	    	System.out.print("   collect title==" +view.getTitle());
//	    	System.out.print("   FavoriteName==" +view.getFavoriteName());
//	    	System.out.print("   Username==" +view.getUsername());
//	    	System.out.println("   Url==" +view.getUrl());
//	    }
//	}
//
//
//	@Test
//	public void  Testcount(){
//		Long count= collectMapper.countByFavoritesIdAndIsDelete(4L, IsDelete.NO);
//		System.out.println("+++++++++++++++++++++++++++++++++++++ count:"+count);
//	}
//
//}