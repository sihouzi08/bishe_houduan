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
import hss.utils.WebShopDto;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.io.*;
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
    @RequestMapping(value = "/shopname", method = RequestMethod.GET)
    public Payload getUserByShopname( @QueryParam("shopname") String shopname){
        return new Payload(shopRepository.findByShopname(shopname));
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




    public static String Date2FileName(String nameFormat, String fileType) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(nameFormat);
        String fileName = dateFormat.format(date) + fileType;
        return fileName;
    }



    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public String download(HttpServletRequest request, HttpServletResponse response) throws IOException {
         String fileName="yyyy-MM-dd HH:mm:ss";
         String fileType="_shop";
        fileName = Date2FileName(fileName,fileType);
//        StringBuilder fileName = new StringBuilder();
        //填充projects数据
        String _category = null;
        List<Shop> shop_data_list =shopRepository.findAll();
        List<WebShopDto> list = new ArrayList<WebShopDto>();
        for(Shop shop :shop_data_list){
            if(shop.getCategory_id()==1){
                _category = "运动";
            }else if(shop.getCategory_id()==2){
                _category = "电子";
            }else if(shop.getCategory_id()==3){
                _category = "书籍";
            }else if(shop.getCategory_id()==4){
                _category = "食物";
            }else {
                _category = "";
            }
            list.add(new WebShopDto(shop.getShopid(), shop.getShopname(), shop.getDes(), shop.getUserName(), shop.getUserphne(), _category, shop.getPicture(),shop.getPrice(), shop.getShop_status(),shop.getPut_time()));
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        ExcelUtil.getInstance().exportObj2Excel(os,list,WebShopDto.class);

        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
        return null;
    }



    /**
     * shop分页
     * @param page
     * @param size
     * @param sort
     * @param operation
     * @param key
     * @param value
     * @return
     */
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

}

