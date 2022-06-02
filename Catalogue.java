import java.util.ArrayList;
import java.util.List;

public class Catalogue {
	private Kiosk kiosk;
	private List<Game> gamesAvailable;
	private List<Game> gamesRented;
	private List<Genre> genres;

	public Catalogue(Kiosk kiosk){
		this.kiosk = kiosk;
        gamesAvailable = new ArrayList<Game>();
        gamesRented = new ArrayList<Game>();
        genres = new ArrayList<Genre>();

	 	genres.add(new Genre("Action Queue"));
		genres.add(new Genre("Role Playing"));
		genres.add(new Genre("Hand Management"));
		genres.add(new Genre("Modular Board"));
		genres.add(new Genre("Modular Board"));

		gamesAvailable.add(new Game("Robinson Crusoe", 2012, 3, new Genre("Action Queue")));
		gamesAvailable.add(new Game("Talisman", 2007, 4, new Genre("Role Playing")));
		gamesAvailable.add(new Game("Three Kingdoms Redux", 2014, 3, new Genre("Hand Management")));
		gamesAvailable.add(new Game("Dungeons & Dragons", 2010, 4, new Genre("Modular Board")));
		gamesAvailable.add(new Game("Elder Sign", 2011, 3, new Genre("Modular Board")));
	}

	public void catalogueMenu() {
        char choice;
        while ((choice = catalogueRead()) != 'R'){
            switch (choice){
                case '1': displayAllGames(); break;
                case '2': allAvailableGames(); break;
                case '3': displayGenre(); break;
                case '4': findByGenre(); break;
                case '5': findByYear(); break;
				case '6': rentGame(); break;
				case '7': returnRentedGame(); break;
                default: cataHelp(); break;

            }
        }
    }

	private char catalogueRead() {
        System.out.print("Welcome to the Catalogue! Please make a selection from the menu:"
		+ "\n" + "1. Display all games."
		+ "\n" + "2. Display all available games."
		+ "\n" + "3. Display all genres."
		+ "\n" + "4. Display games in a genre."
		+ "\n" + "5. Display all games by year."
		+ "\n" + "6. Rent a game."
		+ "\n" + "7. Return a game."
		+ "\n" + "R. Return to previous menu."
		+ "\n" + "Enter a choice: ");
        return In.nextChar();
    }

//////////// DISPLAY ALL GAMES ////////////

	private void displayAllGames(){
		List<Game> allGames = new ArrayList<>(gamesAvailable);
		allGames.forEach(System.out::println);
	}

//////////// DISPLAY AVAILABLE GAMES ////////////

	private void allAvailableGames(){
		System.out.println("\n" + "The following games are available:");
		gamesAvailable.forEach(System.out::println);
		System.out.println("");
	}

//////////// RENT A GAME ////////////

	private Game game(String name){
		for (Game game : gamesAvailable){
			if (game.hasGameName(name)){
				return game;
			}
		}
		return null;
	}

	private int customerId(){
		System.out.print("\n" + "Enter a valid customer ID: ");
		return In.nextInt();
	}

	private String rentGameTitle(){
		System.out.print("Enter the title of the game you wish to rent: ");
		return In.nextLine();
	}

	private void rentGame(){
		int validRentId = customerId();
		Customer customer = kiosk.customer(validRentId);
		
		String gameToRent = rentGameTitle();

		if (customer != null){
			Game game = game(gameToRent);
			if (game != null){
				if (customer.getBalance() > game.getGamePrice()){
					gamesAvailable.remove(game);
					gamesRented.add(game);
					customer.addRentedGame(game);
					customer.addRentingHistory(game);
					customer.deductBal(game.getGamePrice());
					System.out.println("Game rented." + "\n");
				} else {
					System.out.println("You don't have sufficient funds to rent this game." + "\n");
				}

			} else {
				System.out.println("That game is not available or doesn't exist." + "\n");
			}
		}
	}

//////////// RETURN A GAME ////////////

	private String returnGameTitle(){
		System.out.print("Enter the title of the game you wish to return: ");
		return In.nextLine();
	}

	private void returnRentedGame(){
		int validReturnId = customerId();
		Customer customer = kiosk.customer(validReturnId);
		System.out.println(customer.getName() + " has the following games:");
		System.out.println("Games currently rented by " + customer.getName() + ":");
		
		customer.currentlyRented().forEach(System.out::println);

		String gameToReturn = returnGameTitle();

		if (customer != null){
			for (Game rentedGame : customer.currentlyRented()){
				if (rentedGame.hasGameName(gameToReturn)){
					gamesAvailable.add(rentedGame);
					//gamesRented.remove(rentedGame);
					customer.removeRentedGame(rentedGame);
					System.out.println(rentedGame.getGameName() + " has been returned." + "\n");
					break;
				}
			}
		}
	}

//////////// ADD A GAME ////////////

	private String newGameTitle(){
		System.out.print("Enter the title of the game: ");
		return In.nextLine();
	}

	private int newGameYear(){
		System.out.print("Enter the year: ");
		return In.nextInt();
	}

	private String newGameGenre(){
		System.out.print("Enter the genre: ");
		return In.nextLine();
	}

	private int newGamePrice(){
		System.out.print("Enter price: ");
		return In.nextInt();
	}

