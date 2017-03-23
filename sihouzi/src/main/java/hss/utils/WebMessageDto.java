package hss.utils;

/**
 * Created by ClownMonkey on 2017/1/24.
 */
public class WebMessageDto {

    private Integer messageid;


    private String shop;


    private String user;


    private String content;


    private String Leave_time;


    private String Leave_status;

    @ExcelResources(title="留言的id",order=1)
    public Integer getMessageid() {
        return messageid;
    }

    public void setMessageid(Integer messageid) {
        this.messageid = messageid;
    }

    @ExcelResources(title="所留言的商品",order=2)
    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    @ExcelResources(title="用户的名字",order=3)
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @ExcelResources(title="留言的内容",order=4)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ExcelResources(title="留言的时间",order=5)
    public String getLeave_time() {
        return Leave_time;
    }

    public void setLeave_time(String leave_time) {
        Leave_time = leave_time;
    }

    @ExcelResources(title="留言的状态",order=5)
    public String getLeave_status() {
        return Leave_status;
    }

    public void setLeave_status(String leave_status) {
        Leave_status = leave_status;
    }


    @Override
    public String toString() {
        return "WebMessageDto{" +
                "messageid=" + messageid +
                ", shop='" + shop + '\'' +
                ", user='" + user + '\'' +
                ", content='" + content + '\'' +
                ", Leave_time='" + Leave_time + '\'' +
                ", Leave_status='" + Leave_status + '\'' +
                '}';
    }

    public WebMessageDto(Integer messageid, String shop, String user, String content, String leave_time, String leave_status) {
        this.messageid = messageid;
        this.shop = shop;
        this.user = user;
        this.content = content;
        Leave_time = leave_time;
        Leave_status = leave_status;
    }
}
