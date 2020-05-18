package Model;


import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;

public class Comments {
    enum comments {WaitingForApproval, accepted, Unconfirmed}
    private User commenter;
    private Product product;
    private String messages;
    private String title;
    private Boolean didTheCommenterBuyTheProduct;

    public Comments(User commenter,String messages,String title,Product product){
        this.commenter=commenter;
        this.messages=messages;
        this.title=title;
        this.product=product;
        if (product.getListOfBuyers().contains(commenter))this.didTheCommenterBuyTheProduct=true;
        else this.didTheCommenterBuyTheProduct=false;
        product.getComments().add(this);
    }

    public User getCommenter() {
        return commenter;
    }

    public String getTitle() {
        return title;
    }

    public String getMessages() {
        return messages;
    }
}