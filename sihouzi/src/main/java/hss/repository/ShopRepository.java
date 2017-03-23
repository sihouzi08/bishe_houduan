package hss.repository;

import hss.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Foreveross on 2016/11/20.
 */
@Transactional
public interface ShopRepository extends JpaRepository<Shop, Integer> {

}
