package sg.edu.nus.iss.workshop24.model;

public class LineItem {
    
    private String item;
    // private String product;
    private Integer quantity;

    public LineItem() {

    }

    public LineItem(String item, Integer quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public String getItem() {
        return item;
    }
    public void setItem(String item) {
        this.item = item;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    @Override
    public String toString() {
        return "LineItem [item=" + item + ", quantity=" + quantity + "]";
    }

    public void add(Integer quantity) {
        this.quantity += quantity;
    }

    public void add() {
        this.quantity++;
    }

}
