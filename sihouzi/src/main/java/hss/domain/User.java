package hss.domain;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ClownMonkey on 2016/11/15.
 * 实体类映射数据库userinfo
 * 对该类属性的操作即是对数据库的字段操作
 */
@Entity
@Table(name = "userinfo")
public class User implements Serializable {

    private static final long serialVersionUID = -1549643581847130116L;



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userid;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "userstatus")
    private int userstatus;

    public int getUserstatus() {
        return userstatus;
    }

    public void setUserstatus(int userstatus) {
        this.userstatus = userstatus;
    }

    @Column(name = "email")
    private String email;



    @Column(name = "court")
    private String court;

    @Column(name = "school")
    private String school;



    @Column(name = "professional")
    private String professional;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "phone")
    private String phone;


    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getUserName() {
        return userName;
    }
    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
    }

//
//
}