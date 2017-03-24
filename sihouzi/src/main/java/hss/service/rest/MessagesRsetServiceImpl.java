package hss.service.rest;

import com.alibaba.druid.util.StringUtils;
import com.foreveross.springboot.dubbo.utils.Payload;
import hss.domain.Messages;
import hss.domain.Shop;
import hss.domain.User;
import hss.repository.MessagesRepository;
import hss.repository.ShopRepository;
import hss.repository.UserRepository;
import hss.service.rest.api.MessagesRsetService;
import hss.tools.BaseSearch;
import hss.tools.SearchDto;
import hss.utils.ExcelUtil;
import hss.utils.WebMessageDto;
import hss.utils.WebOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static hss.service.rest.ShopRestServiceImpl.Date2FileName;

/**
 * Created by ClownMonkey on 2016/11/19.
 *
 * 1.做到查询分页筛选的API
 *
 * 2.做到修改状态的API
 *
 * 3.做到了根据id查找messages对象并修改的API
 *
 * 4.做到了导出shop对象的excel的API
 *
 */

@RestController
@RequestMapping(value = "/messages")     // 通过这里配置使下面的映射都在/messages下
@SpringBootApplication
public class MessagesRsetServiceImpl implements MessagesRsetService {

    @Autowired
    private MessagesRepository messagesRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 通过messagesid修改messages状态 0和1
     * @param id
     * @param messages_status
     * @return
     */
    @RequestMapping(value = "/amendmessages_status", method = RequestMethod.PUT)
    @ResponseBody
    public String amendShop_statusById(@QueryParam("id") Integer id, @QueryParam("messages_status") Integer messages_status) {
        Messages messages = messagesRepository.findOne(id);
        messages.setLeave_status(messages_status);
        messagesRepository.save(messages);
        return "success";
    }

    /**
     * 通过id修改messages对象 参数必须为messages对象
     * @param id
     * @param jsonObj
     * @return
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)//根据id修改
    @ResponseBody
    public Payload updateShopById(@PathVariable Integer id, @RequestBody Messages jsonObj) {
        Messages messages = messagesRepository.findOne(id);
        if(messages==null){
            logger.info("url出错");
            return new Payload("没找到这个id或者url出错");
        }
        if (jsonObj.getShopid() != null) {
            messages.setShopid(jsonObj.getShopid());
            Shop shop = shopRepository.findOne(jsonObj.getShopid());
            messages.setShop(shop);
        }
        if (jsonObj.getUserid() != null) {
            messages.setUserid(jsonObj.getUserid());
            User user = userRepository.findOne(jsonObj.getUserid());
            messages.setUser(user);
        }
        messages.setUserName(jsonObj.getUserName());
        messages.setContent(jsonObj.getContent());
        messages.setReceivename(jsonObj.getReceivename());
        messages.setLeave_time(jsonObj.getLeave_time());
        messages.setLeave_status(jsonObj.getLeave_status());

        messagesRepository.save(messages);
        return new Payload(messages);
    }


    /**
     * 导出messages数据到excel表
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public String download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName="yyyy-MM-dd HH:mm:ss";
        String fileType="_messages.xls";

//        StringBuilder fileName = new StringBuilder();
        //填充projects数据
        String messages_status = null;
        List<Messages> messages_data_list =messagesRepository.findAll();
        List<WebMessageDto> list = new ArrayList<>();
        for(Messages messages :messages_data_list){
            if(messages.getLeave_status()==1){
                messages_status = "已经通过";
            }else if(messages.getLeave_status()==0){
                messages_status = "恶意评论";
            }else {
                messages_status = "";
            }
            list.add(new WebMessageDto(messages.getMessageid(),messages.get_shop().getShopname(),messages.get_user().getUserName(),messages.getContent(),messages.getLeave_time(),messages_status));
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        ExcelUtil.getInstance().exportObj2Excel(os,list,WebMessageDto.class);

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
     * 带条件的分页查询获取messages对象
     * @param page
     * @param size
     * @param sort
     * @param operation
     * @param key
     * @param value
     * @return
     */
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
