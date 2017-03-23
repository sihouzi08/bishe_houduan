package hss.service.rest;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Foreveross on 2017/1/3.
 */

@RestController
@RequestMapping(value = "/messages")     // 通过这里配置使下面的映射都在/messages下
@SpringBootApplication
public class MessagesRsetServiceImpl implements MessagesRsetService {

    @Autowired
    private MessagesRepository messagesRepository;



    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Payload getMessagesList(){
        Pageable pageable =new PageRequest(0, 5);
        Page<Messages> datas = messagesRepository.findAll(pageable);
        System.out.println("总条数："+datas.getTotalElements());
        System.out.println("总页数："+datas.getTotalPages());
        for(Messages u : datas) {
            System.out.println(u.getShopid()+"====>"+u.getShopname());
        }

        return new Payload(messagesRepository.findAll(pageable));
    }


}
