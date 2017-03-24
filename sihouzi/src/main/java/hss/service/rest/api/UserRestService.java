package hss.service.rest.api;

import com.foreveross.springboot.dubbo.utils.Payload;
import hss.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ClownMonkey on 2016/11/19.
 * 关于user对象操作的抽象方法
 */
public interface UserRestService {

    //通过userid修改数据库user对象
    Payload updateUserById(Integer id, User user);

    //通过jpa规则获取所有的user对象
    public Payload getUserList();


//    public Payload getUserListpage();

    //导出user数据表为excel文件
    public String download(HttpServletRequest request, HttpServletResponse response) throws IOException;

    //假删除操作 即是修改user状态 0和1
    public String amendShop_statusById(Integer id,int userstatus);

    //通过username查找一个user记录
    public Payload getUserByUserName(String username);

    //通过参数获取user所有对象并分页 还支持筛选
    public Payload getGroupList(int page, int size, String sort, String operation, String key, String value);

    //通过一个id查找一条user对象记录
    Payload getUserById(Integer id);

    //添加一个user对象 暂时没用到
    public Payload createUser(User user);

    //通过id删除一个user对象
    Payload deleteUserById(Integer id);
}
