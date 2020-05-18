package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public abstract class Request {
    public static ArrayList<Request> allRequests = new ArrayList<Request>();
    protected String id;
    protected Seller sender;
    protected LocalDateTime dateTime;

    public static ArrayList<Request> getAllRequests() {
        return allRequests;
    }

    public static Request getRequestsById(String id) {
        for (Request request : allRequests) {
            if (request.id.equals(id)) {
                return request;
            }
        }
        return null;
    }

    public void showDetails() {

    }


    public abstract void acceptRequest();

    public abstract String showRequestDetails();

    public abstract void declineRequest();

    public String getDateTime(){
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }


}