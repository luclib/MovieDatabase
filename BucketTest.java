import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BucketTest {

	@Test
	void testBucketMethods() {
		Movie batman = new Movie("The Dark Knight", "Christian Bale", "comic book/ action-adventure / crime thriller", 2008);
		Movie indie = new Movie("Raiders of the Lost Ark", "Harrison Ford", "action-adventure", 1981);
		Movie joker = new Movie("Joker", "Joaquin Phoenix", "psychological thriller", 2019);
		Movie HP1 = new Movie("Harry Potter and the Sorcerer's Stone", "Daniel Radcliff", "fantasy", 2001 );
		Movie hackSaw = new Movie("Hacksaw Ridge", "Andrew Garfield", "biographical war drama", 2016);
		Movie apes = new Movie("War for the Planet of the Apes", "Andy Serkis", "science fiction / action-adventure", 2017);
		
		// isEmpty() method
		Bucket movieList = new Bucket(1);
		assertEquals(true, movieList.isEmpty());
		
		// add(Movie movie) method
		movieList.addMovie(batman);
		assertEquals(batman, movieList.child);
		
		movieList.addMovie(hackSaw);
		assertEquals(hackSaw, movieList.child.sibling);
		
		
		// contains(Movie movie)
		assertEquals(false, movieList.contains("Joker"));
		assertEquals(true, movieList.contains("The Dark Knight"));
		
		// Add rest of movies.
		movieList.addMovie(indie);
		movieList.addMovie(HP1);
		movieList.addMovie(apes);
		movieList.addMovie(joker);
		
		assertEquals(true, movieList.contains("Joker"));
		assertEquals(true, movieList.contains("Hacksaw Ridge"));
		movieList.display();
		
		movieList.remove(indie);
		assertEquals(false, movieList.contains("Raiders of the Lost Ark"));	
		System.out.println();
		movieList.display();
		
		Movie prison = new Movie("Shawshank Redemption", "Tim Robbins", "drama", 1994);
		Movie parasite = new Movie("Parasite", "Song Kang-ho", "black comedy / crime thriller", 2019);
		Movie insideOut = new Movie("Inside Out", "Amy Poehler", "3D computer animated comedy", 2015);
		Movie soldier = new Movie("Patton", "George C. Scott", "epic biographical war film", 1970);
		Movie monastery = new Movie("The Name of the Rose", "Sean Connery", "mystery / historical drama" , 1986);
		Movie jungle = new Movie("Tarzan", "Tony Goldwyn", "animated adventure film", 1999);
		Movie asylum = new Movie("Shutter Island", "Leonardo DiCaprio", "neo-noir / psycholoical thriller", 2010);
		
		
	}

}
