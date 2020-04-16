
package Model;

import java.util.ArrayList;

public class Category {
    private String name;
    private ArrayList subCategory;
    private ArrayList<Product> arrayProduct = new ArrayList<>();
    private ArrayList<Category> arrayCategory = new ArrayList<>();
    private ArrayList<String> specialAttributesForCategory = new ArrayList<>();
    Category parentCategory;

    public Category(String name, Category parentCategory) {
        this.name = name;
        this.arrayCategory = arrayCategory;
        this.parentCategory = parentCategory;
    }
}



