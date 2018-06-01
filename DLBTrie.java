/* 
Neil Patel
CoE 1501; Algorithms
Spring 2018 Semester
GitHub: neilpatel 
*/ 

// Import Statements

import java.util.ArrayList;


public class DLBTrie {
	private final char SENTINEL =  '*';
	
		
	Node headNode;			// Establish a Head node
	Node iteratingNode;		// Establish a way to iterate through the nodes
	Node current;			// Establish a current node
	
	//Constructor 
	public DLBTrie() {	
	}
	
	// Adding the user's input into the Structure
	public void add(String userInput){
		userInput += SENTINEL;
		// Conditional for head node to see if it is null right off the bat. 
		if (headNode == null) {
			this.addStartingVal(userInput);
		}
		// if the head node is not null
		// set current node to the head node. 
		// loop through the user inputs via the ArrayList until the sentinel value is reached. 
		// if a child node is terminated because it is null, set it to the current node. 
		else {
			Node current = headNode;
			for(int i=0; i < userInput.length(); i++) {
				if(current.character == userInput.charAt(i) || (current.character != SENTINEL && current.child == null)) {
					if(current.child == null) {
						current.child = new Node(userInput.charAt(i), userInput);
						current = current.child;
					}
					else {
						current = current.child; // set the child node to the current node. 
					}
					
				}
				// loop through while the current sibling is not null  and the current node in DLB is equal to the user input at i
				else {
					while(current.sibling != null && current.character != userInput.charAt(i)) {
						current = current.sibling;
					}
					// set the child node to the current node. 
					if(current.character == userInput.charAt(i)) {
						current = current.child;
					}
					else if (current.sibling == null) {
						current.sibling = new Node(userInput.charAt(i), userInput);
						// set the sibling to the current node. 
						current = current.sibling;
					}
				}
			} 		
		}
	}
		
	// Method to reset all the predictions displayed when a new iteration is started. 
	public void resetPredictions() {
		iteratingNode = headNode;
	}

	// ArrayList Structure to provide the user suggestions. 
	public ArrayList<String> predictionsGenerator(char userInput) {
		// Conditional to check if the head node is empty/null.
		if (headNode == null) {
			return new ArrayList<String>();
		}
		// Loop to go through the sibling nodes and make sure the current value is not equal to the user input. 
		while (iteratingNode.sibling != null && iteratingNode.character != userInput) {
			iteratingNode = iteratingNode.sibling;
		}
		// If a match occurs, set the child node to the current iterating node. 
		if (iteratingNode.character == userInput) {
			iteratingNode = iteratingNode.child;
		}
		// If the opposite takes place, where the user input is not equal to the current node. 
		else if (iteratingNode.character != userInput) {
			iteratingNode = new Node();
			return new ArrayList<String>();
		}
		return iteratingNode.predictionsGenerator();
	}
	
	// Iterate through all the characters the user inputs and see if it matches a corresponding value in DLB
	public void addStartingVal(String userInput) {
		headNode = new Node(userInput.charAt(0), userInput);
		current = headNode;
		
		//Note: must start iterating at 1 to length otherwise child references will result in issues when reading from user_history
		for (int i = 1; i < userInput.length(); i++) {
			current.child = new Node(userInput.charAt(i), userInput);
			current = current.child;
		}
	}
} //end of public class 


// End of DLB Trie
// Neil Patel