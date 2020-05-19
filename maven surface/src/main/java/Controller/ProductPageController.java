package Controller;

import Model.Category;
import Model.Off;
import Model.Product;
import Model.User;
import View.JustUniqueProduct;
import View.ProductPageView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class ProductPageController {
    private HashMap<String, ArrayList<String>> currentFilters = new HashMap<>();
    private Category category;
    private float maxPrice = 0, minPrice = 0;
    private String sortBy;
    private ArrayList<Product> filteredProducts = new ArrayList<>();
    private JustUniqueProduct uniqueProduct=JustUniqueProduct.getInstance();

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

    public void showCategories(){
        for (Category category:Category.getAllCategories()){
            if (category.getParentCategory()==null){
                System.out.println(category.getName()+":");
                for (Category subs:category.getSubCategories()){
                    System.out.println('\t'+subs.getName());
                }
            }
        }
    }

    public void showProducts() {
        filteredProducts.addAll(Product.getAllProducts());
        Off.updateOffs();
        applyFilters();
        applySort();
        for (Product product : filteredProducts) {
            System.out.println(product.getProductId() + " " + product.getSpecialAttributes().get("name") +
                    " of brand " + product.getSpecialAttributes().get("brand"));
            System.out.println("price: " + product.getPrice());
            if (product.getAssignedOff() != null && product.getAssignedOff().isActive())
                System.out.println("price after off: " + product.getPriceAfterOff());
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
                System.out.println("price");
                System.out.println(specialAttribute);
            }
        }
    }

    public void addFilter(String field, String value) {
        if (category == null) {
            if (field.equalsIgnoreCase("category")) {
                if (Category.categoryWithNameExists(value)) {
                    category = Category.getCategoryByName(value);
                    System.out.println("filter applied successFully");
                } else {
                    System.out.println("category with this name doesn't exist.");
                    return;
                }
            } else {
                System.out.println("this filter is not available. you should first choose a category");
                return;
            }
        } else {
            if (field.equalsIgnoreCase("category")) {
                System.out.println("you have already chosen a category");
                return;
            } else {
                if (field.equalsIgnoreCase("price")) {
                    try {
                        String[] priceLimit = value.split("-");
                        if (Float.parseFloat(priceLimit[1]) < Float.parseFloat(priceLimit[0])) {
                            System.out.println("maximum price can't be lower than minimum price.");
                            return;
                        }
                        minPrice = Float.parseFloat(priceLimit[0]);
                        maxPrice = Float.parseFloat(priceLimit[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("invalid format");
                        return;
                    }
                } else {
                    if (category.getSpecialAttributes().contains(field)) {
                        if (!currentFilters.containsKey(field)) {
                            currentFilters.put(field, new ArrayList<String>());
                        }
                        currentFilters.get(field).add(value);
                    } else {
                        System.out.println("such attribute doesn't exist.");
                        return;
                    }
                }
            }
        }
    }

    public void applyFilters() {
        if (category == null) {
            return;
        }
        filteredProducts.removeIf(product -> product.getCategory() != category);
        if (maxPrice != 0) {
            filteredProducts.removeIf(product -> (product.getPrice() >= minPrice) && (product.getPrice() <= maxPrice));
        }
        for (String attribute : currentFilters.keySet()) {
            for (String value : currentFilters.get(attribute)) {
                filteredProducts.removeIf(product -> !product.getSpecialAttributes().get(attribute).equals(value));
            }
        }

    }


    public void showCurrentFilters() {
        try {
            System.out.println("category " + category.getName());
            if (maxPrice != 0)
                System.out.println("price from " + minPrice + " to " + maxPrice);
            for (String filter : currentFilters.keySet()) {
                System.out.println(filter + ": ");
                for (String option : currentFilters.get(filter)) {
                    System.out.println('\t' + option);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("no filters are chosen.");
        }

    }

    public void removeFilter(String attribute) {
        if (attribute.equalsIgnoreCase("price")) {
            maxPrice = minPrice = 0;
            System.out.println("price filter removed successfully");
        } else {
            String[] splitAttribute = attribute.split(":");
            if (currentFilters.containsKey(splitAttribute[0]) && currentFilters.get(splitAttribute[0]).contains(splitAttribute[1])) {
                currentFilters.get(splitAttribute[0]).remove(splitAttribute[1]);
                System.out.println("filter removed successfully");
            } else {
                System.out.println("such filter isn't applied");
                return;
            }
        }
    }

    public void setSortBy(String sortElement) {
        if (sortElement.equalsIgnoreCase("views|average score|creation date")) {
            sortBy = sortElement;
        } else {
            System.out.println("invalid sort element");
            return;
        }
    }

    public void disableSort() {
        sortBy = "view";
        showProducts();
    }

    public String getSortBy() {
        return sortBy;
    }


    public HashMap<String, ArrayList<String>> getCurrentFilters() {
        return currentFilters;
    }

    public void applySort() {
        if (sortBy.equals("views")) {
            filteredProducts.sort(Comparator.comparing(Product::getViews));
        } else if (sortBy.equals("average score")) {
            filteredProducts.sort(Comparator.comparing(Product::getAverageScore));
        } else if (sortBy.equals("creation date")) {
            filteredProducts.sort(Comparator.comparing(Product::getCreationDate));
        }
    }


}
