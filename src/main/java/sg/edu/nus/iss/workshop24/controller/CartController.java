package sg.edu.nus.iss.workshop24.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.workshop24.exception.OrderException;
import sg.edu.nus.iss.workshop24.model.LineItem;
import sg.edu.nus.iss.workshop24.model.PurchaseOrder;

@Controller
@RequestMapping(path="/order")
public class CartController {
    
    @PostMapping(consumes="application/x-www-form-urlencoded")
    public String postChart(Model m, HttpSession s, @ModelAttribute LineItem item) throws OrderException {
        
        List<LineItem> lineItems = (List<LineItem>) s.getAttribute("cart");

        if (null == lineItems) {
            lineItems = new ArrayList<LineItem>();
            s.setAttribute("cart", lineItems);
        }

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        List<LineItem> currCart = lineItems.stream()
                                .filter(i -> i.getItem().equals(item.getItem()))
                                .toList();
        if (currCart.isEmpty()) {
            lineItems.add(item);
        } else {
            currCart.get(0).add(item.getQuantity());
        }

        purchaseOrder.setLineItems(lineItems);
        System.out.println(lineItems);
        System.out.println(purchaseOrder);

        s.setAttribute("checkoutCart", purchaseOrder);
        m.addAttribute("lineItems", lineItems);
        return "cart_template";
    }
}
