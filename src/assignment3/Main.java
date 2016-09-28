/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Git URL:
 * Fall 2016
 */


package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	
	// static variables and constants only here
	
	
	public static void main(String[] args) throws Exception {
		
		Scanner kb = new Scanner(System.in);	// input Scanner for commands
		
		initialize();
		ArrayList<String> parsed = parse(kb);
		ArrayList<String> ladder_BFS = getWordLadderBFS(parsed.get(0), parsed.get(1));
		printLadder(ladder_BFS);

		
		
	// TODO methods to read in words, output ladder
	}
	
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
		
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of 2 Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		ArrayList<String> words= new ArrayList<String>();
		String first_word = keyboard.next();
		if(first_word.equalsIgnoreCase("/quit")){ return words; }
		else{
			words.add(first_word);
			String second_word = keyboard.next();
			words.add(second_word);
		}
		return words;
	}
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// Return empty list if no ladder.
		
		
		return null;
		
	}
	
	/**
	 * This method uses the breadth first search algorithm to find the optimal solution to
	 * finding a path between two words with each subsequent word have only one letter change
	 * from the previous word.
	 * @param start
	 * @param end
	 * @return ArrayList<String> ladder
	 */
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
    	/* Convert start word to array list for adding to queue */
    	ArrayList<String> start_word = new ArrayList<String>();
    	start_word.add(start);
    	    	
    	/* Create arraylist for dictionary */
    	Set<String> dict = (makeDictionary());
    	
    	/* Create Linked List for word ladder and add start word */
		Queue<ArrayList<String>> BFS_words = new LinkedList<ArrayList<String>>();
		BFS_words.add(start_word);
		
		while(!BFS_words.isEmpty()){
			/* Deqeue first spot in queue and pull out last word in list */
			ArrayList<String> ladder = BFS_words.remove();
			String word = ladder.get(ladder.size() - 1);
			
			/* If word equals end word, return the ladder */
			if(word.equals(end)){
				return ladder;
			}
			
			/* Create array of characters for each word */
			char[] letters = word.toCharArray();
			
			/* check for neighbors of word */
			for(int i = 0; i < letters.length; i++){
				/* traverse through alphabet */
				for(char alpha = 'a'; alpha < 'z'; alpha++){
					char hold_letter = letters[i];
					if(letters[i] != alpha){
						letters[i] = alpha;
					}
					
					
					/* Check to see if word is in Dictionary and not already in ladder */
					String word_revision = new String(letters);
					String new_word = word_revision.toUpperCase();
					
					if(dict.contains(new_word) && !ladder.contains(word_revision)){
						/* make a copy of current ladder and add new word to end */
						ArrayList<String> new_ladder = new ArrayList<String>();
						new_ladder.addAll(ladder);
						new_ladder.add(new_word.toLowerCase());
						
						/* Remove word from dictionary */
						dict.remove(new_word);
						
						/* Add the new ladder to the end of the queue */
						BFS_words.add(new_ladder);
					}
					letters[i] = hold_letter;
				}
			}
		}
		/* No path found so return empty array */
		ArrayList<String> empty_array = new ArrayList<String>();
		return empty_array;
	}
    
    
    
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
	
	
	
	
	public static void printLadder(ArrayList<String> ladder) {
		if(ladder.size() == 0){
			System.out.println("no word ladder can be found between ");
		}
//		else if(ladder.size() == 2){
//			System.out.println("no word ladder can be found between " + ladder.get(0) + " and " + (ladder.get(ladder.size() - 1)));
//		}
		else{
			System.out.println("a " + (ladder.size() - 2) + "-rung word ladder exists between " + ladder.get(0) + " and " + (ladder.get(ladder.size() - 1)));
			for(int i = 0; i < ladder.size(); i++){
				System.out.println(ladder.get(i));
			}
		}
	}
	
	
	
	// TODO
	// Other private static methods here
	
	
	public static void remove_nonNeighbors(ArrayList<String> neighbors){
		/* Iterate through whole array and remove non neighbor words */
		for(int i = neighbors.size() - 1; i > 0; i--){
			int difference = 0;
			/* Get two words that are next to each other for comparison */
			String word1 = neighbors.get(i);
			String word2 = neighbors.get(i-1);
			for(int length = 0; length < word1.length(); length++){
				if(word1.charAt(length) != word2.charAt(length)){
					difference++;
				}
			}
			if(difference != 1){ neighbors.remove(i-1); }
		}
	}
}
