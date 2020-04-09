package apProject;

import java.util.Scanner;

public interface AccountMenuView {
	Scanner scanner = new Scanner(System.in);

	void logInRegister();

	void enterGuestPlatform();

	void enterCostumerPlatform(Account costumer);

	void enterCostumerInformationMenu(Account Manager);

	// void viewCart(Account costumer);

	void enterSellerPlatform(Account seller);

	void enterSellerInformationMenu(Account Manager);

	void enterManagerPlatform(Account manager);

	void enterManagerInformationMenu(Account Manager);

	void usersManagementMenu(Account Manager);

	void productManagementMenu(Account Manager);
}
