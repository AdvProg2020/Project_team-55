package Model;

import java.util.HashMap;

public class Cart {
    private HashMap<Product, Integer> cartItems = new HashMap<>();

    public Cart(Buyer buyer) {
        buyer.setCart(this);
    }

    public HashMap<Product, Integer> getCartItems() {
        return cartItems;
    }

    public float calculatePrice() {
        Off.updateOffs();
        float sum = 0;
        if (!cartItems.isEmpty()) {
            for (Product product : cartItems.keySet()) {
                if (product.getAssignedOff() == null) {
                    sum += product.getPrice() * cartItems.get(product);
                } else {
                    sum += product.getPriceAfterOff() * cartItems.get(product);
                }
            }
            return sum;
        }
        return 0;
    }
}
