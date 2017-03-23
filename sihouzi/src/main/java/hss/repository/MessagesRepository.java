package hss.repository;

import hss.domain.Messages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by ClownMonkey on 2016/11/20.
 */
@Repository
public interface MessagesRepository extends JpaRepository<Messages, Integer>, JpaSpecificationExecutor<Messages>{

    @Query(value ="select * from messages,userinfo,shopinfo where messages.userid=userinfo.userid and messages.shopid=shopinfo.shopid and userinfo.username = ?1", nativeQuery = true)
    List<Messages> findByUsername(String username);

}
