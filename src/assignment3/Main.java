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
	
	// static variables and constants only here.
	static boolean found = false;
	static boolean valid = false;
	public static String strt;
	public static String last;
	public static ArrayList<String> ans = new ArrayList<String>();
	
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
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of 2 Strings containing curr word and end word. 
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
		
		return passed;
	}
	
	public static ArrayList<String> getWordLadderDFS(String curr, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// Return empty list if no ladder.

		Set<String> dict = makeDictionary();
		ArrayList<String> history = new ArrayList<String>();
		
		//modifies global ArrayList with recursion
		getDFShelper(curr, end, dict, history);
		return ans;
	}
	
	public static void getDFShelper(String curr, String end, Set<String> dict, ArrayList<String> hist) {
		
		valid = false;
		//checks to see if the current word is the end word
/*		if(curr.equals(strt))
		{
			hist.add(curr);
		} */
		
		if(curr.equals(end))
		{
			found = true;
			hist.add(curr);
			ans = hist;
		}
		
		if(found==false)
		{	
			//convert word to char array 
			char[] first  = curr.toCharArray();

			//loops through dictionary to find words with 1 letter difference
			for(String x : dict)
			{	
				if(found==true){return;}
				if (!hist.contains(x))
				{
					int diff = 0;
					char[] second = x.toCharArray();
		/*			
					if(x.equals("curr")|| x.equals("STARS")|| x.equals("SOARS")|| x.equals("SOAKS")|| x.equals("SOCKS")|| x.equals("COCKS")|| x.equals("CONKS")|| x.equals("CONES")|| x.equals("CONEY")|| x.equals("MONEY"))
					{
						diff = 0;
					}
		*/				
					//comparing letters
					for(int s = 0; s < second.length; s++)
					{
						if(first[s] != second[s])
						{
							diff = diff + 1;
						}
					}
					
					//returning valid word found
					if (diff == 1)
					{
						valid = true;
						hist.add(curr);
						getDFShelper(x, end, dict, hist);
					}
				}
			}
			if(valid == false)
			{
				hist.remove(curr);
				//dict.remove(curr);
			}
		}
	}
	
    public static ArrayList<String> getWordLadderBFS(String curr, String end) {
		
		// TODO some code
		Set<String> dict = makeDictionary();
		// TODO more code
		
		return null; // replace this line later with real return
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
}
