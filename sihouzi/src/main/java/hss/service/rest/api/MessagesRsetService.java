package hss.service.rest.api;

import com.foreveross.springboot.dubbo.utils.Payload;
import hss.domain.Messages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ClownMonkey on 2017/1/3.
 * messages实体类的抽象接口方法
 */
public interface MessagesRsetService {

    //带条件的分页查询获取messages对象
    public Payload getMessagesPageList(int page, int size, String sort, String operation, String key, String value);

    //通过messagesid修改messages状态 0和1
    public String amendShop_statusById(Integer id, Integer messages_status);

    //通过id修改messages对象 参数必须为messages对象
    public Payload updateShopById( Integer id, Messages jsonObj);

    //导出messages数据到excel表
    public String download(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
