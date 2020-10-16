/*
This class HashmapCreation creates the quadratic hash table with keys being the
words and values being another hash table. The values hash table consist of URL
name along with its frequency for the key(words).
*/

import java.util.*;

import algorithms.QuadraticProbingHashTable2;
import algorithms.QuadraticProbingHashTable2.HashEntry;

import java.io.*;


public class HashmapCreation {

	public static QuadraticProbingHashTable2<String> QH = new QuadraticProbingHashTable2<>();
	public static QuadraticProbingHashTable2 read_files() throws FileNotFoundException {

		//Text3 contains the NLP tokenized certain part of speech words like nouns,adjectives etc.
		String folder_path = "W3C_Web_Pages/Text3/";
		File folder = new File(folder_path);

		// For all the files in the the folder Text3
		for (final File fileEntry : folder.listFiles()) {
			String path = folder_path + fileEntry.getName();
			File file = new File(path);
			Scanner scanner = new Scanner(file);

			//System.out.println("Finding in file " + path+"\n");

			//Fetch the next line
			while (scanner.hasNextLine()) {
				Scanner s2 = new Scanner(scanner.nextLine());

				//Fetch the words in that line.
				while (s2.hasNext()) {
		            String s = s2.next().toLowerCase();
								//Insert the word in the quadratic hash table with its filename.
		            QH.insert(s, fileEntry.getName());

		        }

			}//end of while; every file read complete checkpoint
			}


		return QH;
	}

	public static ArrayList merge_sort(Map<String, Integer> table){

	       //Transfer as List and sort it. Collection.sort by default uses merge
				 //sort when arraylist is being used.
	       ArrayList<Map.Entry<?, Integer>> sorted_list = new ArrayList(table.entrySet());
	       Collections.sort(sorted_list, new Comparator<Map.Entry<?, Integer>>(){

	         public int compare(Map.Entry<?, Integer> o1, Map.Entry<?, Integer> o2) {
	            return o2.getValue().compareTo(o1.getValue());
	        }});

	       return sorted_list;
	    }

	public static void sorting(QuadraticProbingHashTable2 obj) {

		//An array that is used for the implementation of hash table
		HashEntry[] a = obj.array;

		//Index arr contains the index of array table that has words stored.
		ArrayList b = obj.index_arr;

		for (int i = 0 ; i < b.size(); i++) {
			Map arr = a[(int) b.get(i)].table;
			ArrayList l =merge_sort(arr);
			//Setting the property of the value in the hash table to have display_file
			// array type that contains the list of files in the order of frequency
			// in the descending order
			a[(int) b.get(i)].display_file =  l;
		}

	}

}
