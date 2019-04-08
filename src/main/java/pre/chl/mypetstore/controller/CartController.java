package pre.chl.mypetstore.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pre.chl.mypetstore.domain.Account;
import pre.chl.mypetstore.domain.Cart;
import pre.chl.mypetstore.domain.CartItem;
import pre.chl.mypetstore.domain.Item;
import pre.chl.mypetstore.service.CatalogService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;


@Controller
public class CartController {
    private Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    private CatalogService catalogService;
    @GetMapping("/cart/cart")
        public String addToCart(@RequestParam("workingItemId")String workingItemId, HttpSession session){
        if(workingItemId != null) {

            Cart cart = (Cart)session.getAttribute("cart");

            if(cart == null) {
                cart = new Cart();
            }
            if(cart.containsItemId(workingItemId)){
                cart.incrementQuantityByItemId(workingItemId);
            }
            else {

                boolean isInStock = catalogService.isItemInStock(workingItemId);
                Item item = catalogService.getItem(workingItemId);
                cart.addItem(item,isInStock);
            }

            session.setAttribute("cart",cart);
            if((String)session.getAttribute("flag") == "yes") {
                Account account =(Account)session.getAttribute("account");
                logger.info("用户"+account.getUsername()+"添加了宠物"+workingItemId+"到购物车");
            }
        }

        return  "cart/cart";
    }
    @GetMapping("/mycart")
    public String viewMycart(HttpSession session){
        Cart cart=(Cart)session.getAttribute("cart");
        if(cart==null) {
            session.setAttribute("cart", new Cart());
        }
        return "cart/cart";
    }

    @GetMapping("/cart/removeitem")
    public String removeitem(@RequestParam("removeitemId")String removeitemId,HttpSession session,Model model) {
        Cart cart = (Cart) session.getAttribute("cart");
        Item item = cart.removeItemById(removeitemId);
        if (item == null) {
            model.addAttribute("errormsg", "Attempted to remove null CartItem from Cart.");
            return "common/error";
        } else {
            return  "cart/cart";
        }
    }
    @PostMapping("/cart/updateCart")
    public  String updateCart(HttpServletRequest request, HttpSession session, Model model){

        Cart cart = (Cart) session.getAttribute("cart");
        Iterator<CartItem> cartItems = cart.getAllCartItems();
        while (cartItems.hasNext()) {
            CartItem cartItem = cartItems.next();
            String itemId = cartItem.getItem().getItemId();
            try {
                int quantity = Integer.parseInt(request.getParameter(itemId));
                cart.setQuantityByItemId(itemId, quantity);
                if (quantity < 1) {
                    cartItems.remove();
                }

            } catch (Exception e) {
                model.addAttribute("errormsg", "The Quantities of Item must be Integer!");
                return "common/error";
            }
        }
        session.setAttribute("cart",cart);
        return "cart/cart";
    }

}
