package Model;

import java.util.ArrayList;

public class Cart {
    private ArrayList<CartItem> cartItems = new ArrayList<>();

    public Cart(Buyer buyer) {
        buyer.setCart(this);
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public int calculatePrice() {
        Off.updateOffs();
        int sum = 0;
        if (!cartItems.isEmpty()) {
            for (CartItem item : cartItems) {
                Product product = item.getItem();
                if (product.getAssignedOff() == null) {
                    sum += product.getPrice() * item.getQuantity();
                } else {
                    sum += product.getPriceAfterOff() * item.getQuantity();
                }
            }
            return sum;
        }
        return 0;
    }
}
