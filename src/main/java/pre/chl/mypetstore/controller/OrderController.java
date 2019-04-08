package pre.chl.mypetstore.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import pre.chl.mypetstore.domain.Account;
import pre.chl.mypetstore.domain.Cart;
import pre.chl.mypetstore.domain.Order;
import pre.chl.mypetstore.service.AccountService;
import pre.chl.mypetstore.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes({"order","cart"})
public class OrderController {
    private Log logger = LogFactory.getLog(this.getClass());
    Order order = null;
    @Autowired
    OrderService orderService;
    @Autowired
    AccountService accountService;
    //新的订单页面
    @GetMapping("/order/neworderform")
    public  String newOrderForm( HttpSession session, Model model) {
        Cart cart = (Cart)session.getAttribute("cart");
        Account account = (Account)session.getAttribute("account");
        if(account==null)
        {
            return  "account/signon";
        }
        else if((cart!=null)){
            order = new Order();
            order.initOrder(accountService.getAccount(account.getUsername()),cart);
            session.setAttribute("order",order);
            model.addAttribute("order",order);

        }
        else {
            model.addAttribute("errormsg","An order could not be created because a cart could not be found.");
        }
        return "order/neworderform";
    }
    //neworderForm界面的continue
    @PostMapping("/continue1")
    public  String confirmOrder( Order order,@RequestParam(value = "shippingAddressRequired",required = false)String shippingAddressRequired, HttpServletRequest request, HttpSession session,ModelMap model){
        order = (Order)model.get("order");
        Date date = new Date();
        order.setOrderDate(new java.sql.Date(date.getTime()));
        session.setAttribute("order",order);
        if(shippingAddressRequired == null)
        {
            orderService.insertOrder(order);
            return "order/confirmorder";
        }
        else
        {
           return "order/shippingform";
        }

        //orderService.insertOrder(order);
    }
    //跳转到shippingFrom界面后的continue
    @PostMapping("/continue2")
    public  String shippingForm(Order order,HttpSession session,ModelMap model){
        order = (Order)model.get("order");
        Date date = new Date();
        order.setOrderDate(new java.sql.Timestamp(date.getTime()));
        session.setAttribute("order",order);
        orderService.insertOrder(order);

        return "order/confirmorder";
    }
    //最终确认的订单
    @PostMapping("/order/vieworder")
    public String viewOrder(HttpSession session,Model model){
        // 重置购物车
        Cart cart = new Cart();
        model.addAttribute("cart",cart);
        session.setAttribute("cart",cart);
        if((String)session.getAttribute("flag") == "yes") {
            Account account =(Account)session.getAttribute("account");
            logger.info("用户"+account.getUsername()+"生成了订单到购物车,订单号："+order.getOrderId()+"订单日期"+order.getOrderDate());
        }
        return "order/vieworder";
    }
    //用户订单列表
    @GetMapping("/order/listorders")
    public String listOrder(HttpSession session,Model model){
        Account account =(Account)session.getAttribute("account");
        List<Order> orderList = orderService.getOrdersByUsername(account.getUsername());
        model.addAttribute("orderList",orderList);
        return "order/listorders";

    }
    //查看某一具体订单
    @GetMapping("/order/vieworder")
    public String viewOrderByOrderId(@RequestParam("orderId")int orderId,Model model){
        Order order=orderService.getOrder(orderId);
        model.addAttribute("order",order);
        return "order/vieworderbyid";
    }
}
