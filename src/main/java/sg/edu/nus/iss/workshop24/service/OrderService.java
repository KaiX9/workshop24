package sg.edu.nus.iss.workshop24.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.workshop24.exception.OrderException;
import sg.edu.nus.iss.workshop24.model.FruitProducts;
import sg.edu.nus.iss.workshop24.model.PurchaseOrder;
import sg.edu.nus.iss.workshop24.repository.LineItemRepository;
import sg.edu.nus.iss.workshop24.repository.PurchaseOrderRepository;

@Service
public class OrderService {
    
    @Autowired
    private LineItemRepository itemRepo;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepo;

    @Transactional(rollbackFor=OrderException.class)
    public void createOrder(PurchaseOrder purchaseOrder) throws OrderException {
        // create order id
        Random rand = new Random();
        int ordId = rand.nextInt(10000000);

        String orderId = String.valueOf(ordId);

        purchaseOrder.setOrderId(ordId);
        FruitProducts.fruitProducts = itemRepo.getProducts();

        purchaseOrderRepo.insertPurchaseOrder(purchaseOrder);

        itemRepo.addLineItems(purchaseOrder.getLineItems(), orderId);

    }
}
