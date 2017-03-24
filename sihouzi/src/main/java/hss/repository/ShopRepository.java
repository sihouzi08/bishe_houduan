package hss.repository;

import hss.domain.Shop;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
//import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * Created by ClownMonkey on 2016/11/20.
 * 实体类shop的jpa关联
 */
@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> , JpaSpecificationExecutor<Shop> {

    //jpa查询规则，通过shopname查询
     List<Shop> findByShopname(String shopname);

     //自定义方法 根据sql语句来查询
     @Query(value ="select sum(price) from Shopinfo ", nativeQuery = true)
     Integer getShopPriceSum();

}
