/*
This is the main driver program which runs all the modules
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import algorithms.Sequences;
import algorithms.LinkedListOperation;
import algorithms.QuadraticProbingHashTable2;
import algorithms.QuadraticProbingHashTable2.HashEntry;

public class FinalRun{
	public static  String DIR_NAME = "W3C_Web_Pages/Text2/";

	public static void display_contents_file(String filename) throws FileNotFoundException {
		String path = DIR_NAME + filename;
		File file = new File(path);
		Scanner scanner = new Scanner(file);
		System.out.println("********URL content******");
		while (scanner.hasNext()) {
			//Display the content of the file line by line
			String line = scanner.nextLine();
			System.out.println(line+"\n");
		}//end of while
	scanner.close();
	}//end of display_contents_file()

	public static void display_websites(HashEntry obj,int flag, LinkedListOperation linked_list_obj) throws FileNotFoundException {

			System.out.println("\n**********Recommended URL links based on word frequency**********\n");
			//display_file is a property of the words in hash table that contains file
			//name along with its frequency in the sorted order. We display the top
			//10 URL with its id in the order of frequency in descending order.
			for (int l = 0; l < obj.display_file.size(); l++) {
				 System.out.println(l+1 + ". " + obj.display_file.get(l));
				 if (l == 9) break;
			}

		Scanner in2 = new Scanner(System.in);
		String temp="";
		//flag variable is used just for playing with the input strings
		//User can open the link by URL id.
		if (flag == 1) temp = "Enter the id of the URL you want to open";
		if (flag == 2) temp = "If you want to check out another url, enter the id of that URL you want to open";

		System.out.println("\n"+temp+": \nEnter -100 to go back");
		System.out.println("\tEnter your choice: ");
		int choice = in2.nextInt();

		if (choice == -100)
			return;

		// Hnadling out of range values
		else if (choice > obj.display_file.size() || choice < 0) {
			System.out.println("!!!!!!! PLEASE GIVE VALID INPUT CHOICE !!!!!!!!!!!!");
			display_websites(obj, 1, linked_list_obj);
		}

		else {
			//Access the display_file to get the string filename
			String file_name = obj.display_file.get(choice-1).toString();
			file_name = file_name.substring(0,file_name.indexOf("="));

			//Add the URL to the linked list object. Note timestamp is automatically
			//added in the run time and is stored with the file name in the list.
			linked_list_obj.add_node_beginning(file_name);

			//Show the content of the URL/file.
			display_contents_file(file_name);

			//Recursive function call to allow user to open other links for the same
			//words if the user wants.
			display_websites(obj, 2, linked_list_obj);
		}
	}



	public static void search_word(QuadraticProbingHashTable2 QH, LinkedListOperation linked_list_obj) throws FileNotFoundException {
		Scanner in2 = new Scanner(System.in);
		//Ask the user to search for a word
		System.out.println("\n\tEnter a word: ");
		String word = in2.nextLine();

		//Search the word in the quadratic hash table and get its frequency
		int freq = (QH.return_freq(word));
		HashEntry obj = null;

		//If freq != -1, that means the word exists in the hash table
		if (freq != -1) {
			System.out.println("\n\t*************List of URLS with frequency count*********\n");
			//obj is the location object of that word in the hash table
			 obj = QH.return_obj(word);

			 //Display the websites based on the word frequency
			 display_websites(obj, 1, linked_list_obj);

		}//end of if
		else {
			//If no such word is found in the hash table. Use edit distance
			//algorithm to suggest user with the closest words.
			System.out.println("!!!!!!! Word not found. !!!!!!!!!!!!");

			//QH.index.arr contains those index of array, where words are stored in
			//the hash table
			ArrayList arr = QH.index_arr;

			//Initializing Sequences where edit distance algorithm is implemented.
			Sequences o1 = new Sequences();

			// This is the hash table array.
			HashEntry[] arr2 = QH.array;

			//We set the minimum threshold to 2, which means any word that has an edit
			// distance of 2, we display the top 5 from those words list.
			int min_thres = 2;
			int l = 0;
			for (int k = 0; k < arr.size() ; k++) {
				//Get the words of the hashtable
				String hash_table_word = arr2[(int) arr.get(k)].element.toString();

				//Calculate the edit distance between the hashed table word and input word.
				int dr = o1.editDistance(word, hash_table_word);
				if (dr <= min_thres) {
					l ++;
					if (l == 1) System.out.println("*********Are you looking for this word?********");
					System.out.println(hash_table_word);
				}
				if (l == 5) break;

			}



		}//end of else
	}

	public static void browser_history_operation(LinkedListOperation linked_list_obj) {
		while (1 == 1) {
			//User can view, delete history by id and clear the entire history
			System.out.println("1.View history\n2.Delete history by id\n3.Clear the entire history\n4.Go Back");
			System.out.println("\tEnter your choice: ");
			Scanner in = new Scanner(System.in);
			int choice = in.nextInt();

			if (choice == 1) linked_list_obj.display_list();
			else if (choice == 2) {
				//Every history of URL is associated with an ID
				System.out.println("\tEnter the id of the URL you want to delete: ");
				Scanner in2 = new Scanner(System.in);
				int id = in2.nextInt();
				//Delete the particular history by id in the linked list.
				linked_list_obj.delete_node(id);

			}
			//Deletes all the elements from a linked list.
			else if (choice == 3) linked_list_obj.clear_list();
			else if (choice == 4) {return;}
			else {System.out.println("!!!!!!! PLEASE GIVE VALID INPUT CHOICE !!!!!!!!!!!!");}
		}
	}

	public static void main(String[] args) throws IOException, URISyntaxException {


		//Setting the Folder name and its path
		File folder = new File("W3C_Web_Pages/");

		//Instantiate HTMLToText which converts HTML to two different text files
		//The first text file is the plain text representation of the HTML file
		//The second text file contains just the useful part of speech recognition
		// like nouns, adjectives, which is going to be stored in the hash table.
		HTMLToText htmlToText = new HTMLToText();
		System.out.println("---------------- NLP in Progress -------------------");
		htmlToText.filesFolderOpr(folder);
		System.out.println("---------------- Completed -------------------");

		//Read the second files obtained from above step and store it in the hash table
		QuadraticProbingHashTable2 hash_table_obj = HashmapCreation.read_files();

		//Merge sorting the hash table values.
		HashmapCreation.sorting(hash_table_obj);

		//Initialize the LinkedListOperation class
		LinkedListOperation linked_list_obj = new LinkedListOperation();
		//The successful execution of above instructions means now the pre-processing
		//phase is now Completed.


		while (1==1) {
			//Display the user with the following options
			//User can search for a word and perform several browser history related oepartions.
			System.out.println("\n\t\t\t*********Welcome to our search engine*********");
			System.out.println("1.Search for a word\n2.Browser history related operations\n3.Exit");
			System.out.println("\tEnter your choice: ");

			Scanner in = new Scanner(System.in);
			try {
				 int choice = in.nextInt();

			if (choice == 1) {
				search_word(hash_table_obj, linked_list_obj);
			}//end of choice 1
			else if (choice == 2) {
				browser_history_operation(linked_list_obj);
			}//end of choice 2
			else if (choice == 3) {
				break;
			}
			else {
				System.out.println("!!!!!!! PLEASE GIVE VALID INPUT CHOICE !!!!!!!!!!!!");
			}//end of else
			}catch(InputMismatchException e) {
				System.out.println("!!!!!!! PLEASE GIVE VALID INPUT CHOICE !!!!!!!!!!!!");
			}catch(Exception e) {
				System.out.println("Excpeption Occured : "+e);
			}
		}//end of while loop
	}
}
