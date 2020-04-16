package Model;

public class Comments {
    private User userComments;
    private Product products ;
    private String messages;
    private Boolean DidTheCommenterBuyTheProduct;
}
enum comments{WaitingForApproval, accepted , Unconfirmed}