package sg.edu.nus.iss.workshop24.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.workshop24.exception.OrderException;
import sg.edu.nus.iss.workshop24.model.LineItem;
import sg.edu.nus.iss.workshop24.model.PurchaseOrder;
import sg.edu.nus.iss.workshop24.service.OrderService;

@Controller
@RequestMapping(path="/checkout")
public class CheckoutController {
    
    @Autowired
    private OrderService orderService;

    @PostMapping
    public String postCheckout(@RequestParam("name") String name
        , @RequestParam("ship_address") String ship_address
        , @RequestParam("notes") String notes
        , HttpSession s
        , Model m) throws OrderException {
        
        List<LineItem> lineItems = (List<LineItem>) s.getAttribute("cart");
        PurchaseOrder purchaseOrder = (PurchaseOrder) s.getAttribute("checkoutCart");

        purchaseOrder.setName(name);
        purchaseOrder.setShip_address(ship_address);
        purchaseOrder.setNotes(notes);
        if (purchaseOrder.getLineItems().size() > 5) {
            s.invalidate();
            throw new OrderException("Cannot order more than 5 items");
        }
        orderService.createOrder(purchaseOrder);
        System.out.println(purchaseOrder);
        
        s.invalidate();

        m.addAttribute("total", lineItems.size());
        return "checkout";
    }

    @ExceptionHandler(OrderException.class)
    public String handleOrderException(OrderException ex, Model m) {
        
        m.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
