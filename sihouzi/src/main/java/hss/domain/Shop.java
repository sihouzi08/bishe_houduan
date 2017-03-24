package hss.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.security.Timestamp;

/**
 * Created by ClownMonkey on 2016/11/20.
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

    @ManyToOne
    @JoinColumn(name = "categoryid")
    @JsonIgnore
    private Category category;

    @Transient
    private Category _category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category get_category() {
        return category;
    }

    public void set_category(Category _category) {
        this._category = _category;
    }

    public Integer getCategory_id() {
        if(category == null){
            return category_id;
        } else {
            return category.getId();
        }
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

//    @JsonProperty("category_id")
    @Transient
    private Integer category_id;

    @Column(name = "picture")
    private String picture;

    @Column(name = "price")
    private Integer price;

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



    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
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

//    public Shop(String shopname, String des, String userName, String userphne, String category, String picture, Integer price, String shop_status, String put_time) {
//        this.shopname = shopname;
//        this.des = des;
//        this.userName = userName;
//        this.userphne = userphne;
//        this.category = category;
//        this.picture = picture;
//        this.price = price;
//        this.shop_status = shop_status;
//        this.put_time = put_time;
//    }


}
