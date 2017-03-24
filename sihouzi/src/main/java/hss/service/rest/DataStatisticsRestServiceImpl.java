package hss.service.rest;

import com.alibaba.druid.util.StringUtils;
import com.foreveross.springboot.dubbo.utils.Payload;
import hss.domain.*;
import hss.repository.MessagesRepository;
import hss.repository.OrderRepository;
import hss.repository.ShopRepository;
import hss.repository.UserRepository;
import hss.service.rest.api.DataStatisticsRestService;
import hss.tools.BaseSearch;
import hss.tools.SearchDto;
import hss.tools.SearchTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.util.*;

import static com.squareup.okhttp.internal.Internal.logger;

//import static org.apache.poi.hslf.record.RecordTypes.List;

/**
 * Created by ClownMonkey on 2017/1/11.
 */
@RestController
@RequestMapping(value = "/datastatistics")     // 通过这里配置使下面的映射都在/shop下
@SpringBootApplication
public class DataStatisticsRestServiceImpl implements DataStatisticsRestService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessagesRepository messagesRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    //    @Override
    @RequestMapping(value = "/shoppricesum", method = RequestMethod.GET)
    public Payload getShopPriceSum() {
        return new Payload(shopRepository.getShopPriceSum());
//        return null;
    }



    @RequestMapping(value = "/getdata", method = RequestMethod.GET)
    public Payload getSum(@QueryParam("type") String type, @QueryParam("field") String field, @QueryParam("table") String table) {
//        return new Payload(shopRepository.getShopPriceSum());
        StringBuilder querysql = new StringBuilder();

        String s = "(" + field + ") as " + type + " from " + table;//eg:select sum(price) as sum from Shopinfo
        querysql.append("select " + type + s.trim());
        logger.info("querysql--> " + querysql);
        return new Payload(jdbcTemplate.queryForMap(querysql.toString().trim()));

    }

    //eg:select sum(moneysum) as sum1 from Shopinfo,Category,Orderinfo where Orderinfo.shopid=Shopinfo.shopid and Shopinfo.categoryid=Category.id and Orderinfo.ordertime between '2017-1-11 00:00:00' and '2017-1-13 00:00:00'
    @RequestMapping(value = "/getdatatest", method = RequestMethod.GET)
    public Payload getDataTest(@QueryParam("category") @DefaultValue("category=食物") String category) {


        HashMap<String, List<HashMap<String, Object>>> dimensions = new HashMap<String, List<HashMap<String, Object>>>();
        List<HashMap<String, Object>> mapmonth1 = new ArrayList<>();
        List<HashMap<String, Object>> mapmonth2 = new ArrayList<>();
        List<HashMap<String, Object>> mapmonth3 = new ArrayList<>();
        List<HashMap<String, Object>> mapmonth4 = new ArrayList<>();
        String querysql[] = new String[12];
        String month[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","_Dec"};
        String _category[] = {"运动","电子","书籍","食物"};
        int count=0;
         for(int j=0;j<_category.length;j++){
             for(int i=0;i<querysql.length;i++){
                 count++;
//eg:select sum(moneysum) as Feb from Shopinfo,Category,Orderinfo where Orderinfo.shopid=Shopinfo.shopid and Shopinfo.categoryid=Category.id and Orderinfo.ordertime between '2016-1-11 00:00:00' and '2017-1-13 00:00:00' and Category.category='食物'
                 querysql[i]="select sum(moneysum) as " + month[i].toString().trim() + " from Shopinfo,Category,Orderinfo where Orderinfo.shopid=Shopinfo.shopid and Shopinfo.categoryid=Category.id and Orderinfo.ordertime between '2016-"+(i+1)+"-1 00:00:00' and '2016-"+(i+2)+"-1 00:00:00' and Category.category='"+_category[j]+"'";
                 if(i==11){
                     querysql[i]="select sum(moneysum) as " + month[i].toString().trim() + " from Shopinfo,Category,Orderinfo where Orderinfo.shopid=Shopinfo.shopid and Shopinfo.categoryid=Category.id and Orderinfo.ordertime between '2016-"+12+"-1 00:00:00' and '2016-"+12+"-31 00:00:00' and Category.category='"+_category[j]+"'";
                 }
//                 logger.info(querysql[i].toString().trim());
//                 logger.info(month[i].toString().trim());
                 if(count<13){
                     mapmonth1.add((HashMap<String, Object>) jdbcTemplate.queryForMap(querysql[i].toString().trim()));
                 }else if(count<25){
                     mapmonth2.add((HashMap<String, Object>) jdbcTemplate.queryForMap(querysql[i].toString().trim()));
                 }else if(count<37){
                     mapmonth3.add((HashMap<String, Object>) jdbcTemplate.queryForMap(querysql[i].toString().trim()));
                 }else if(count<49){
                     mapmonth4.add((HashMap<String, Object>) jdbcTemplate.queryForMap(querysql[i].toString().trim()));
                 }else {
                     logger.info("出错");
                 }
             }

         }
        dimensions.put("yundong", mapmonth1);
        dimensions.put("dianzi", mapmonth2);
        dimensions.put("shuji", mapmonth3);
        dimensions.put("shiwu", mapmonth4);
//        }


        return new Payload(dimensions);
    }


    @RequestMapping(value = "/username", method = RequestMethod.GET)
    public Payload getOrderPageList(@QueryParam("username") @DefaultValue("username=侯圣燊") String username, @QueryParam("page") int page, @QueryParam("size") int size) {
        List<Order> list = orderRepository.findByUsername(username);
        PageDto pageDto = new PageDto();
        pageDto.setTotalElements(list.size());
        StringBuilder contentsql = new StringBuilder();//select * from orderinfo,userinfo,shopinfo where orderinfo.userid=userinfo.userid and orderinfo.shopid=shopinfo.shopid and userinfo.username = ?1 order by orderid asc limit 0, 3
        contentsql.append("select * from Orderinfo,Userinfo,Shopinfo where Orderinfo.userid=Userinfo.userid and Orderinfo.shopid=Shopinfo.shopid and Userinfo.username='" + username + "' limit " + page * size);
        List jdbcList;
       /*
        page,size(1,5)
        int i = page * size + size;//10
        int o = page * size;//5
        (5,10)
       */
        contentsql.append("," + (page * size + size));
        logger.info("contentsql-->   " + contentsql);

        jdbcList = this.jdbcTemplate.queryForList(contentsql.toString().trim());
        pageDto.setContent(jdbcList);


        return new Payload(pageDto);
    }

    /**
     * 通过username 找 user shop order  所以相关信息
     *
     * @param username
     * @param page
     * @param size
     * @return user-->shop-->order
     */
    @RequestMapping(value = "/username2", method = RequestMethod.GET)
    public Payload getMessagesPageList(@QueryParam("username") @DefaultValue("username=侯圣燊") String username,
                                       @QueryParam("page") int page, @QueryParam("size") int size,
                                       @QueryParam("sort") @DefaultValue("sort=shopid,desc") String sort,
                                       @QueryParam("shopname") @DefaultValue("shopname=单车") String shopname,
                                       @QueryParam("category") @DefaultValue("category=运动") String category) {
        List<Order> list = new ArrayList();
        if (!StringUtils.isEmpty(shopname) && StringUtils.isEmpty(username) && StringUtils.isEmpty(category)) {
            list = orderRepository.findByShopname(shopname);
        } else if (!StringUtils.isEmpty(username) && StringUtils.isEmpty(shopname) && StringUtils.isEmpty(category)) {
            list = orderRepository.findByUsername(username);
        } else if (!StringUtils.isEmpty(category) && StringUtils.isEmpty(username) && StringUtils.isEmpty(shopname)) {
            list = orderRepository.findByCategory(category);
        } else {
            logger.info("url出错");
        }
        PageDto pageDto = new PageDto();
        pageDto.setTotalElements(list.size());
        pageDto.setSize(size);

        if (list.size() % size == 0) {
            pageDto.setTotalPages(list.size() / size);
        } else {
            pageDto.setTotalPages(list.size() / size + 1);
        }
       /*
        page,size(1,5)
        int i = page * size + size;//10
        int o = page * size;//5
        (5,10)
       */
        List<Order> jdbcList = new ArrayList();
        for (Integer i = 0; i < size; i++) {
            if ((page * size + i) >= list.size()) {//3*2+2<7?
                logger.info("page * size + i = " + (page * size + i));
            } else {
                jdbcList.add(list.get(page * size + i));
            }
        }


        if(!StringUtils.isEmpty(sort)){
            sort = sort.replaceAll("sort=", "").trim(); // sort=firstname,desc
            String[] sort_arr = sort.split(",");
            logger.info("条件-->"+sort_arr[0]+"    "+"方式-->"+sort_arr[1]);


            if(sort_arr[0].equals("moneySum")){

                logger.info("条件moneySum-->"+sort_arr[0]);

                if(sort_arr[1].equals("asc")){
                    logger.info("方式顺序-->"+sort_arr[1]);
                    Collections.sort(jdbcList, new Comparator<Order>() {
                        @Override
                        public int compare(Order order1, Order order2) {
                            Integer id1 = order1.getMoneySum();
                            Integer id2 = order2.getMoneySum();
                            //可以按User对象的其他属性排序，只要属性支持compareTo方法
                            return id1.compareTo(id2);
                        }
                    });
                }
                if(sort_arr[1].equals("desc")){
                    logger.info("方式倒序-->"+sort_arr[1]);
                    Collections.sort(jdbcList, new Comparator<Order>() {
                        @Override
                        public int compare(Order order1, Order order2) {
                            Integer id1 = order1.getMoneySum();
                            Integer id2 = order2.getMoneySum();
                            //可以按User对象的其他属性排序，只要属性支持compareTo方法
                            return id2.compareTo(id1);
                        }
                    });
                }

            }
            if(sort_arr[0].equals("amount")){
                logger.info("条件amount-->"+sort_arr[0]);
                if(sort_arr[1].equals("asc")){
                    logger.info("方式顺序-->"+sort_arr[1]);
                    Collections.sort(jdbcList, new Comparator<Order>() {
                        @Override
                        public int compare(Order order1, Order order2) {
                            Integer id1 = order1.getAmount();
                            Integer id2 = order2.getAmount();
                            //可以按User对象的其他属性排序，只要属性支持compareTo方法
                            return id1.compareTo(id2);
                        }
                    });
                }
                if(sort_arr[1].equals("desc")){
                    logger.info("方式倒序-->"+sort_arr[1]);
                    Collections.sort(jdbcList, new Comparator<Order>() {
                        @Override
                        public int compare(Order order1, Order order2) {
                            Integer id1 = order1.getAmount();
                            Integer id2 = order2.getAmount();
                            //可以按User对象的其他属性排序，只要属性支持compareTo方法
                            return id2.compareTo(id1);
                        }
                    });
                }
            }
        }



        pageDto.setContent(jdbcList);

        return new Payload(pageDto);

    }








    @RequestMapping(value = "/date", method = RequestMethod.GET)
    public Payload getOrdersPageListByDate(   @QueryParam("page") int page, @QueryParam("size") int size,
                                              @QueryParam("sort") @DefaultValue("sort=shopid,desc") String sort,
                                              @QueryParam("field")  String field,
                                              @QueryParam("orderEndDateMin")  String orderEndDateMin,
                                              @QueryParam("orderEndDateMax")  String orderEndDateMax) {


        StringBuilder endtime = new StringBuilder();
        StringBuilder starttime = new StringBuilder();
        starttime.append(orderEndDateMin + " 00:00:00");
        endtime.append(orderEndDateMax + " 12:00:00");

        logger.info(sort);

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

          if(field.equals("Leave_time")){
              return new Payload(messagesRepository.findAll(SearchTools.buildSpecification(
                      SearchTools.buildSpeDto("and", new SearchDto("and", "Leave_time", "gt", starttime.toString().trim())),
                      SearchTools.buildSpeDto("and", new SearchDto("Leave_time", "lt", endtime.toString().trim()))),pageable));
          }
        return new Payload(orderRepository.findAll(SearchTools.buildSpecification(
                SearchTools.buildSpeDto("and", new SearchDto("and", "ordertime", "gt", starttime.toString().trim())),
                SearchTools.buildSpeDto("and", new SearchDto("ordertime", "lt", endtime.toString().trim()))),pageable));
    }



    /**
     * 通过username 找 user shop messages  所有相关信息
     *
     * @param username
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/messagesusername", method = RequestMethod.GET)
    public Payload getOrderPageList2(@QueryParam("username") @DefaultValue("username=侯圣燊") String username,
                                     @QueryParam("page") int page, @QueryParam("size") int size,
                                     @QueryParam("sort") @DefaultValue("sort=shopid,desc") String sort,
                                     @QueryParam("shopname") @DefaultValue("shopname=单车") String shopname,
                                     @QueryParam("category") @DefaultValue("category=运动") String category) {

        List<Messages> listmessages = new ArrayList();
        if (!StringUtils.isEmpty(shopname) && StringUtils.isEmpty(username) && StringUtils.isEmpty(category)) {
            listmessages = messagesRepository.findByShopname(shopname);
        } else if (!StringUtils.isEmpty(username) && StringUtils.isEmpty(shopname) && StringUtils.isEmpty(category)) {
            listmessages = messagesRepository.findByUsername(username);
        } else if (!StringUtils.isEmpty(category) && StringUtils.isEmpty(username) && StringUtils.isEmpty(shopname)) {
            listmessages = messagesRepository.findAll();
        } else {
            logger.info("url出错");
        }
        PageDto pageDto = new PageDto();
        pageDto.setTotalElements(listmessages.size());
        pageDto.setSize(size);
        if (listmessages.size() % size == 0) {
            pageDto.setTotalPages(listmessages.size() / size);
        } else {
            pageDto.setTotalPages(listmessages.size() / size + 1);
        }

       /*
        page,size(1,5)
        int i = page * size + size;//10
        int o = page * size;//5
        (5,10)
       */
        List<Messages> jdbcList = new ArrayList();
        for (Integer i = 0; i < size; i++) {
            if ((page * size + i) >= listmessages.size()) {//3*2+2<7?
                logger.info("page * size + i = " + (page * size + i));
            } else {
                jdbcList.add(listmessages.get(page * size + i));
            }
        }


        if(!StringUtils.isEmpty(sort)){
            sort = sort.replaceAll("sort=", "").trim(); // sort=firstname,desc
            String[] sort_arr = sort.split(",");
            logger.info("条件-->"+sort_arr[0]+"    "+"方式-->"+sort_arr[1]);


            if(sort_arr[0].equals("Leave_time")){

                logger.info("条件moneySum-->"+sort_arr[0]);

                if(sort_arr[1].equals("asc")){
                    logger.info("方式顺序-->"+sort_arr[1]);
                    Collections.sort(jdbcList, new Comparator<Messages>() {
                        @Override
                        public int compare(Messages messages1, Messages messages2) {
                            String id1 = String.valueOf(messages1.getContent());
                            String id2 = String.valueOf(messages2.getContent());
                            //可以按User对象的其他属性排序，只要属性支持compareTo方法
                            return id1.compareTo(id2);
                        }
                    });
                }
                if(sort_arr[1].equals("desc")){
                    logger.info("方式倒序-->"+sort_arr[1]);
                    Collections.sort(jdbcList, new Comparator<Messages>() {
                        @Override
                        public int compare(Messages messages1, Messages messages2) {
                            String id1 = String.valueOf(messages1.getContent());
                            String id2 = String.valueOf(messages2.getContent());
                            //可以按User对象的其他属性排序，只要属性支持compareTo方法
                            return id2.compareTo(id1);
                        }
                    });
                }

            }
        }



        pageDto.setContent(jdbcList);

        return new Payload(pageDto);
    }


    /**
     *通过username找他所有信息
     */

    /**
     *  统计初步设计API：
     *
     *A,对于单一user方面入手（给定一个username都可以找到所有跟该用户有关的信息，username-->findByUsername）
     * 1.该用户的订单信息（给定一个search参数）
     * 2.该用户所买过的商品
     * 3.该用户花钱状况
     * 4.该用户的评论情况
     * 5.。。。待定。。。
     *
     *B。图形统计
     * 1.图形种类：扇形，散点，折线，柱形图
     * 2.商品卖出情况（X-->种类[运动，电子，书籍]  Y-->[卖出总数]）
     * 3.扇形商品卖出比例
     * 4.散点商品分布哪个系（Y-->用户的系别）
     * 6.X从用户下手（用户-金额，用户-商品数） 从商品下手（eg上面） 评论下手（用户-评论数）
     * 5.。。。待定。。。
     *
     *C。各类产品的聚合函数
     * 1.某字段的avg sum max min
     * 2.花样去数据
     *
     *D。暂定
     * 1.excel生成
     * 2.产品推荐
     *
     *
     */

}
