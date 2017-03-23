package hss.utils;

/**
 * Created by ClownMonkey on 2017/1/24.
 */
public class WebUserDto {



    private Integer userid;


    private String username;


    private String password;


    private String email;


    private String school;


    private String court;


    private String professional;


    private String userstatus;


    private String phone;


    @ExcelResources(title="用户id",order=1)
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    @ExcelResources(title="用户名字",order=2)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @ExcelResources(title="用户密码",order=3)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ExcelResources(title="用户邮箱",order=4)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ExcelResources(title="用户所在学校",order=5)
    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @ExcelResources(title="用户系别",order=6)
    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
    }

    @ExcelResources(title="用户专业",order=7)
    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    @ExcelResources(title="用户状态",order=8)
    public String getUserstatus() {
        return userstatus;
    }

    public void setUserstatus(String userstatus) {
        this.userstatus = userstatus;
    }

    @ExcelResources(title="用户电话号码",order=9)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "WebUserDto{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", school='" + school + '\'' +
                ", court='" + court + '\'' +
                ", professional='" + professional + '\'' +
                ", userstatus='" + userstatus + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public WebUserDto(Integer userid, String username, String password, String email, String school, String court, String professional, String userstatus, String phone) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.email = email;
        this.school = school;
        this.court = court;
        this.professional = professional;
        this.userstatus = userstatus;
        this.phone = phone;
    }
}
