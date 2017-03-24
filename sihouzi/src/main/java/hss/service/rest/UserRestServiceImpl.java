package hss.service.rest;

import com.alibaba.druid.util.StringUtils;
import hss.repository.UserRepository;
import hss.service.rest.api.UserRestService;
import hss.tools.BaseSearch;
import hss.tools.SearchDto;
import hss.utils.ExcelUtil;
import hss.utils.WebShopDto;
import hss.utils.WebUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.foreveross.springboot.dubbo.utils.Payload;
import hss.domain.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static hss.service.rest.ShopRestServiceImpl.Date2FileName;

/**
 * Created by ClownMonkey on 2016/11/19.
 *
 * 1.做到查询分页筛选的API
 *
 * 2.做到修改状态的API
 *
 * 3.做到了根据id，username，查找user对象的API
 *
 * 4.做到了导出user对象的excel的API
 *
 * 5.删除增加的API，暂时没用
 *
 */


@RestController
@RequestMapping(value="/users")     // 通过这里配置使下面的映射都在/users下
@SpringBootApplication

public class UserRestServiceImpl implements UserRestService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    /**
     * 测试方法 测试该url可执行
     * @return
     */
    @RequestMapping("/hehe")
    public String hehe() {
        return "现在时间：" + (new Date()).toLocaleString();
    }


    /**
     * 通过userid修改user对象，参数必须为user对象
     * @param id
     * @param jsonObj
     * @return
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Payload updateUserById(@PathVariable Integer id, @RequestBody User jsonObj){
        User user = userRepository.findOne(id);
//        user.setUserid(id);
        if(user==null){
            logger.info("url出错");
        }
        user.setUserName(jsonObj.getUserName());
        user.setPassword(jsonObj.getPassword());
        user.setEmail(jsonObj.getEmail());
        user.setCourt(jsonObj.getCourt());
        user.setSchool(jsonObj.getSchool());
        user.setProfessional(jsonObj.getProfessional());
        user.setPhone(jsonObj.getPhone());
        userRepository.save(user);
        return new Payload(user);
    }

    /**
     * 获取所有user对象
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Payload getUserList(){
        return new Payload(userRepository.findAll());
    }

    /*
    分页尝试 后来没用这个
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Payload getUserListpage(){
        Pageable pageable =new PageRequest(0, 5);
        Page<User> datas = userRepository.findAll(pageable);
        System.out.println("总条数："+datas.getTotalElements());
        System.out.println("总页数："+datas.getTotalPages());
        for(User u : datas) {
            System.out.println(u.getUserid()+"====>"+u.getUserName());
        }

        return new Payload(userRepository.findAll());
    }


    /**
     *
     * 导出shop数据表为excel文件
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public String download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName="yyyy-MM-dd HH:mm:ss";
        String fileType="_user.xls";

//        StringBuilder fileName = new StringBuilder();
        //填充projects数据
        String _userstatus = null;
        List<User> user_data_list =userRepository.findAll();
        List<WebUserDto> list = new ArrayList<>();
        for(User user :user_data_list){
            if(user.getUserstatus()==0){
                _userstatus = "拉黑用户";
            }else if(user.getUserstatus()==1){
                _userstatus = "正常用户";
            }else {
                _userstatus = "";
            }
            list.add(new WebUserDto(user.getUserid(),user.getUserName(),user.getPassword(),user.getEmail(),user.getSchool(),user.getCourt(),user.getProfessional(),_userstatus,user.getPhone()));
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        ExcelUtil.getInstance().exportObj2Excel(os,list,WebUserDto.class);

        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String(Date2FileName(fileName,fileType).getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
        return null;
    }

    /**
     * 假删除操作 即是修改状态 0和1
     * @param id
     * @param userstatus
     * @return
     */
    @RequestMapping(value = "/amenduserstatus", method = RequestMethod.PUT)
    @ResponseBody
    public String amendShop_statusById(@QueryParam("id") Integer id, @QueryParam("userstatus") int userstatus) {
        User user = userRepository.findOne(id);
        user.setUserstatus(userstatus);
        userRepository.save(user);
        return "success";
    }

    /**
     * 通过username查找一个user记录
     * @param username
     * @return
     */
    @RequestMapping(value = "/username", method = RequestMethod.GET)
    public Payload getUserByUserName( @QueryParam("username") String username){
        return new Payload(userRepository.findByUserName(username));
    }

    /**
     * 带条件的分页获取user对象 一些参数非必须
     * @param page
     * @param size
     * @param sort
     * @param operation
     * @param key
     * @param value
     * @return
     */
    @RequestMapping(value = "/userspage", method = RequestMethod.GET)
    public Payload getGroupList(@QueryParam("page") @DefaultValue("0") int page,
                                @QueryParam("size") @DefaultValue("50") int size,
                                @QueryParam("sort") @DefaultValue("sort=shopid,desc") String sort,
                                @QueryParam("operation") @DefaultValue("operation=eq") String operation,
                                @QueryParam("key") @DefaultValue("key=userid") String key,
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
            return new Payload(userRepository.findAll(new BaseSearch<User>(new SearchDto(key,operation, value)),pageable));
        }

        return new Payload(userRepository.findAll(pageable));
    }

    /**
     * 通过一个id查找一条user对象记录
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Payload getUserById(@PathVariable Integer id){
        return new Payload(userRepository.findOne(id));
    }

    /**
     * 添加一个user对象 暂时没用到
     * @param jsonObj
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Payload createUser(@RequestBody User jsonObj){
        List<User> users = this.userRepository.findByUserName(jsonObj.getUserName());
        if (users.size() == 0){
            User user = new User();
            user.setUserid(jsonObj.getUserid());
            user.setUserName(jsonObj.getUserName());
            user.setPassword(jsonObj.getPassword());
            user.setEmail(jsonObj.getEmail());
            user.setCourt(jsonObj.getCourt());
            user.setSchool(jsonObj.getSchool());
            user.setProfessional(jsonObj.getProfessional());
            user.setPhone(jsonObj.getPhone());
            System.out.print(""+jsonObj.getProfessional());
            User p = userRepository.save(user);
            return new Payload(p);
        }else{
            return new Payload("改用户名已经存在");
        }

    }

    /**
     * 通过id删除一个user对象
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Payload deleteUserById(@PathVariable Integer id){
        userRepository.delete(id);
        return new Payload(id);
    }

//    @GET
//    @Path("/")
//    public Payload getUserList() {
//        return new Payload(userRepository.findAll());
//    }
//
//    @GET
//    @Path("{id : \\d+}")
//    public Payload getUserById(@PathParam("id") Integer id) {
//        return new Payload(userRepository.findOne(id));
//    }


//    @POST
//    @Path("/")
//    public Payload createUser(@RequestBody User user) {
//        User p = userRepository.save(user);
//        return new Payload(p);
//    }
//
//    @PUT
//    @Path("{id : \\d+}")
//    public Payload updateUserById(@PathParam("id") Integer id, User user) {
//        return null;
//    }
//
//    @DELETE
//    @Path("{id : \\d+}")
//    public Payload deleteUserById(@PathParam("id") Integer id) {
//        userRepository.delete(id);
//        return new Payload(id);
//    }
}
