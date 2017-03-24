package hss.service.rest;

import com.foreveross.springboot.dubbo.utils.Payload;
import hss.domain.Order;
import hss.domain.Shop;
import hss.domain.User;
import hss.repository.OrderRepository;
import hss.repository.ShopRepository;
import hss.repository.UserRepository;
import hss.service.rest.api.OrderRestService;
import hss.tools.BaseSearch;
import hss.tools.SearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.*;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.squareup.okhttp.internal.Internal.logger;

/**
 * Created by ClownMonkey on 2017/1/12.
 */
@RestController
@RequestMapping(value = "/order")     // 通过这里配置使下面的映射都在/order下
@SpringBootApplication
public class OrderRestServiceImpl implements OrderRestService {

    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/testAddOrder")
    public void testAddOrder() {
        int num=(int)(Math.random()*40)+10;
        for (Integer i = 0; i < 11; i++) {
            Order order = new Order();
            order.setMoneySum(num);
            order.setAmount(i + 3);

            order.setUserid(i);
            if (i % 2 == 0) {
                order.setPaystate(0);
                order.setUserid(0);
                order.setShopid(2);
            } else {
                order.setPaystate(1);
                order.setUserid(1);
                order.setShopid(3);
            }
            orderRepository.save(order);
        }
    }


    @RequestMapping(value = "/amendpaystatus", method = RequestMethod.PUT)
    @ResponseBody
    public String amendShop_statusById(@QueryParam("id") Integer id, @QueryParam("paystate") Integer paystate) {
        Order order = orderRepository.findOne(id);
        order.setPaystate(paystate);
        orderRepository.save(order);
        return "success";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)//根据id修改
    @ResponseBody
    public Payload updateShopById(@PathVariable Integer id, @RequestBody Order jsonObj) {

        Order order = orderRepository.findOne(id);
        if(order==null){
            logger.info("url出错");
            return new Payload("没找到这个id或者url出错");
        }
        if (jsonObj.getShopid() != null) {
            order.setShopid(jsonObj.getShopid());
            Shop shop = shopRepository.findOne(jsonObj.getShopid());
            order.setShop(shop);
        }
        if (jsonObj.getUserid() != null) {
            order.setUserid(jsonObj.getUserid());
            User user = userRepository.findOne(jsonObj.getUserid());
            order.setUser(user);
        }
        order.setOrdertime(jsonObj.getOrdertime());
        order.setMoneySum(jsonObj.getMoneySum());
        order.setAmount(jsonObj.getAmount());
        order.setPaystate(jsonObj.getPaystate());

        orderRepository.save(order);
        return new Payload(order);
    }


    @RequestMapping(value = "/orderspage", method = RequestMethod.GET)
    public Payload getOrderPageList(@QueryParam("page") @DefaultValue("0") int page,
                                    @QueryParam("size") @DefaultValue("50") int size,
                                    @QueryParam("sort") @DefaultValue("sort=shopid,desc") String sort,
                                    @QueryParam("operation") @DefaultValue("operation=eq") String operation,
                                    @QueryParam("key") @DefaultValue("key=messageid") String key,
                                    @QueryParam("value") @DefaultValue("1") String value) {
//        logger.debug("get roles: {} . -> {}", "GET /api/v1/roles/", sort);

        // 简单适配, 暂无严谨性
        sort = sort.replaceAll("sort=", "").trim(); // sort=firstname,desc
        String[] sort_arr = sort.split(",");
        Sort.Direction direction;
        if (sort_arr[1] != null && "desc".equals(sort_arr[1])) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }
        String[] properties = sort_arr[0].split("&");

        Sort _sort = new Sort(direction, Arrays.asList(properties));
        Pageable pageable = new PageRequest(page, size, _sort);
            if (StringUtils.isEmpty(key) || StringUtils.isEmpty(operation) || StringUtils.isEmpty(value)) {
                logger.info("提示: key或者operation或者value为空 ");
            }

        if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(operation) && !StringUtils.isEmpty(value)) {
            logger.info("提示: key并且operation并且value都不为空 ");
            return new Payload(orderRepository.findAll(new BaseSearch<Order>(new SearchDto(key, operation, value)), pageable));
        }

        return new Payload(orderRepository.findAll(pageable));
    }


}
