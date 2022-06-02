public class Genre {
	private String name;
	
    public Genre(String name){
        this.name = name;
    }

    public String getGenreName(){
        return this.name;
    }

    public boolean hasGenre(String name){
        return name.equals(this.name);
    }

    @Override
    public String toString(){
        return name;
    }

}