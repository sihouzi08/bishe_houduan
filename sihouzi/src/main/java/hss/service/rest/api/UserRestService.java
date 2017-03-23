package hss.service.rest.api;

import com.foreveross.springboot.dubbo.utils.Payload;
import hss.domain.User;

/**
 * Created by ClownMonkey on 2016/11/19.
 */
public interface UserRestService {
    Payload updateUserById(Integer id, User user);

    public Payload getUserList();

    public Payload getUserListpage();

    Payload getUserById(Integer id);

    public Payload createUser(User user);

    Payload deleteUserById(Integer id);
}
