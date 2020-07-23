package Model;

public class CartItem {
    private Product item;
    private int quantity;

    public CartItem(Product item) {
        this.item = item;
        this.quantity = 1;
    }

    public void increaseItem() {
        this.quantity += 1;
    }

    public void decreaseItem() {
        if (this.quantity > 1) {
            this.quantity -= 1;
        }
    }

    public Product getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPricePerEach() {
        return item.getPrice();
    }

    public float getTotalPrice() {
        return item.getPrice() * quantity;
    }
}
