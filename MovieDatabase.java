import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MovieDatabase {

	static Scanner scnr = new Scanner(System.in);
	final int INITIAL_VALUE = 5381;
	final int MULTIPLIER = 33;
	
	Bucket[] movieDB;
	int numbItems;
	double loadFactor;
	boolean running = true;
	
	public MovieDatabase(){
		this.movieDB = new Bucket[13];
		int i;
		for(i = 0; i < movieDB.length; i++) {
			movieDB[i] = new Bucket(i+1);
		}
		this.numbItems = 0;		// number of items.
		this.loadFactor = 0;
		
	}
	
	public int getNumbItems() {
		int numbItems = 0;
		for(Bucket element: this.movieDB) {
			while(element.child!=null) {
				numbItems++;
				Movie movie = element.child.sibling;
				while(movie!=null) {
					numbItems++;
					movie = movie.sibling;
				}
				break;
			}
		}
		return numbItems;
	}
	
	public int getLoadFactor() {
		int LF = this.getNumbItems()/movieDB.length;
		return LF;
	}
	
	public void startUp() {
		// Create a blank database
		System.out.println("Welcome to the Movie Database");
		System.out.println("-------Make a choice:--------");
		System.out.println("Read a database file (1)");
		System.out.println("Add a new movie (2)");
		System.out.println("Search for a movie by title (3)");
		System.out.println("Delete a movie (4)");
		System.out.println("Display all movies(5)");
		System.out.println("Exit (-1)");
		System.out.println("-----------------------------");
		System.out.print("Your choice: ");		
	}
	
	// Clear the movie database and load the movies from
	// movie.txt only.
	public void clearDB() {
		this.movieDB = new Bucket[13];
		int i;
		for(i = 0; i < movieDB.length; i++) {
			movieDB[i] = new Bucket(i+1);
		}
		this.numbItems = 0;		// number of items.
		this.loadFactor = numbItems/movieDB.length;
	}
	
	public void loadDB() {
		// load database
		try {
			FileInputStream inFile;
			inFile = new FileInputStream("src/movies.txt");
			Scanner input = new Scanner(inFile);
			input.useDelimiter(",");
			
			// clear the current movide database.
			this.clearDB();
			
			while(input.hasNext()) {
				String title = input.next();
				String leadAct = input.next();
				String description = input.next();
				int release_Year = Integer.parseInt(input.nextLine().replace(", ", ""));
				Movie movie = new Movie(title, leadAct, description, release_Year);
				this.addMovie(movie);
			}
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getClass() + " error: " + e.getMessage());
		}		
		System.out.println("File loaded successfully.");
	}
	
	// Method to verify that the input entered by the user is numerical
	public static boolean isDigit(String word) {
		boolean isDigit = true;
		char[] stream = word.toCharArray();
		for(char ch: stream){
			if(!(Character.isDigit(ch))) isDigit = false; break;
		}
		return isDigit;
	}
	
	public int HashMultiplicative(String key) {
		char[] charSequence = key.toCharArray();
		int stringHash = INITIAL_VALUE;
		for(char character: charSequence) {
			stringHash = (stringHash * MULTIPLIER) + character;
		}
		if(this.numbItems == 0)
			return 0;
		else
			return Math.abs(stringHash % this.numbItems);
	}

	public void update() {		
		// Check load factor
		this.loadFactor = this.getLoadFactor();
		if(this.loadFactor > 0.5) {
			Bucket[] oldDB = this.movieDB;
			
			// Double the size of the array
			int currDbSize = oldDB.length;
			int newDbSize = 2 * (currDbSize);
			
			// transfer data elements to new database
			Bucket[] newDB= new Bucket[newDbSize];
			
			// Initialize new db
			int i;
			for(i = 0; i < newDB.length; i++) {
				newDB[i] = new Bucket(i+1);
			}
			
			// search through every movie in each bucket of the database
			for(Bucket element: oldDB) {
				Movie movie = element.child;
				while(movie != null) {
					int index = this.HashMultiplicative(movie.title);
					newDB[index].addMovie(movie);
					movie = movie.sibling;
				}
			}
			this.movieDB = newDB;
		}
	}

	public void addMovie(Movie movie) {
		// get hash multiplicative
		int index = HashMultiplicative(movie.title);
		while(Math.round(index) >= this.movieDB.length)
			index = HashMultiplicative(movie.title);
		this.movieDB[index].addMovie(movie);
		this.numbItems++;
	}

	public void removeMovie(String title) {
		boolean found = false;
		for(Bucket element: movieDB) {
			if(element.contains(title)) {
				element.remove(element.getMovie(title));
				found = true;
				System.out.println(title + "has been removed from database.");
				this.numbItems--;
				break;
			}
		}
		if(!found) {
			System.out.println("Movie not found!");
		}	
	}

	public void search(String title) {
		boolean found = false;
		Movie movie;
		for(Bucket bucket: movieDB) {
			if(bucket.contains(title)) {
				movie = bucket.getMovie(title);
				found = true;
				System.out.println("\nMovie found!");
				System.out.println("  Title: " + movie.title);
				System.out.println("  Lead actor/actress: " + movie.leadAct);
				System.out.println("  Description: " + movie.description);
				System.out.println("  Release year: " + movie.release_Year);
				break;
			}
		}
		if(!found) {
			System.out.println("Movie not found!");
			System.out.println("Did you spell the title correctly?");
		}			
	}

	public void displayAll() {
		for(Bucket element: movieDB)
			if(element.child != null)
				element.display();
	}
	
	public void readCommand(String command) {
		switch(command) {
			//Read a database file
			case("1"):
				System.out.print("Enter filename: ");
				String fileName = scnr.next();
				if(!fileName.equals("movies.txt"))
					System.out.println("No file with name '" + fileName + "' was found.");
				else
					loadDB();
				break;
				
			// Add a new movie
			case("2"):
				try{
					// Get input data from user.
					String title, leadAct, description;
					
					int release_year;
					System.out.print("Enter a movie's title: ");
					scnr.nextLine();	// For some bizarre reason, the scanner starts with a blank line...
					title = scnr.nextLine();
					
					System.out.print("Enter movie's lead actor/actress: ");
					leadAct = scnr.nextLine();
					
					System.out.print("Enter movie's description: ");
					description = scnr.nextLine();
					
					System.out.print("Enter movie's release year: ");
					release_year = Integer.parseInt(scnr.nextLine().replace(", ",""));
					
					// Create movie object from input file
					Movie movie = new Movie(title, leadAct, description, release_year);
					this.addMovie(movie);
					this.update();
					System.out.println("Movie inserted successfully...");
					break;
				}catch(NumberFormatException e) {
					System.out.println("\nYou have entered a wrong format for the release year.\n"
							+ "Please try again and enter a valid year only.\n");
				}
				
			
			// Search for a movie by title.
			case("3"):
				// Get input data from user.
				System.out.print("Enter the movie's title: ");
				scnr.nextLine();
				String query = scnr.nextLine();
				this.search(query);
				break;
			
			// Delete a movie.
			case("4"):
				System.out.print("Enter the movie's title: ");
				scnr.nextLine();
				String toBeRemoved = scnr.nextLine();
				this.removeMovie(toBeRemoved);
				this.update();
				break;
			
			// Display all movies.
			case("5"):
				this.displayAll();
				break;
			
			// Exit
			case("-1"):
				this.running = false;
				System.out.println("Exiting program...");
				break;
			default:
				System.out.println("\nUnknown command: please enter one of the following numbers:\n   1: read a database file"
						+ "\n   2: add a new movie\n   3: search for a movie by title(3)\n   4: Delete a movie\n   5: Display all movies"
						+ "\n  -1: Exit\n");
				System.out.println("Your choice: ");
				command = scnr.next();
				break;
		}
	}
	
	public static void main(String[] args) {
		MovieDatabase db = new MovieDatabase();
		db.startUp();
		String command = scnr.nextLine();
		db.readCommand(command);
		while(db.running) {
			{
				System.out.print("\nYour choice: ");
				command = scnr.next();
			}
		}
	}

}
