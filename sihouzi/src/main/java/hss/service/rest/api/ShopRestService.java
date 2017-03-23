package hss.service.rest.api;

import com.foreveross.springboot.dubbo.utils.Payload;
import hss.domain.Shop;

/**
 * Created by ClownMonkey on 2016/11/20.
 */
public interface ShopRestService {

    Payload updateShopById(Integer id, Shop shop);

    public Payload getShopList();

    Payload getShopById(Integer id);

    public Payload createShop(Shop shop);

    public Payload getShopPageList(int page, int size, String sort, String operation, String key, String value);

    Payload deleteShopById(Integer id);
}
