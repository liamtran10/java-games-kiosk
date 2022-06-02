import java.util.ArrayList;
import java.util.List;

public class Game {
	private String title; 
	private int year; 
	private int price; 
	private Genre genre;
	
	public Game(String title, int year, int price, Genre genre){
		this.title = title;
		this.year = year;
		this.price = price;
		this.genre = genre;
	}

	public boolean hasGameName(String title){
		return title.equals(this.title);
	}

	public boolean hasGameYear(int year){
		return year == this.year;
	}

	public String getGameName(){
		return this.title;
	}

	public int getGamePrice(){
		return this.price;
	}

	public int getGameYear(){
		return this.year;
	}

	public Genre getGameGenre(){
		return this.genre;
	}

	@Override
	public String toString(){
		return year + "\t" + title + "\t" + genre + "\t$" + price;
	}
}