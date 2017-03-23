package hss.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Foreveross on 2016/11/20.
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
    private String Leave_status;

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

    public String getLeave_status() {
        return Leave_status;
    }

    public void setLeave_status(String leave_status) {
        Leave_status = leave_status;
    }
}
