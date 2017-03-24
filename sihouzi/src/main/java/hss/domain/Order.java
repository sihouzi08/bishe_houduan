package hss.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ClownMonkey on 2017/1/12.
 * order实体类映射数据库
 */
@Entity
@Table(name = "orderinfo")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer orderid;

    @Column(name = "ordertime")
    private String ordertime;

    @Column(name = "paystate")
    private Integer paystate;

    @Column(name = "moneysum")
    private Integer moneySum;

    @Column(name = "amount")
    private Integer amount;


    @ManyToOne
    @JoinColumn(name = "shopid")
    @JsonIgnore
    private Shop shop;

    public Shop get_shop() {
        return shop;
    }

    public void set_shop(Shop _shop) {
        this._shop = _shop;
    }

    @Transient
    private Shop _shop;

    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnore
    private User user;

    @Transient
    private User _user;

//    @JsonProperty("user_id")
    @Transient
    private Integer userid;

    public Integer getUserid() {
        if(user == null){
            return userid;
        } else {
            return user.getUserid();
        }
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

//    @JsonProperty("shop_id")
    @Transient
    private Integer shopid;//外键所属模块

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public Integer getPaystate() {
        return paystate;
    }

    public User get_user() {
        return user;
    }

    public void set_user(User _user) {
        this._user = _user;
    }

    public void setPaystate(Integer paystate) {
        this.paystate = paystate;
    }

    public Integer getMoneySum() {
        return moneySum;
    }

    public void setMoneySum(int moneySum) {
        this.moneySum = moneySum;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getShopid() {
        if(shop == null){
            return shopid;
        } else {
            return shop.getShopid();
        }

    }

    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }
}
