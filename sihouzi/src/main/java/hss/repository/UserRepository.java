package hss.repository;



import hss.domain.User;
import hss.tools.BaseSearch;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by ClownMonkey on 2016/11/15.
 * 实体类user的jpa关联
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer>  , JpaSpecificationExecutor<User> {
   // User findByName(int id);
   List<User> findByUserName(String userName);


//    @Query("FROM User u WHERE u.userName=?1 AND u.password IS NOT NULL")
//    List<User> findAll(String userName);
}