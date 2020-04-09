package apProject;

import java.util.ArrayList;

public interface Account {
	String userName;
	String firstName;
	String lastName;
	String role;
	String email;
	String phoneNumber;
	String password;
	ArrayList<String> allowanceCode;
	double credit;
	static ArrayList<Account> allAccounts;
	static ArrayList<String> requests;

	Account mainManager;
	ArrayList<Account> subManagers;

	ArrayList<Product> sellBuyList;

	String corporateName;

	Account(String userName,String password,String role);

	Account getAccountByUserName(String name);

	String getPassword();

	ArrayList<String> getAllowanceCodes();

	double getCredit();

	ArrayList<Account> getAllAccounts();

	ArrayList<String> getRequests();

	void setFirstName(String firstName);

	void setLastName(String lastName);

	void setRole(String role);

	void setEmail(String email);

	void setPhoneNumber(String phoneNumber);

	void setPassWord(String password);

	void removeAccount(Account account);

	ArrayList<Product> getCart(Account user);
}
