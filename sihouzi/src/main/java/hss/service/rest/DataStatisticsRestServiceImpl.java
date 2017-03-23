package hss.service.rest;

import com.alibaba.druid.util.StringUtils;
import com.foreveross.springboot.dubbo.utils.Payload;
import hss.domain.*;
import hss.repository.MessagesRepository;
import hss.repository.OrderRepository;
import hss.repository.ShopRepository;
import hss.repository.UserRepository;
import hss.service.rest.api.DataStatisticsRestService;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

import java.util.ArrayList;
import java.util.List;

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


    @RequestMapping(value = "/username", method = RequestMethod.GET)
    public Payload getOrderPageList(@QueryParam("username") @DefaultValue("username=侯圣燊") String username, @QueryParam("page")int page, @QueryParam("size")int size) {
        List<Order> list = orderRepository.findByUsername(username);
        PageDto pageDto = new PageDto();
        pageDto.setTotalElements(list.size());
        StringBuilder contentsql = new StringBuilder();//select * from orderinfo,userinfo,shopinfo where orderinfo.userid=userinfo.userid and orderinfo.shopid=shopinfo.shopid and userinfo.username = ?1 order by orderid asc limit 0, 3
        contentsql.append("select * from Orderinfo,Userinfo,Shopinfo where Orderinfo.userid=Userinfo.userid and Orderinfo.shopid=Shopinfo.shopid and Userinfo.username='" + username + "' limit "+ page * size);
        List jdbcList;
       /*
        page,size(1,5)
        int i = page * size + size;//10
        int o = page * size;//5
        (5,10)
       */
        contentsql.append("," +(page * size + size));
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
                                       @QueryParam("page")int page, @QueryParam("size")int size,
                                       @QueryParam("shopname") @DefaultValue("shopname=单车") String shopname) {
        List<Order> list = new ArrayList();
        if(!StringUtils.isEmpty(shopname) && StringUtils.isEmpty(username)){
            list = orderRepository.findByShopname(shopname);
        }else if(!StringUtils.isEmpty(username) && StringUtils.isEmpty(shopname)){
            list = orderRepository.findByUsername(username);
        }
        PageDto pageDto = new PageDto();
        pageDto.setTotalElements(list.size());
        pageDto.setSize(size);

        if(list.size()%size==0){
            pageDto.setTotalPages(list.size()/size);
        }else {
            pageDto.setTotalPages(list.size()/size+1);
        }
       /*
        page,size(1,5)
        int i = page * size + size;//10
        int o = page * size;//5
        (5,10)
       */
        List<Order> jdbcList = new ArrayList();
        for( Integer i = 0; i < size; i++){
              if((page * size + i) >= list.size()){//3*2+2<7?
                     logger.info("page * size + i = "+(page * size + i));
              }else {
                  jdbcList.add(list.get(page * size+i));
              }
        }
        pageDto.setContent(jdbcList);

        return new Payload(pageDto);
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
    public Payload getOrderPageList2(@QueryParam("username") @DefaultValue("username=侯圣燊") String username, @QueryParam("page")int page, @QueryParam("size")int size) {

       List<Messages> listmessages = new ArrayList();

       listmessages = messagesRepository.findByUsername(username);
        PageDto pageDto = new PageDto();
        pageDto.setTotalElements(listmessages.size());
        pageDto.setSize(size);
        if(listmessages.size()%size==0){
            pageDto.setTotalPages(listmessages.size()/size);
        }else {
            pageDto.setTotalPages(listmessages.size()/size+1);
        }

       /*
        page,size(1,5)
        int i = page * size + size;//10
        int o = page * size;//5
        (5,10)
       */
        List<Messages> jdbcList = new ArrayList();
        for( Integer i = 0; i < size; i++){
            if((page * size + i) >= listmessages.size()){//3*2+2<7?
                logger.info("page * size + i = "+(page * size + i));
            }else {
                jdbcList.add(listmessages.get(page * size+i));
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
