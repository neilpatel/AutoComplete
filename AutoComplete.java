/* 
Neil Patel
CoE 1501; Algorithms
Spring 2018 Semester
GitHub: neilpatel
*/ 


// Import Statements

import java.util.*;
import java.io.*;
import java.math.*;
import java.lang.*;
import java.text.StringCharacterIterator;


public class AutoComplete {
	static char userInput; 	// Variable to store userInput
	static int count = 0;	// Variable to store the number of iterations. (used for averageTime)
	static DLBTrie dictionaryTrie = new DLBTrie(); // Create a new structure to hold dictionary values/words. 
	static DLBTrie user_history = new DLBTrie(); // Create a new structure to hold user_history values/words. 
	static ArrayList<String> tPredictions, hPredictions = new ArrayList<String>(), pPredictions= new ArrayList<String>(), suggestion = new ArrayList<String>(); // Make an ArrayList of Strings for each type of suggestion/prediction
	static double startTime;	// Variable for start time
	static double endTime;		// Variable for end time
	static double averageTime;	// Variable for the average time that past 
	static double totalTime;	// Variable for total time
	static double deltaTime;	// Variable for delta time. (end - start)
	static double time; 
	static String currentWord; 	// Variable to hold letters in the current word. (filling next word)
	static String outputWord = ""; 	// Variable that holds what word will be displayed as suggestion. 
	
