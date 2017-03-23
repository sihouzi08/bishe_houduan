package hss.service.rest.api;

import com.foreveross.springboot.dubbo.utils.Payload;
import hss.domain.Shop;

/**
 * Created by Foreveross on 2016/11/20.
 */
public interface ShopRestService {

    Payload updateShopById(Integer id, Shop shop);

    public Payload getShopList();

    Payload getShopById(Integer id);

    public Payload createShop(Shop shop);

    Payload deleteShopById(Integer id);
}
