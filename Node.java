/* 
Neil Patel
CoE 1501; Algorithms
Spring 2018 Semester
GitHub: neilpatel
*/ 

// Import Statements
import java.util.ArrayList;

public class Node{
	String currentWord;
	Node sibling = null;
	Node child = null; 
	Node nextSibling = null;
	Node nextChild = null;
	//String prediction;
	
	
	char character; 

	public Node () {}

	// Use Array List to keep track of all the predictions that are being stored. 
	public ArrayList<String> predictionsGenerator() {
		ArrayList<String> prediction = new ArrayList<String>();
		
		//Check if the sentinel variable has the been triggered. 
		if (character == '*') {
			prediction.add(currentWord);
		}
		
		// Conditional to check if the child node is equal to null
		if (child != null) {
			prediction.addAll(child.predictionsGenerator());
		}
		
		// Conditional to check if the sibling node is equal to null
		if (sibling != null) {
			prediction.addAll(sibling.predictionsGenerator());
		}
		
		
		return prediction; // return the prediction 
	}
	
		
	// Establish a way to  keep track of the currentWord and also eliminate the next line character
	public Node(char ch, String currentWord) {
		character = ch;
		int currentWordSub1 = currentWord.length() - 1;
		this.currentWord = currentWord.substring(0, currentWordSub1);
	}
}


// End of DLBNode Class 
// Neil Patel