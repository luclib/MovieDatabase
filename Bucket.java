import java.util.Iterator;

/* The Bucket is a custom class that represents
 an element in our hash table. It is essentially
 a container for movies, functioning as a LinkedList
 with each Bucket in a hashtable serving as a parent node
 to a movie, while subsequent movies within the Bucket
 serve as siblings of the first child node.*/

public class Bucket{
	int bucketNumb;	// Key
	Movie child;
	
	public boolean isEmpty() {
		boolean isEmpty = true;
		if(this.child != null)
			isEmpty = false;
		return isEmpty;
	}
	
	// For use in unit tests
	public Bucket(int key) {
		this.bucketNumb = key;
		this.child = null;
	}
	
	public Bucket(int key, Movie child) {
		this.bucketNumb = key;
		this.child = child;
	}
	
	public void display() {
		// Check for child
		Movie child = this.child;
		if(child != null) {
			System.out.println(child.title + ", " + child.leadAct + ", " + child.description + ", " + child.release_Year);
			if(child.sibling != null) {
				System.out.println(child.sibling.title + ", " + child.sibling.leadAct + ", " + child.sibling.description + ", " + child.sibling.release_Year);
				Movie next = this.child.sibling.sibling;
				while(next != null) {
					System.out.println(next.title + ", " + next.leadAct + ", " + next.description + ", " + next.release_Year);
					next = next.sibling;
				}
			}
		}		
	}
	
	public boolean contains(String target) {
		Boolean found = false;
		Movie movie = this.child;
		if(movie != null)
			if(movie.title.equals(target))
				found = true;
			else {
				movie = movie.sibling;
				while(movie != null) {
					if(movie.title.equals(target) ){
						found = true;
						break;
					}
					else
						movie = movie.sibling;
				}
			}		
		return found;
	}	

	public Movie getMovie(String target) {
		Movie movie = this.child;
		if(movie != null)
			if(movie.title.equals(target))
				return movie;
			else {
				movie = movie.sibling;
				while(movie != null) {
					if(movie.title.equals(target)) {
						return movie;
					}
					else
						movie = movie.sibling;
				}
			}
		return movie;
	}	

	// For Unit-testing
	public void addMovie(Movie addedMovie) {
		if(this.child == null) {
			this.child = addedMovie;
			this.child.parent = this;
		}
		else{
			// Go to the last movie in the list.
			Movie movie = this.child;
			while(movie.sibling != null) {
				movie = movie.sibling;
			}
			movie.sibling = addedMovie; // Append movie to bucket
			movie.sibling.parent = this;	// Designate bucket as parent of movie
		}		
	}
	

	public boolean remove(Movie target) {
		boolean removed = false;
		if(this.contains(target.title)) {
			// Go to the last movie.
			Movie currMovie = this.child;
			Movie nextMovie;
			Movie prevMovie;
			if(currMovie.title == target.title) {	// Check the child
				// Check for siblings
				if(currMovie.sibling == null) {	// Check for siblings
					this.child = null;
					currMovie.parent = null;
					removed = true;
				}
				else {
					nextMovie = currMovie.sibling;
					currMovie.sibling = null;
					this.child = nextMovie;
					removed = true;
				}	
			}
			else { 	// Move on the first sibling
				do {
					prevMovie = currMovie;
					currMovie = currMovie.sibling;
					nextMovie = currMovie.sibling;
					if(currMovie.title == target.title) {
						prevMovie.sibling = nextMovie;
						currMovie.sibling = null;
						removed = true;
						break;
					}
				} while(nextMovie != null);
			}
		}
		return removed;
	}
}