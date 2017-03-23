package hss.service.rest;

/**
 * Created by Foreveross on 2016/11/20.
 */

import hss.domain.Shop;
import hss.repository.ShopRepository;
import hss.service.rest.api.ShopRestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.foreveross.springboot.dubbo.utils.Payload;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Foreveross on 2016/11/19.
 */


@RestController
@RequestMapping(value = "/shop")     // 通过这里配置使下面的映射都在/users下
@SpringBootApplication
//
//@Service("userRestService")
//@Path("/user")
//@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
//@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class ShopRestServiceImpl implements ShopRestService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    String currTime = (new Date()).toLocaleString();
    @Autowired
    private ShopRepository shopRepository;

    @RequestMapping("/hehe")
    public String hehe() {
        return "现在时间：" + (new Date()).toLocaleString();
    }


    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Payload updateShopById(@PathVariable Integer id, @RequestBody Shop jsonObj) {
        Shop shop = new Shop();
        shop.setShopid(id);
        shop.setUserName(jsonObj.getUserName());
        shop.setShopname(jsonObj.getShopname());
        shop.setCategory(jsonObj.getCategory());
        shop.setDes(jsonObj.getDes());
        shop.setUserphne(jsonObj.getUserphne());
        shop.setPicture(jsonObj.getPicture());
        shop.setPrice(jsonObj.getPrice());
        shop.setPut_time(currTime);
        shop.setShop_status(jsonObj.getShop_status());
        System.out.print("" + jsonObj.getPut_time());
        Shop p = shopRepository.save(shop);
        return new Payload(p);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Payload getShopList() {
        return new Payload(shopRepository.findAll());
    }


    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Payload getUserListpage(){
        Pageable pageable =new PageRequest(0, 5);
        Page<Shop> datas = shopRepository.findAll(pageable);
        System.out.println("总条数："+datas.getTotalElements());
        System.out.println("总页数："+datas.getTotalPages());
        for(Shop u : datas) {
            System.out.println(u.getShopid()+"====>"+u.getShopname());
        }

        return new Payload(shopRepository.findAll(pageable));
    }

    @RequestMapping(value = "/newpage", method = RequestMethod.GET)
    public Payload getDataGroupList(@QueryParam("page") @DefaultValue("0") int page,
                                    @QueryParam("size") @DefaultValue("50") int size,
                                    @QueryParam("sort") @DefaultValue("sort=shopid,desc") String sort) {
        logger.debug("get roles: {} . -> {}", "GET /api/v1/roles/", sort);

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
        return new Payload(shopRepository.findAll(pageable));
    }



    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Payload getShopById(@PathVariable Integer id) {
        return new Payload(shopRepository.findOne(id));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Payload createShop(@RequestBody Shop jsonObj) {
        Shop shop = new Shop();

        System.out.println("ooo" + currTime);
        shop.setShopid(jsonObj.getShopid());
        shop.setUserName(jsonObj.getUserName());
        shop.setShopname(jsonObj.getShopname());
        shop.setCategory(jsonObj.getCategory());
        shop.setDes(jsonObj.getDes());
        shop.setPicture(jsonObj.getPicture());
        shop.setUserphne(jsonObj.getUserphne());
        shop.setPrice(jsonObj.getPrice());
        shop.setPut_time(currTime);
        shop.setShop_status(jsonObj.getShop_status());
        System.out.print("" + jsonObj.getPut_time());
        Shop p = shopRepository.save(shop);
        return new Payload(p);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Payload deleteShopById(@PathVariable Integer id) {
        shopRepository.delete(id);
        return new Payload(id);
    }

//    @GET
//    @Path("/")
//    public Payload getUserList() {
//        return new Payload(userRepository.findAll());
//    }
//
//    @GET
//    @Path("{id : \\d+}")
//    public Payload getUserById(@PathParam("id") Integer id) {
//        return new Payload(userRepository.findOne(id));
//    }


//    @POST
//    @Path("/")
//    public Payload createUser(@RequestBody User user) {
//        User p = userRepository.save(user);
//        return new Payload(p);
//    }
//
//    @PUT
//    @Path("{id : \\d+}")
//    public Payload updateUserById(@PathParam("id") Integer id, User user) {
//        return null;
//    }
//
//    @DELETE
//    @Path("{id : \\d+}")
//    public Payload deleteUserById(@PathParam("id") Integer id) {
//        userRepository.delete(id);
//        return new Payload(id);
//    }
}

