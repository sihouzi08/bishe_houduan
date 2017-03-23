package hss.utils;

/**
 * Created by Foreveross on 2016/12/28.
 */
public class WebShopDtoAll {


    private Integer shopid;


    private String shopname;


    private String des;


    private String userName;


    private String userphne;


    private String category;


    private String picture;


    private Integer price;


    private String shop_status;


    private String put_time;
    @ExcelResources(title="商品id",order=1)
    public Integer getShopid() {
        return shopid;
    }
    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }

    @ExcelResources(title="商品名字",order=2)
    public String getShopname() {
        return shopname;
    }
    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    @ExcelResources(title="商品描述",order=3)
    public String getDes() {
        return des;
    }
    public void setDes(String des) {
        this.des = des;
    }

    @ExcelResources(title="用户名",order=4)
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @ExcelResources(title="用户手机",order=5)
    public String getUserphne() {
        return userphne;
    }
    public void setUserphne(String userphne) {
        this.userphne = userphne;
    }

    @ExcelResources(title="学校",order=6)
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    @ExcelResources(title="商品图片",order=7)
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }

    @ExcelResources(title="商品价格",order=8)
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }

    @ExcelResources(title="商品状态",order=9)
    public String getShop_status() {
        return shop_status;
    }
    public void setShop_status(String shop_status) {
        this.shop_status = shop_status;
    }

    @ExcelResources(title="商品时间",order=10)
    public String getPut_time() {
        return put_time;
    }
    public void setPut_time(String put_time) {
        this.put_time = put_time;
    }

    @Override
    public String toString() {
        return "WebShopDtoAll{" +
                "shopid=" + shopid +
                ", shopname='" + shopname + '\'' +
                ", des='" + des + '\'' +
                ", userName='" + userName + '\'' +
                ", userphne='" + userphne + '\'' +
                ", category='" + category + '\'' +
                ", picture='" + picture + '\'' +
                ", price='" + price + '\'' +
                ", shop_status='" + shop_status + '\'' +
                ", put_time='" + put_time + '\'' +
                '}';
    }

    public WebShopDtoAll(Integer shopid, String shopname, String des, String userName, String userphne, String category, String picture, Integer price, String shop_status, String put_time) {
        this.shopid = shopid;
        this.shopname = shopname;
        this.des = des;
        this.userName = userName;
        this.userphne = userphne;
        this.category = category;
        this.picture = picture;
        this.price = price;
        this.shop_status = shop_status;
        this.put_time = put_time;
    }
}
