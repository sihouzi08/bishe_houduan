package hss.service.rest;



import com.foreveross.springboot.dubbo.utils.BeanHelper;
import hss.domain.Category;
import hss.domain.Shop;
import hss.repository.CategoryRepository;
import hss.repository.ShopRepository;
import hss.service.rest.api.ShopRestService;
import hss.tools.BaseSearch;
import hss.tools.SearchDto;
//import hss.tools.SearchTools;
import hss.utils.ExcelUtil;
import hss.utils.WebShopDtoAll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.foreveross.springboot.dubbo.utils.Payload;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ClownMonkey on 2016/11/19.
 *
 * 1.做到查询分页筛选的API
 *
 * 2.做到修改状态的API
 *
 */


@RestController
@RequestMapping(value = "/shop")     // 通过这里配置使下面的映射都在/shop下
@SpringBootApplication
//
//@Service("userRestService")
//@Path("/user")
//@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
//@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class ShopRestServiceImpl implements ShopRestService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    String currTime = (new Date()).toLocaleString();

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping("/hehe")
    public String hehe() {
        return "现在时间：" + (new Date()).toLocaleString();
    }


    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Payload updateShopById(@PathVariable Integer id, @RequestBody Shop jsonObj) {
        Shop shop = shopRepository.findOne(id);
        BeanHelper.mapPartOverrider(shop, jsonObj);
        if (jsonObj.getCategory_id() != null) {
            shop.setCategory_id(jsonObj.getCategory_id());
            Category category = categoryRepository.findOne(jsonObj.getCategory_id());
            shop.setCategory(category);
        }

//        shop.setShopid(id);
        shop.setUserName(jsonObj.getUserName());
        shop.setShopname(jsonObj.getShopname());

//        shop.setCategoryid(jsonObj.getCategoryid());
        shop.setDes(jsonObj.getDes());
        shop.setUserphne(jsonObj.getUserphne());
        shop.setPicture(jsonObj.getPicture());
        shop.setPrice(jsonObj.getPrice());
//        shop.setPut_time(currTime);
        shop.setShop_status(jsonObj.getShop_status());
        shopRepository.save(shop);
        return new Payload(shop);
    }


    @RequestMapping(value = "/amendShop_status", method = RequestMethod.PUT)
    @ResponseBody
    public String amendShop_statusById(@QueryParam("id") Integer id, @QueryParam("shop_status") String shop_status) {
        Shop shop = shopRepository.findOne(id);
        shop.setShop_status(shop_status);
        shopRepository.save(shop);
        return "success";
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Payload getShopList() {
        return new Payload(shopRepository.findAll());
    }


    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Payload getUserListpage(@QueryParam("page") @DefaultValue("0") int page,
                                   @QueryParam("size") @DefaultValue("50") int size){
        Pageable pageable =new PageRequest(0, 5);
        Page<Shop> datas = shopRepository.findAll(new BaseSearch<Shop>(new SearchDto("shop_status","eq", 1)),pageable);
        System.out.println("总条数："+datas.getTotalElements());
        System.out.println("总页数："+datas.getTotalPages());
        for(Shop u : datas) {
            System.out.println(u.getShopid()+"====>"+u.getShopname());
        }

        return new Payload(shopRepository.findAll(new BaseSearch<Shop>(new SearchDto("shop_status","eq", 1)),pageable));
    }




    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public String test4() throws Exception {
        List<Shop> list1 =shopRepository.findAll();
        List<WebShopDtoAll> list = new ArrayList<WebShopDtoAll>();
//        list.add(new WebShopDtoAll(11,"权限系统", "com", "admin", "1221","权限系统","权限系统","权限系统","权限系统","权限系统"));

        for(Shop shop :list1){
            list.add(new WebShopDtoAll(shop.getShopid(),shop.getShopname(),shop.getDes(),shop.getUserName(),shop.getUserphne(),shop.getCategory_id(),shop.getPicture(),shop.getPrice(),shop.getShop_status()));
        }

//        list.add(new WebShopDtoAll(33,"校园网", "zslin", "admin", "2112","校园网","校园网","校园网","校园网","校园网" ));
//        list.add(new WebShopDtoAll(33,"校园网", "zslin", "admin", "2112","校园网","校园网","校园网","校园网","校园网" ));
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("title", "Shop信息表");
//        map.put("total", list.size()+" 条");
//        map.put("date", getDate());

        ExcelUtil.getInstance().exportObj2Excel( new FileOutputStream("/root/"+"shop_test.xls"), list,WebShopDtoAll.class);

        return "success";

    }

    private String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(new Date());
    }


    public static String Date2FileName(String nameFormat, String fileType) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(nameFormat);
        String fileName = dateFormat.format(date) + fileType;
        return fileName;
    }


    @RequestMapping(value = "/newspage", method = RequestMethod.GET)
    public Payload getShopPageList(@QueryParam("page") @DefaultValue("0") int page,
                                    @QueryParam("size") @DefaultValue("50") int size,
                                    @QueryParam("sort") @DefaultValue("sort=shopid,desc") String sort,
                                @QueryParam("operation") @DefaultValue("operation=eq") String operation,
                                @QueryParam("key") @DefaultValue("key=shop_status") String key,
                                @QueryParam("value") @DefaultValue("1") String value) {
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

        if(StringUtils.isEmpty(key) || StringUtils.isEmpty(operation)  || StringUtils.isEmpty(value)){
            logger.info("提示: key或者operation或者value为空 ");
        }

        if(!StringUtils.isEmpty(key) && !StringUtils.isEmpty(operation)  && !StringUtils.isEmpty(value)){
            logger.info("提示: key并且operation并且value都不为空 ");
            return new Payload(shopRepository.findAll(new BaseSearch<Shop>(new SearchDto(key,operation, value)),pageable));
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
//        System.out.println("ooo" + currTime);
        shop.setShopid(jsonObj.getShopid());
        shop.setUserName(jsonObj.getUserName());
        shop.setShopname(jsonObj.getShopname());
        shop.setCategory(jsonObj.getCategory());
        shop.setDes(jsonObj.getDes());
        shop.setPicture(jsonObj.getPicture());
        shop.setUserphne(jsonObj.getUserphne());
        shop.setPrice(jsonObj.getPrice());
//        shop.setPut_time(currTime);
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

