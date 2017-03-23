package hss.service.rest;

import com.alibaba.druid.util.StringUtils;
import com.foreveross.springboot.dubbo.utils.Payload;
import hss.domain.Messages;
import hss.repository.MessagesRepository;
import hss.service.rest.api.MessagesRsetService;
import hss.tools.BaseSearch;
import hss.tools.SearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.util.Arrays;

/**
 * Created by ClownMonkey on 2017/1/3.
 */

@RestController
@RequestMapping(value = "/messages")     // 通过这里配置使下面的映射都在/messages下
@SpringBootApplication
public class MessagesRsetServiceImpl implements MessagesRsetService {

    @Autowired
    private MessagesRepository messagesRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/amendmessages_status", method = RequestMethod.PUT)
    @ResponseBody
    public String amendShop_statusById(@QueryParam("id") Integer id, @QueryParam("messages_status") String messages_status) {
        Messages messages = messagesRepository.findOne(id);
        messages.setLeave_status(messages_status);
        messagesRepository.save(messages);
        return "success";
    }


    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)//根据id修改
    @ResponseBody
    public Payload updateShopById(@PathVariable Integer id, @RequestBody Messages jsonObj) {
        Messages messages = new Messages();
//        messages.setShopid(id);
        messages.setUserName(jsonObj.getUserName());
        messages.setContent(jsonObj.getContent());
        messages.setReceivename(jsonObj.getReceivename());
        messages.setLeave_time(jsonObj.getLeave_time());
        messages.setLeave_status(jsonObj.getLeave_status());
        messages.setUserid(jsonObj.getUserid());
        messages.setShopid(jsonObj.getShopid());
        Messages p = messagesRepository.save(messages);
        return new Payload(p);
    }


    @RequestMapping(value = "/messagespage", method = RequestMethod.GET)//查找评论所有分页并具有筛选功能
    public Payload getMessagesPageList(@QueryParam("page") @DefaultValue("0") int page,
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

        if(StringUtils.isEmpty(key) || StringUtils.isEmpty(operation)  || StringUtils.isEmpty(value)){
            logger.info("提示: key或者operation或者value为空 ");
        }

        if(!StringUtils.isEmpty(key) && !StringUtils.isEmpty(operation)  && !StringUtils.isEmpty(value)){
            logger.info("提示: key并且operation并且value都不为空 ");
            return new Payload(messagesRepository.findAll(new BaseSearch<Messages>(new SearchDto(key,operation, value)),pageable));
        }

        return new Payload(messagesRepository.findAll(pageable));
    }




}
