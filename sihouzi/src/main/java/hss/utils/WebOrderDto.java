package hss.utils;

/**
 * Created by ClownMonkey on 2017/1/24.
 */
public class WebOrderDto  {

    private Integer orderid;


    private String ordertime;


    private String user;


    private String shop;


    private Integer moneySum;


    private Integer amount;


    private String paystate;

    @ExcelResources(title="订单id",order=1)
    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    @ExcelResources(title="订单下单时间",order=2)
    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    @ExcelResources(title="下单的用户",order=3)
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @ExcelResources(title="对应的商品",order=4)
    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    @ExcelResources(title="订单的金额",order=5)
    public Integer getMoneySum() {
        return moneySum;
    }

    public void setMoneySum(Integer moneySum) {
        this.moneySum = moneySum;
    }

    @ExcelResources(title="商品的数量",order=6)
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @ExcelResources(title="订单的状态",order=7)
    public String getPaystate() {
        return paystate;
    }

    public void setPaystate(String paystate) {
        this.paystate = paystate;
    }

    @Override
    public String toString() {
        return "WebOrderDto{" +
                "orderid=" + orderid +
                ", ordertime='" + ordertime + '\'' +
                ", user='" + user + '\'' +
                ", shop='" + shop + '\'' +
                ", moneySum='" + moneySum + '\'' +
                ", amount='" + amount + '\'' +
                ", paystate='" + paystate + '\'' +
                '}';
    }

    public WebOrderDto(Integer orderid, String ordertime, String user, String shop, Integer moneySum, Integer amount, String paystate) {
        this.orderid = orderid;
        this.ordertime = ordertime;
        this.user = user;
        this.shop = shop;
        this.moneySum = moneySum;
        this.amount = amount;
        this.paystate = paystate;
    }
}
