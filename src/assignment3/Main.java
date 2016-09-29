/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Max Fennis
 * maf3743
 * 16460
 * Davin Eliasson
 * de5877
 * 16460
 * Slip days used: <0>
 * Git URL:
 * Fall 2016
 */


package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	
	// static variables and constants only here.
	static boolean found = false;
	static boolean valid = false;
	public static String strt;
	public static String last;
	public static ArrayList<String> ans = new ArrayList<String>();
	public static ArrayList<String> previous = new ArrayList<String>();
	
	public static void main(String[] args) throws Exception {
		
		Scanner kb;	// input Scanner for commands

		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
		} else {
			kb = new Scanner(System.in);// default from Stdin
		}
		initialize();
		ArrayList<String> parsed = parse(kb);
		strt = parsed.get(0);
		last = parsed.get(1);
		
		ArrayList<String> dfs = getWordLadderDFS(strt, last);
		printLadder(dfs);
//		ArrayList<String> bfs = getWordLadderBFS(strt, last);
//		printLadder(bfs);
	}
	
	/**
	 * 
	 */
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
		ArrayList<String> passed = new ArrayList<String>();
		String first = keyboard.next();
		
		if(first.equals("/quit"))
		{
			return passed;
		}
		
		passed.add(first);
		String second = keyboard.next();
		passed.add(second);
		
		strt = passed.get(0);
		last = passed.get(1);
		
		return passed;
	}
	
	/**
	 * 
	 * @param curr
	 * @param end
	 * @return
	 */
	public static ArrayList<String> getWordLadderDFS(String curr, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// Return empty list if no ladder.

		Set<String> dict = makeDictionary();
		ArrayList<String> history = new ArrayList<String>();
		
		strt = curr;
		last = end;
		
		strt = strt.toUpperCase();
		last = last.toUpperCase();
		
		//modifies global ArrayList with recursion
		getDFShelper(curr, end, dict, history);
		return ans;
	}
	
	/**
	 * 
	 * @param curr
	 * @param end
	 * @param dict
	 * @param hist
	 */
	public static void getDFShelper(String curr, String end, Set<String> dict, ArrayList<String> hist) {
		
		// used to check if branch is valid
		valid = false;
		
		// checks to see if the current word is the end word
		if(curr.equals(end))
		{
			// used to check if the end word was found
			found = true;
			hist.add(curr);
			
			for(String lower : hist)
			{
				lower = lower.toLowerCase();
			}
			
			ans = hist;
		}
		
		if(found==false)
		{	
			//need Hashtable to store permutations of current word that are in the dictionary by key
			Hashtable<Integer,ArrayList<String>> table = new Hashtable<Integer,ArrayList<String>>();
			
			for (int x = 0; x < curr.length(); x++)
			{
				for(int y = 0; y < 26; y++)
				{
					
					char[] firstMod = curr.toCharArray();
					char letter = (char)(y+65);
					
					if(letter == firstMod[x])
					{
						continue; //skip rest of code in the loop
					}
					
					firstMod[x] = letter;
					String perm = new String(firstMod); // converts char array to String
					
					if(dict.contains(perm) && !previous.contains(perm))
					{
						previous.add(perm);
						
						int key = countLetterDiff(perm,end);
						
						if(table.get(key) != null)
						{
							table.get(key).add(perm);
						}
						else
						{
							ArrayList<String> validWords = new ArrayList<String>();
							validWords.add(perm);						
							table.put(key, validWords);
						}
					}
				}	
			}

			//loops through permutations that are in the dictionary by key in the hashtable
			for(int i = 0; i < curr.length()+1; i++)
			{
				//check current key of the hashtable
				if(table.get(i)!= null)
				{
					for(int j = 0; j < table.get(i).size(); j++)
					{
						// valid branch found  
						valid = true;
						if(!hist.contains(curr))
						{
							hist.add(curr);
						}
						
						// return best next word
						getDFShelper(table.get(i).get(j), end, dict, hist);
						
						// kill all other live calls
						if(found==true){return;}
					}
				}
			}
			//remove added word from invalid branch
			if(valid == false)
			{
				hist.remove(curr);
			}
			
		}
	}
	
	/**
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		
    	strt = start.toUpperCase();
    	last = end.toUpperCase();
    	
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
    /**
     * 
     * @return
     */
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
	
	/**
	 * 
	 * @param ladder
	 */
	public static void printLadder(ArrayList<String> ladder) {
		
		if(ladder.size() == 0){
			System.out.println("no word ladder can be found between " + strt + " and " + last + ".");
		}
		else{
			System.out.println("a " + (ladder.size()-2) + "-rung word ladder exists between " + ladder.get(0) + " and " + ladder.get(ladder.size()-1)+ ".");
			for(String x : ladder)
			{
				System.out.println(x);
			}
		}
		
	}
	
	/**
	 * 
	 * @param curr
	 * @param end
	 * @return
	 */
	public static int countLetterDiff(String curr, String end)
	{
		int cnt = 0;
		char[] t1 = curr.toCharArray();
		char[] t2 = end.toCharArray();
		
		for(int s = 0; s < t2.length; s++)
		{
			if(t1[s] != t2[s])
			{
				cnt += 1;
			}
		}
		
		return cnt;
	}
}
