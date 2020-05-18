package Controller;

import Model.Category;
import Model.Off;
import Model.Product;
import Model.User;
import View.JustUniqueProduct;
import com.sun.xml.internal.ws.api.client.WSPortInfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class OffsController {
    private static OffsController controller;
    private JustUniqueProduct uniqueProduct=JustUniqueProduct.getInstance();
    private  HashMap<String, ArrayList<String>> currentFilters = new HashMap<>();
    private Category category=null;
    private float minPrice=0,maxPrice=0;
    private String sortBy="views";
    private ArrayList<Product> productsWithOff = new ArrayList<>();


    public void showOffs() {
        productsWithOff.clear();
        Off.updateOffs();
        for (Off off : Off.getOffArray()) {
            if (off.isActive()) {
                    productsWithOff.addAll(off.getProductsArray());
            }
        }
        applyFilters();
        applySort();
        showProducts();
    }

    public void showProducts() {
        for (Product product:productsWithOff) {
            System.out.println('\t' + product.getSpecialAttributes().get(0));
            System.out.println("\toriginal price: " + product.getPrice());
            System.out.println("\tprice after off: " +
                    product.getPriceAfterOff());
        }
    }

    public void goToProduct(User user, String id) {
        Off.updateOffs();
        if (Product.getProductById(id) != null) {
            if (Product.getProductById(id).getAssignedOff() != null) {
                uniqueProduct.enterUniqueProduct(user, Product.getProductById(id));
            } else {
                System.out.println("no off contains this product");
            }
        } else {
            System.out.println("this product doesn't exist");
        }
    }

    public void showAvailableFilters() {
        if (category == null) {
            for (Category category : Category.getAllCategories()) {
                if (category.getParentCategory() == null) {
                    System.out.println(category.getName() + ":");
                    for (Category subCategory : category.getSubCategories()) {
                        System.out.println('\t' + subCategory.getName());
                    }
                }
            }
        } else {
            for (String specialAttribute : category.getSpecialAttributes()) {
                System.out.println(specialAttribute);
            }
        }
    }

    public void addFilter(String field,String value) {
        if (category == null) {
            if (field.equalsIgnoreCase("category")){
                if (Category.categoryWithNameExists(value)){
                    category=Category.getCategoryByName(value);
                    System.out.println("filter applied successFully");
                }else {
                    System.out.println("category with this name doesn't exist.");
                    return;
                }
            }else {
                System.out.println("this filter is not available. you should first choose a category");
                return;
            }
        } else {
            if (field.equalsIgnoreCase("category")){
                System.out.println("you have already chosen a category");
                return;
            }else {
                if(field.equalsIgnoreCase("price")){
                    try {
                        String[] priceLimit = value.split("-");
                        if (Float.parseFloat(priceLimit[1])<Float.parseFloat(priceLimit[0])){
                            System.out.println("maximum price can't be lower than minimum price.");
                            return;
                        }
                        minPrice = Float.parseFloat(priceLimit[0]);
                        maxPrice = Float.parseFloat(priceLimit[1]);
                    }catch (NumberFormatException e){
                        System.out.println("invalid format");
                        return;
                    }
                }else {
                    if (category.getSpecialAttributes().contains(field)){
                    if (!currentFilters.containsKey(field)) {
                        currentFilters.put(field, new ArrayList<String>());
                    }
                    currentFilters.get(field).add(value);
                }else {
                        System.out.println("such attribute doesn't exist.");
                        return;
                    }
                }
            }
        }
        showOffs();
    }

    public void applyFilters(){
        if (category==null){
            return;
        }
        productsWithOff.removeIf(product -> product.getCategory()!=category);
        if (maxPrice!=0){
            productsWithOff.removeIf(product -> (product.getPriceAfterOff()>=minPrice)&&(product.getPriceAfterOff()<=maxPrice));
        }
        for (String attribute:currentFilters.keySet()){
            for (String value:currentFilters.get(attribute)){
                productsWithOff.removeIf(product -> !product.getSpecialAttributes().get(attribute).equals(value));
            }
        }

    }


    public void showCurrentFilters() {
        try {
            System.out.println("category "+category.getName());
            if (maxPrice!=0)
                System.out.println("price from "+minPrice+" to "+maxPrice);
            for (String filter : currentFilters.keySet()) {
                System.out.println(filter + ": ");
                for (String option : currentFilters.get(filter)) {
                    System.out.println('\t' + option);
                }
            }
        }catch (NullPointerException e){
            System.out.println("no filters are chosen.");
        }

    }

    public void removeFilter(String attribute) {
        if (attribute.equalsIgnoreCase("price")){
            maxPrice=minPrice=0;
            System.out.println("price filter removed successfully");
        }else {
            String[] splitAttribute=attribute.split(":");
            if (currentFilters.containsKey(splitAttribute[0])&&currentFilters.get(splitAttribute[0]).contains(splitAttribute[1])){
                currentFilters.get(splitAttribute[0]).remove(splitAttribute[1]);
                System.out.println("filter removed successfully");
            }else {
                System.out.println("such filter isn't applied");
                return;
            }
        }
        showOffs();
    }

    public void setSortBy(String sortElement){
        if (sortElement.equalsIgnoreCase("views|average score|creation date")){
        sortBy=sortElement;
        }else {
            System.out.println("invalid sort element");
            return;
        }
        showOffs();
    }
     public void disableSort(){
        sortBy="view";
        showOffs();
     }

    public String getSortBy() {
        return sortBy;
    }

    public void applySort(){
        if (sortBy.equals("views")){
            productsWithOff.sort(Comparator.comparing(Product::getViews));
        }else if (sortBy.equals("average score")){
            productsWithOff.sort(Comparator.comparing(Product::getAverageScore));
        }else if (sortBy.equals("creation date")){
            productsWithOff.sort(Comparator.comparing(Product::getCreationDate));
        }
     }

     public static OffsController getInstance(){
        if (controller==null)return new OffsController();
        return controller;
     }

}
