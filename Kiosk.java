import java.util.ArrayList;
import java.util.List;

public class Kiosk {

	private Catalogue catalogue; 
	private List<Customer> customers;

	public Kiosk(){
		this.catalogue = new Catalogue(this);
		this.customers = new ArrayList<Customer>();
		customers.add(new Customer(101, "Jaime", 10));
		customers.add(new Customer(102, "Luke", 10));
		customers.add(new Customer(103, "William", 1));
	}

	
	public static void main(String[] args){
		new Kiosk().use();
	}
//------------ KIOSK MENU ------------//
    private char readChoice() {
        System.out.print("Welcome to the Game Kiosk! Please make a selection from the menu:"
		+ "\n" + "1. Explore the catalogue."
		+ "\n" + "2. View your customer record."
		+ "\n" + "3. Show you favourite games."
		+ "\n" + "4. Top up account."
		+ "\n" + "5. Enter Admin Mode."
		+ "\n" + "X. Exit the system."
		+ "\n" + "Enter a choice: ");
        return In.nextChar();
    }

    public void use() {
        char choice;
        while ((choice = readChoice()) != 'X'){
            switch (choice){
                case '1': catalogue.catalogueMenu(); break;
                case '2': customerRecord(); break;
                case '3': catalogue.showFavourites(); break;
                case '4': topUpAccount(); break;
                case '5': adminUse(); break;
                default: help(); break;

            }
        }
        System.out.println("Thank you for using the Game Kiosk, do visit us again.");
    }
//------------ KIOSK MENU ------------//

//------------ ADMIN MENU ------------//
	private void adminUse() {
		char choice;
		while ((choice = readAdminChoice()) != 'R'){
			switch (choice){
				case '1': listCustomers(); break;
				case '2': addCustomer(); break;
				case '3': removeCustomer(); break;
				case '4': catalogue.listGames(); break;
				case '5': catalogue.addGame(); break;
				case '6': catalogue.removeGame(); break;
				case 'R': use(); break;
				default: help(); break;
			}
		}
	}

	private char readAdminChoice() {
		System.out.print("Welcome to the administration menu:"
		+ "\n" + "1. List all customers."
		+ "\n" + "2. Add a customer."
		+ "\n" + "3. Remove a customer."
		+ "\n" + "4. List all games."
		+ "\n" + "5. Add a game to the catalogue."
		+ "\n" + "6. Remove a game from the catalogue."
		+ "\n" + "R. Return to the previous menu."
		+ "\n" + "Enter a choice: ");
		return In.nextChar();
    }
//------------ ADMIN MENU ------------//

//------------ CUSTOMERS ------------//
	public Customer customer(int ID){
		for (Customer customer : customers){
			if (customer.hasType(ID)){
				return customer;
			}
		}
		return null;
	}

//////////// ADD A CUSTOMER ////////////

	private int newCustId(){
		System.out.print("Enter a new ID: ");
		return In.nextInt();
	}

	private String newCustName(){
		System.out.print("Enter the customer's name: ");
		return In.nextLine();
	}

	private int newCustBal(){
		System.out.print("Enter the customer's initial balance: ");
		return In.nextInt();
	}

	private int anotherId(){
		System.out.print("That customer already exists, please enter a new ID: ");
		return In.nextInt();
	}

	private void addCustomer(){
		System.out.println("\n" + "Adding a new customer.");
		int newId = newCustId();
		Customer customer = customer(newId);
		boolean customerExists = false;

		if (customer == null){
			customerExists = true;
			String newName = newCustName();
			int newBal = newCustBal();
			customers.add(new Customer(newId, newName, newBal));
			System.out.println("Customer added." + "\n");
		} 

		if (customer != null){
			while (customerExists == false){

				int otherId = anotherId();

				if (customer(otherId) == null){
					String otherName = newCustName();
					int otherBal = newCustBal();
					customers.add(new Customer(otherId, otherName, otherBal));
					customerExists = true;
					break;
				}
			}
		System.out.println("Customer added." + "\n");
		} 
	}

//////////// REMOVE A CUSTOMER ////////////

	private int removeId(){
		System.out.print("Enter a customer ID: ");
		return In.nextInt();
	}

	private void removeCustomer(){
		System.out.println("\n" + "Removing a customer.");
		int idToRemove = removeId();
		Customer customer = customer(idToRemove);

		if (customer != null){
			customers.remove(customer);
			System.out.println("Customer removed." + "\n");
		} else {
			System.out.println("That customer does not exist." + "\n");
		}
	}

//////////// LIST ALL CUSTOMERS ////////////

	private void listCustomers(){
		System.out.println("\n" + "The Kiosk has the following customers:");
		customers.forEach(System.out::println);
		System.out.println("");
	}

//////////// CUSTOMER RECORD ////////////

	private int searchId(){
		System.out.print("\n" + "Enter a customer ID: ");
		return In.nextInt();
	}

	private void customerRecord(){

		int idToSearch = searchId();
		Customer customer = customer(idToSearch);
			if (customer == null){
				System.out.println("That customer does not exist." + "\n");
			} else {
				System.out.println("ID: " + idToSearch);
				System.out.println("Name: " + customer.getName());
				System.out.println("Balance: $" + customer.getBalance());
				System.out.println("Games currently rented by " + customer.getName() + ":");
				customer.currentlyRented().forEach(System.out::println);
				System.out.println(customer.getName() + "'s renting history:");
				customer.rentingHistory().forEach(System.out::println);
				System.out.println("");
			}
	}

//------------ CUSTOMERS ------------//

//////////// TOP UP ACCOUNT ////////////

	private int topUpAmt(){
		System.out.print("Enter the top-up amount:");
		return In.nextInt();
	}

	private void topUpAccount(){
		int validId = searchId();
		Customer customer = customer(validId);

		if (customer != null){
			int amtTopUp = topUpAmt();
			int originalAmt = customer.getBalance();
			customer.topUp(amtTopUp);
			System.out.println("\n" + "Transaction complete.");
			System.out.println(customer.getName() + "'s balance was: $" + originalAmt);
			System.out.println(customer.getName() + "'s current balance is: $" + customer.getBalance() + "\n");
		}
	}

	private void help(){
		System.out.println("Please enter a number between 1 and 5, or press X to exit.");
	}
}