	public void addGame(){

		System.out.println("\n" + "Adding a new game.");
		String gameTitle = newGameTitle();

		Game game = game(gameTitle);

		int gameYear = newGameYear();

		String gameGenre = newGameGenre();

		genres.add(new Genre(gameGenre));

		int gamePrice = newGamePrice();


		if (game == null){
			gamesAvailable.add(new Game(gameTitle, gameYear, gamePrice, new Genre(gameGenre)));
			System.out.println("Added " + gameTitle + " to catalogue." + "\n");
		} else {
			System.out.println("The game is already in the catalogue.");
		}
	}

//////////// REMOVE A GAME ////////////

	private String removeGameTitle(){
		System.out.print("Enter the title of the game: ");
		return In.nextLine();
	}

	private int removeGameYear(){
		System.out.print("Enter the year: ");
		return In.nextInt();
	}

	public void removeGame(){
		System.out.println("\n" + "Removing a game.");
		String removeTitle = removeGameTitle();
		Game game = game(removeTitle);

		int removeYear = removeGameYear();

		if (game != null && game.hasGameYear(removeYear)){
			gamesAvailable.remove(game);
			System.out.println(removeYear + "\t" + removeTitle + "\t" + game.getGameGenre() + "\t$" + game.getGamePrice()+ " removed from catalogue." + "\n");
		} else {
			System.out.println("No such game found." + "\n");
		}
	}

//////////// LIST GAME ////////////

	public void listGames(){
		System.out.println("\n" + "The Kiosk has the following games:");
		gamesAvailable.forEach(System.out::println);
		System.out.println("");
	}

//////////// DISPLAY GAMES BY YEAR ////////////

	private int gameByYear(){
		System.out.print("\n" + "Enter the year: ");
		return In.nextInt();
	}

	private void findByYear(){

		int gamesYear = gameByYear();

		System.out.println("The kiosk has the following games by that year:");

		for (Game game : gamesAvailable){
			if (game.getGameYear() == gamesYear){
				System.out.println(game);
			}
		}
		System.out.println("");
	}

//////////// DISPLAY GAMES BY GENRE ////////////

	private Genre genre(String name){
		for (Genre genre : genres){
			if (genre.hasGenre(name)){
				return genre;
			}
		}
		return null;
	}

	private String gameByGenre(){
		System.out.print("\n" + "Enter a genre: ");
		return In.nextLine();
	}

	private void findByGenre(){
		String gamesGenre = gameByGenre();
		Genre genre = genre(gamesGenre);
		System.out.println("The kiosk has the following games in that genre:");

		if (genre != null){
			for (Game game : gamesAvailable){
				if (genre.getGenreName() == game.getGameGenre().getGenreName()){
					System.out.println(game);
				}
			}
		}
		System.out.println("");
	}

//////////// DISPLAY GENRE ////////////

	private void displayGenre(){

		ArrayList<String> noDupesList = new ArrayList<String>();

		System.out.println("\n" + "The Kiosk has games in the following genres:");

		for (Genre genre : genres){
			if (!noDupesList.contains(genre.getGenreName())){
				noDupesList.add(genre.getGenreName());
			}
		}

		noDupesList.forEach(System.out::println);
		System.out.println("");
	}

//////////// SHOW FAVOURITES ////////////

	private Game stringToGame(String name){
		for (Game game : gamesRented){
			if (game.hasGameName(name)){
				return game;
			}
		}
		return null;
	}


	private int enterId(){
		System.out.print("\n" + "Enter a customer ID: ");
		return In.nextInt();
	}

	public void showFavourites(){

		ArrayList<String> uniqueGamesRented = new ArrayList<String>();
		ArrayList<Integer> amtRentedSorted = new ArrayList<Integer>();

		int count = 0;

		int searchId = enterId();
		Customer customer = kiosk.customer(searchId);

		if (customer != null){

			System.out.println(customer.getName() + "'s favourite games are:");

			//Removes all duplicates in gamesRented list and put all unique games into "uniqueGamesRented."
			for (Game gameRented : gamesRented){
				if (!uniqueGamesRented.contains(gameRented.getGameName())){
					uniqueGamesRented.add(gameRented.getGameName());
				}
			}

			//Compares games in uniqueGamesRented to gamesRented to count how many times each game appears.
			//Counts how many times each game has been rented.
			for (String uniqueGame : uniqueGamesRented){
				count = 0;
				for (Game gameRented : gamesRented){
					if (uniqueGame.contains(gameRented.getGameName())){
						count++;
					}
				}
				amtRentedSorted.add(count);
			}

			//Sorts amtRentedSorted in descending order using insertion sort algorithm. 
			//Simultaneously sorts the uniqueGamesRented list as well. 
			for (int i = 1; i < amtRentedSorted.size(); i++) {
				int current = amtRentedSorted.get(i);
				String gameToSort = uniqueGamesRented.get(i);
				int j=i;
				while ((j > 0) && (amtRentedSorted.get(j - 1) < current)) {
					amtRentedSorted.set(j, amtRentedSorted.get(j - 1));
					uniqueGamesRented.set(j, uniqueGamesRented.get(j - 1));
					j--;
				}
				amtRentedSorted.set(j, current);
				uniqueGamesRented.set(j, gameToSort);
			}

			//Initialise a count here so that it only prints the top 3 games. 
			count = 0;
			for (String sortGame : uniqueGamesRented){
				Game game = stringToGame(sortGame);
				count++;
				if (count <= 3){
					System.out.println(game);
				}
			}
			System.out.println("");
		}
	}



//////////// PREVIOUS MENU ////////////

	private void previousMenu(){ //R
		kiosk.use();
	}

	private void cataHelp(){
		System.out.println("Please enter a number between 1 and 7 or press R to return to the previous menu.");
	}
}