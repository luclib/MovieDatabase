
public class Movie{
	String title;	// Identity key
	String leadAct; // Can be actor or actress
	String description;
	int release_Year;
	Bucket parent;
	Movie sibling;
	
	// For use in the add() method
	public Movie(String title, String leadAct, String description, int release_year) {
		this.title = title;
		this.leadAct = leadAct;
		this.description = description;
		this.release_Year = release_year;
		this.sibling = null;
	}
}
