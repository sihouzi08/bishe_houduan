package hss.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.security.Timestamp;

/**
 * Created by Foreveross on 2016/11/20.
 */
@Entity
@Table(name = "shopinfo")
public class Shop implements Serializable {

    private static final long serialVersionUID = -1549643581827830116L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer shopid;

    @Column(name = "shopname")
    private String shopname;

    @Column(name = "des")
    private String des;

    @Column(name = "username")
    private String userName;

    @Column(name = "userphne")
    private String userphne;

    @Column(name = "category")
    private String category;

    @Column(name = "picture")
    private String picture;

    @Column(name = "price")
    private String price;

    @Column(name = "shop_status")
    private String shop_status;

    @Column(name = "put_time")
    private String put_time;

    public Integer getShopid() {
        return shopid;
    }

    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserphne() {
        return userphne;
    }

    public void setUserphne(String userphne) {
        this.userphne = userphne;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShop_status() {
        return shop_status;
    }

    public void setShop_status(String shop_status) {
        this.shop_status = shop_status;
    }

    public String getPut_time() {
        return put_time;
    }

    public void setPut_time(String put_time) {
        this.put_time = put_time;
    }

}
