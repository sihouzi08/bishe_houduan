package hss.repository;

import hss.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Created by Foreveross on 2016/11/20.
 */
@Transactional
public interface MessagesRepository extends JpaRepository<User, Integer> {
}
