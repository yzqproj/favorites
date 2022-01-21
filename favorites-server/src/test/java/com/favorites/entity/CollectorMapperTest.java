//package com.favorites.entity;
//
//import com.favorites.entity.view.IndexCollectorView;
//import com.favorites.mapper.CollectorMapper;
//import com.favorites.service.CollectorService;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.PersistenceUnit;
//import javax.persistence.Query;
//import java.util.List;
//
///**
// * @Description: 获取收藏家测试类
// * @Auth: yuyang
// * @Date: 2017/1/18 19:56
// * @Version: 1.0
// **/
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//public class CollectorMapperTest {
//
//    @PersistenceUnit
//    private EntityManagerFactory emf;
//    @Autowired
//    private CollectorMapper collectorMapper;
//    @Autowired
//    private CollectorService collectorService;
//
//    @Test
//    public void test(){
//        EntityManager em=emf.createEntityManager();
//        String querySql = "SELECT follow_id as user_id,COUNT(1) AS counts FROM follow GROUP BY follow_id ORDER BY counts DESC LIMIT 1";
//        Query query=em.createNativeQuery(querySql);
//        List objecArraytList = query.getResultList();
//        Object[] obj = (Object[]) objecArraytList.get(0);
//        System.out.println("+++++++++++++++++++++++++++++++++++++ user_id:"+obj[0]);
//        System.out.println("+++++++++++++++++++++++++++++++++++++ counts:"+obj[1]);
//        em.close();
//    }
//
//    @Test
//    public void getMostUser(){
//        Long collectUserId = collectorMapper.getMostCollectUser();
//        System.out.println("+++++++++++++++++++++++++++++++++++++ collectUserId:"+collectUserId);
//        Long followedUserid = collectorMapper.getMostFollowedUser(collectUserId);
//        System.out.println("+++++++++++++++++++++++++++++++++++++ followedUserid:"+followedUserid);
//        String notUserIds = collectUserId+","+followedUserid;
//        Long praiseUserid = collectorMapper.getMostPraisedUser(notUserIds);
//        System.out.println("+++++++++++++++++++++++++++++++++++++ praiseUserid:"+praiseUserid);
//        notUserIds = notUserIds+","+praiseUserid;
//        Long commentUserid = collectorMapper.getMostCommentedUser(notUserIds);
//        System.out.println("+++++++++++++++++++++++++++++++++++++ commentUserid:"+commentUserid);
//        notUserIds = notUserIds+","+commentUserid;
//        Long popularUserid = collectorMapper.getMostPopularUser(notUserIds);
//        System.out.println("+++++++++++++++++++++++++++++++++++++ popularUserid:"+popularUserid);
//        notUserIds = notUserIds+","+popularUserid;
//        Long activeUserid = collectorMapper.getMostActiveUser(notUserIds);
//        System.out.println("+++++++++++++++++++++++++++++++++++++ activeUserid:"+activeUserid);
//    }
//
//    @Test
//    public void  getCollectors(){
//        IndexCollectorView indexCollectorView = collectorService.getCollectors();
//        System.out.println("+++++++++++++++++++++++++++++++++++++ collectors:"+indexCollectorView.getMostActiveUser());
//    }
//
//
//}
