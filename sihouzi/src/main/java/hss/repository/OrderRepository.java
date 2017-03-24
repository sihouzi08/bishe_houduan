package hss.repository;

import hss.domain.Order;
//import hss.domain.Shop;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ClownMonkey on 2017/1/12.
 * order对象关联jpa
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {

    //sql自定义查询  通过username查找order
    @Query(value ="select * from orderinfo,userinfo,shopinfo where orderinfo.userid=userinfo.userid and orderinfo.shopid=shopinfo.shopid and userinfo.username = ?1", nativeQuery = true)
    List<Order> findByUsername(String username);

    //sql自定义查询  通过shopname查找order
    @Query(value ="select * from orderinfo,userinfo,shopinfo where orderinfo.userid=userinfo.userid and orderinfo.shopid=shopinfo.shopid and Shopinfo.Shopname = ?1", nativeQuery = true)
    List<Order> findByShopname(String username);

    //sql自定义查询  通过category查找order
    @Query(value ="select * from orderinfo,userinfo,shopinfo,category where orderinfo.userid=userinfo.userid and orderinfo.shopid=shopinfo.shopid and shopinfo.categoryid=category.id and category.category = ?1", nativeQuery = true)
    List<Order> findByCategory(String category);



}
