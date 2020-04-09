package apProject;

public interface AccountMenuController {

	void registerAccount(String type, String name);

	void getCostumerInformation(Account costumer);

	void getManagerInformation(Account manager);

	void getSellerInformation(Account seller);

	void loginAccount(String userName);

	void showCostumerInformation(Account costumer);

	void showManagerInformation(Account manager);

	void showSellerInformation(Account seller);

	void editField(Account account, String field);

	// manager platform

	void showAccountsList();

	void sortAccountsByField(String field);

	void viewInformation(String userName);

	void changerole(String userName);

	void removeAccount(String userName);

	void createManagerProfile(Account manager);

	void showProdutsList();

	void sortProductsByField(String field);

	void removeProduct(String id);
}