	public static void main(String[] args) {
		System.out.println("\nCreating the Dictionary Structure...\n");
		
		// Try-Catch block to see if a dictionary.txt is in present working directory. 
		try{
			time = System.nanoTime();
			Scanner dictionary = new Scanner(new File ("dictionary.txt"));
			
			while (dictionary.hasNextLine()) {
				
				dictionaryTrie.add(dictionary.nextLine());
				
			}
			time = System.nanoTime() - time;
			//deltaTime will hold the current time(system) - time in execution
		
			
			System.out.print("The dictionary has been generated using dictionary file. ");
		} // end of try 
		catch (IOException e) {
			System.out.println("Error: No Valid dictionary.txt file has been provided. ");
		}
		
		// Try-Catch block to detect a user_history.txt file
		// If one does not exist, one will be created. 
		try {
			time = System.nanoTime();
			Scanner history = new Scanner(new File("user_history.txt"));
			
			while (history.hasNextLine()) {
				user_history.add(history.nextLine());
			}
			
			System.out.println("\n\nThe User History text file exists in your present working directory. A new one was not created. ");
			time = System.nanoTime() - time;
		}
		// IF no user history file could be detected in the present working directory, one will be created. 
		// FileWriter will be used to write the words to the new file that was created. 
		catch (FileNotFoundException e) {
			System.out.print("\n\nNote: No user_history.txt file has been detected. Do not worry!  ");
			System.out.println("\n\n\t >>One will be created for you  now... ");
			try {
				FileWriter makeUserHistoryFile = new FileWriter(new File ("user_history.txt"));
				makeUserHistoryFile.close();
			}
			// If some error takes place and user_history.txt could not be created. 
			catch (IOException e2)
			{
				System.out.println("Error: Could not create a user_history.txt file \n\n");
			}
		}
		
		// Create Scanner for taking user input. 
		Scanner sc = new Scanner(System.in);
		System.out.print("\n\nEnter your first character: ");
		do {
			currentWord = "";		// Building the current word as a string
			if (count > 0) {
				System.out.print("Enter first character of the next word: ");
			}
			userInput = sc.nextLine().charAt(0);
			
		
			pPredictions.clear();			//Clear all the past predictions when typing in a new word. (at this point it holds '[]' that is why it gets cleared)
			
			//hPredictions.clear(); 		// Clear hPredictions... (at this point it holds '[]')
			//suggestion.clear();
						
			dictionaryTrie.resetPredictions();	//Clear the predictions in the dictionary Trie. *will result in NullPointerException if commented out
			user_history.resetPredictions();	//Clear all the predictions in the user_history. This is on the next iteration so the same predictions dont come up. 
			
			
			
			char firstLetter = 'A';				// Variable used to hold the letter A, aka the first letter of the DLB. 
			
			//Do the following while the user enters in a valid letter >> a letter between A-Z
			while (userInput >= firstLetter){
				suggestion.clear(); 	// Clear the suggestions.
				//hPredictions.clear();
				currentWord += userInput;
				
				
				int a = 0; 			// Variable for getting a particular index of the suggestions (outputWord)
				outputWord = ""; 	// Set the initial outputword to a blank string. The user at this point has not typed any word to match a value in the dictionary. 
				
				
				startTime = System.nanoTime(); 		// Use System nanoTime to set the startTime
				tPredictions = dictionaryTrie.predictionsGenerator(userInput); 	// @param tPredictions -> total number of predictions that are generated with userInput
				hPredictions = user_history.predictionsGenerator(userInput);	// @param hPredictions -> history of predictions that are generated with userInput
				
				int counter = 1;
				for(int i=0; i< hPredictions.size(); i++) {
					
					suggestion.add(hPredictions.get(i));
					outputWord = outputWord + "(" + (counter) + ") " + hPredictions.get(i) + "     ";
					counter++;
				}
				
			//** Important check**
				// This for loop checks to see if the current word that is being populated is in the user history AND in the dictionary. 
					// If this is true, meaning that the user has entered a word in before and it is in the dictionary. When the user tries to enter
					// in the same word again, ONLY ONE will populate as one of the five predictions. (specified in the requirements)
				for (String temp : tPredictions) {
					if (suggestion.size() == 5) {
						
						break;
					}
					else if (suggestion.contains(temp)) {
						
						continue;
					}
					else {
						
						suggestion.add(temp);
						outputWord = outputWord + "(" + (counter) + ") " + temp + "     ";
						counter++;
					}
				}	
				
				
				endTime = System.nanoTime();
				if (outputWord.equals("")) {
					outputWord += "I'm afraid no predictions match your current entry. \nPlease enter '$' (once finished) to save this word into your user history. ";
				}
				outputWord += '\n';
				System.out.print("\n(");
				
				// This block will calculate and format all the times and display the time. 
				// Calculate the necessary 'calculations' to display the time after each letter is entered. 
			
				double convertingFactor = 10000000.0; 		//Store a constant variable for conversion from nano seconds to seconds. 
				deltaTime = endTime - startTime; 		
				System.out.print(deltaTime/convertingFactor);
				System.out.print(" s) \n");
				System.out.print("Predictions: \n");
				
				
			
				System.out.println(outputWord);
				count = count + 1; 
				totalTime += (deltaTime)/(convertingFactor); 
				
				
				
				System.out.print("Enter the next character: ");		// After all calculations are done, user will be prompted to enter in the next character. 
				userInput = sc.nextLine().charAt(0);
			} // end of  while loop
		
			// Conditional to check if the user enters in a number between 1-5 to complete the word. 
			// IF this is done, the completed word will be placed into the user.
			if (userInput < '6' && userInput > '0'){				// ** COuld not use 1 to 5 inclusive, because I got a IndexOutOfBoundsException
				System.out.println("\n\n\tWORD COMPLETED:  " + suggestion.get(userInput - '1'));
				System.out.println("");
				
				// Conditional to check if the history of predictions contains a suggestion that is provided based on the user input 
				if (!hPredictions.contains(suggestion.get(userInput - '1'))) {
					user_history.add(suggestion.get(userInput - '1')); 	// if reached, suggestion will be added to the user_history file. 
				
					try {
						FileWriter makeUserHistoryFile = new FileWriter(new File("user_history.txt"), true);
					
						makeUserHistoryFile.write(suggestion.get(userInput - '1') + "\n");
						System.out.println();

						makeUserHistoryFile.close();
					}
					catch (IOException e) {
						System.out.println("Error: Could not process this request with 'user_history.txt'");
					}
				}	
			
			// IF the user uses the 'create a new word' character with the '$' key, the word will be completed at this point immediately. 
			}else if (userInput == '$') {
				System.out.println("\n\n\tWORD COMPLETED:  " + currentWord);
				System.out.println();
				if (!hPredictions.contains(currentWord)) {
					user_history.add(currentWord);
					try{
						FileWriter makeUserHistoryFile = new FileWriter(new File("user_history.txt"), true);
						makeUserHistoryFile.write(currentWord + "\n");
						System.out.println("");
						makeUserHistoryFile.close();
					}
					catch (IOException e) {
						System.out.println("Error: Could not process this request with 'user_history.txt'");
					}
				}
			}
		}
		while(userInput != '!');
		
		averageTime = (totalTime/count);
		System.out.println("Average Time:  " + averageTime + " s");
		System.out.println("Bye!");
			
	}// end of main method
} // end of public class


// End of AutoComplete class
// Neil Patel 