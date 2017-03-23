//package hss.utils;
//
//import com.zslin.domain.Shop;
//import com.zslin.dto.WebShopDtoAll;
//import com.zslin.repository.ShopRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.FileOutputStream;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * Created by Foreveross on 2016/12/29.
// */
//
//
//@RestController
//@RequestMapping(value = "/shop")     // 通过这里配置使下面的映射都在/users下
//@SpringBootApplication
//public class Controll {
//
//    @Autowired
//    private ShopRepository shopRepository;
//
//
//    @RequestMapping(value = "/file", method = RequestMethod.GET)
//    public String test4() throws Exception {
//        List<Shop> list1 =shopRepository.findAll();
//        List<WebShopDtoAll> list = new ArrayList<WebShopDtoAll>();
//        list.add(new WebShopDtoAll(11,"权限系统", "com", "admin", "1221","权限系统","权限系统","权限系统","权限系统","权限系统"));
//
//        for(Shop shop :list1){
//            list.add(new WebShopDtoAll(shop.getShopid(),shop.getShopname(),shop.getDes(),shop.getUserName(),shop.getUserphne(),shop.getCategory(),shop.getPicture(),shop.getPrice(),shop.getShop_status(),shop.getPut_time()));
//        }
//
//        list.add(new WebShopDtoAll(33,"校园网", "zslin", "admin", "2112","校园网","校园网","校园网","校园网","校园网" ));
//        list.add(new WebShopDtoAll(33,"校园网", "zslin", "admin", "2112","校园网","校园网","校园网","校园网","校园网" ));
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("title", "Shop信息表");
//        map.put("total", list.size()+" 条");
//        map.put("date", getDate());
//
////        ExcelUtil.getInstance().exportObj2ExcelByTemplate(map, "shop-test.xls", new FileOutputStream("D:/temp/"+"shop_"+Date2FileName("yyyyMMdd_HHmmss" , ".xls")),
////                list, WebShopDtoAll.class, true);
//
//        ExcelUtil.getInstance().exportObj2Excel( new FileOutputStream("D:/temp/"+"shop_"+Date2FileName("yyyyMMdd_HHmmss" , ".xls")), list,WebShopDtoAll.class);
//
//        return "success";
//
//    }
//
//    private String getDate() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
//        return sdf.format(new Date());
//    }
//
//
//    public static String Date2FileName(String nameFormat, String fileType) {
//        Date date = new Date(System.currentTimeMillis());
//        SimpleDateFormat dateFormat = new SimpleDateFormat(nameFormat);
//        String fileName = dateFormat.format(date) + fileType;
//        return fileName;
//    }
//
//
//
//}
