import java.util.ArrayList;
import java.util.List;


public class Customer {
	private int ID; 
	private String name; 
	private int balance;
	private List<Game> currentlyRented; 
	private List<Game> rentingHistory;

	public Customer(int ID, String name, int balance){
		this.ID = ID;
		this.name = name;
		this.balance = balance;
		currentlyRented = new ArrayList<Game>();
		rentingHistory = new ArrayList<Game>();
	}

	public boolean hasType(int ID){
		return ID == this.ID;
	}

	
	public String getName(){
		return this.name;
	}

	public int getBalance(){
		return this.balance;
	}

	public int deductBal(int price){
		return balance -= price;
	}

	public int topUp(int amount){
		return balance += amount;
	}

	public void addRentedGame(Game game){
		currentlyRented.add(new Game(game.getGameName(), game.getGameYear(), game.getGamePrice(), game.getGameGenre()));
	}

	public void removeRentedGame(Game game){
		currentlyRented.remove(game);
	}

	public void addRentingHistory(Game game){
		rentingHistory.add(new Game(game.getGameName(), game.getGameYear(), game.getGamePrice(), game.getGameGenre()));
	}

	public ArrayList<Game> currentlyRented(){
		ArrayList<Game> currentlyRentedMatch = new ArrayList<>(currentlyRented);
		return currentlyRentedMatch;
	} 

	public ArrayList<Game> rentingHistory(){
		ArrayList<Game> rentingHistoryMatch = new ArrayList<>(rentingHistory);
		return rentingHistoryMatch;
	} 

	@Override
	public String toString(){
		return ID + "\t" + name + "\t$ " + balance;
	}
}