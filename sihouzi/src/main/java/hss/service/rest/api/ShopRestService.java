package hss.service.rest.api;

import com.foreveross.springboot.dubbo.utils.Payload;
import hss.domain.Shop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ClownMonkey on 2016/11/20.
 * 关于shop对象操作的抽象方法
 */
public interface ShopRestService {

    //通过shopid修改数据库shop对象
    Payload updateShopById(Integer id, Shop shop);

    //通过jpa规则获取所有的shop对象
    public Payload getShopList();

    //通过id查找出一条shop记录
    Payload getShopById(Integer id);

    //导出shop数据表为excel文件
    public String download(HttpServletRequest request, HttpServletResponse response)throws IOException;

    //添加一个shop的记录，暂时没用到
    public Payload createShop(Shop shop);

    //通过参数获取shop所有对象并分页 还支持筛选
    public Payload getShopPageList(int page, int size, String sort, String operation, String key, String value);

    //通过id删除一个数据库shop记录，暂时前端还只是用的假删除
    Payload deleteShopById(Integer id);

    //假删除操作 即是修改shop状态 0和1
    String amendShop_statusById(Integer id,String shop_status);

    //通过shopname查找一条shop记录
    public Payload getUserByShopname(String shopname);
}
