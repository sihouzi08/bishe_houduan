package hss.service.rest.api;

import com.foreveross.springboot.dubbo.utils.Payload;

/**
 * Created by ClownMonkey on 2017/1/3.
 */
public interface MessagesRsetService {


    public Payload getMessagesPageList(int page, int size, String sort, String operation, String key, String value);

}
