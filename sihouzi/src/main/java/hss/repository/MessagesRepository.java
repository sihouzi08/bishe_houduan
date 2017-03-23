package hss.repository;

import hss.domain.Messages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.transaction.Transactional;

/**
 * Created by Foreveross on 2016/11/20.
 */
@Transactional
public interface MessagesRepository extends JpaRepository<Messages, Integer>, JpaSpecificationExecutor<Messages>{

}
