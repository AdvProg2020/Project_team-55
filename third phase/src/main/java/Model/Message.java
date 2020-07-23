package Model;

public class Message {
    private String messageText;
    private User sender;
    private User receiver;

    public Message(String messageText,User sender,User receiver){
        this.messageText=messageText;
        this.receiver=receiver;
        this.sender=sender;
    }
}
