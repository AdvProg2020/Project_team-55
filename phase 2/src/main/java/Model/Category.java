package Model;

import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

import java.util.ArrayList;

public class Category {
    private static final ArrayList<Category> allCategories = new ArrayList<>();
    Category parentCategory;
    private String name;
    private ArrayList<Category> subCategories;
    private ArrayList<Product> arrayProduct = new ArrayList<>();
    private ArrayList<String> specialAttributes = new ArrayList<>();

    public Category(String name, Category parentCategory, ArrayList specialAttributes) {
        this.name = name;
        this.parentCategory = parentCategory;
        this.specialAttributes=specialAttributes;
        if (parentCategory != null) {
            this.specialAttributes.addAll(0, parentCategory.getSpecialAttributes());
            parentCategory.subCategories.add(this);
        } else {
            subCategories=new ArrayList<>();
            this.specialAttributes.add(0, "name");
            this.specialAttributes.add(1, "brand");
        }
        allCategories.add(this);
    }

    public static ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public static Category getCategoryByName(String name) {
        for (Category category : allCategories) {
            if (category.name.toString().equalsIgnoreCase(name)) {
                return category;
            }
        }
        return null;
    }

    public static boolean categoryWithNameExists(String name) {
        return Category.getCategoryByName(name) != null;
    }

    public static void deleteCategory(Category category) {
        for (Product product : category.arrayProduct) {
            Product.getAllProducts().remove(product);
        }
        if (category.parentCategory != null) {
            category.parentCategory.subCategories.remove(category);
        }
        allCategories.remove(category);
    }

    public ArrayList<Product> getArrayProduct() {
        return arrayProduct;
    }

    public ArrayList<Category> getSubCategories() {
        return subCategories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialAttributes(ArrayList<String> specialAttributes){
        this.specialAttributes=specialAttributes;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public ArrayList<String> getSpecialAttributes() {
        return specialAttributes;
    }

    public void removeAttribute(String attribute) {
        specialAttributes.remove(attribute);
        for (Product product : arrayProduct) {
            product.getSpecialAttributes().remove(attribute);
        }
    }

    public void addAttribute(String attribute) {
        specialAttributes.add(attribute);
        for (Product product : arrayProduct) {
            product.getSpecialAttributes().put(attribute, "");
        }
    }

    @Override
    public String toString() {
        return getName();
    }
}



