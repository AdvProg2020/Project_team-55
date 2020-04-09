package apProject;

import java.util.ArrayList;

public interface Product {
	String Id;
	String name;
	Category category;
	Account seller;
	String status;
	String description;
	double price;
	String brand;
	double averageScore;
	ArrayList<Comment> comments;
	static ArrayList<Product> allProducts;

	Product getProductById(String id);
}
