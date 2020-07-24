package Model;


public class Comment{
    enum comments {WaitingForApproval, accepted, Unconfirmed}
    private User commenter;
    private Product product;
    private String messages;
    private Boolean didTheCommenterBuyTheProduct;

    public Comment(User commenter, String messages, Product product){
        this.commenter=commenter;
        this.messages=messages;
        this.product=product;
        if (product.getListOfBuyers().contains(commenter))this.didTheCommenterBuyTheProduct=true;
        else this.didTheCommenterBuyTheProduct=false;
        product.getComments().add(this);
    }

    public String getCommenter() {
        return commenter.getUsername();
    }

    public String getMessages() {
        return messages;
    }
}