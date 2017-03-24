package hss.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ClownMonkey on 2016/11/20.
 * messages实体类映射数据库表messages
 */
@Entity
@Table(name = "messages")
public class Messages implements Serializable {
    private static final long serialVersionUID = -1549643581827030116L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer messageid;

    @Column(name = "content")
    private String content;

    @Column(name = "receivename")
    private String receivename;

    @Column(name = "username")
    private String userName;

    @Column(name = "Leave_time")
    private String Leave_time;

    @Column(name = "Leave_status")
    private Integer Leave_status;

    @ManyToOne
    @JoinColumn(name = "shopid")
    @JsonIgnore
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnore
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Transient
    private User _user;

    @Transient
    private Shop _shop;

//    @JsonProperty("user_id")
    @Transient
    private Integer userid;

    public Integer getUserid() {
        if (user == null) {
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

    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }



    public Integer getShopid() {
        if ( shop == null) {
            return shopid;
        } else {
            return shop.getShopid();
        }
    }


    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public User get_user() {
        return user;
    }

    public void set_user(User _user) {
        this._user = _user;
    }

    public Shop get_shop() {
        return shop;
    }

    public void set_shop(Shop _shop) {
        this._shop = _shop;
    }

    public Integer getMessageid() {
        return messageid;
    }

    public void setMessageid(Integer messageid) {
        this.messageid = messageid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReceivename() {
        return receivename;
    }

    public void setReceivename(String receivename) {
        this.receivename = receivename;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLeave_time() {
        return Leave_time;
    }

    public void setLeave_time(String leave_time) {
        Leave_time = leave_time;
    }

    public Integer getLeave_status() {
        return Leave_status;
    }

    public void setLeave_status(Integer leave_status) {
        Leave_status = leave_status;
    }
}
