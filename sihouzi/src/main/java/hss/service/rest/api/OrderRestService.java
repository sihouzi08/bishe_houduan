package hss.service.rest.api;

import com.foreveross.springboot.dubbo.utils.Payload;
import hss.domain.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ClownMonkey on 2017/1/12.
 * order的抽象接口方法
 */
public interface OrderRestService  {

    //order带条件分页
    public Payload getOrderPageList( int page, int size, String sort, String operation, String key, String value);

    //假删除，修改order状态，0和1
    public String amendShop_statusById(Integer id, Integer paystate);

    //通过id修改order对象
    public Payload updateShopById(Integer id, Order jsonObj);

    //导出order数据为excel表
    public String download(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
