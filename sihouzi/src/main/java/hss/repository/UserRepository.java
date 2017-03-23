package hss.repository;


/**
 * Created by Foreveross on 2016/11/15.
 */

import hss.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by 钟述林 393156105@qq.com on 2016/10/19 11:41.
 */
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
   // User findByName(int id);
   List<User> findByUserName(String userName);
//    @Query("FROM User u WHERE u.userName=?1 AND u.password IS NOT NULL")
//    List<User> findAll(String userName);
}