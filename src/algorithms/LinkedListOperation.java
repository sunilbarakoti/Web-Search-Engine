package algorithms;
import java.util.LinkedList;
import java.sql.Timestamp;

public class LinkedListOperation {
	public LinkedList linked_list_object;

	//Initialize Linked list class.
	public LinkedListOperation(){

		LinkedList<LinkedListNode> linked_list_object = new LinkedList<LinkedListNode>();
		this.linked_list_object = linked_list_object;
	}


	public void add_node_beginning(String url_name) {
		//Get the current timestamp
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		//Create a LinkedListNode object
		LinkedListNode obj = new LinkedListNode(url_name,timestamp);
		System.out.println(obj.file_name + obj.timestamp);
		//Add the object to the linked list object.
		linked_list_object.addFirst(obj);

	}

	public void delete_node(int url_id) {

		boolean flag = false;
		//Iterate through the linked list
		for (int i = 0; i < linked_list_object.size(); i++) {
        	int id = ((LinkedListNode) linked_list_object.get(i)).id;
					//Match the id while iterating through the list.
        	if ( url_id == id ) {
						//Remove the node from the linked list containing the particular id.
        		linked_list_object.remove(i);
        		flag = true;
        		break;
        	}

        }
		if (flag) System.out.println("\n*****URL successfully deleted******\n");
    	if (!flag) System.out.println("\n!!!!!!!!!!Id not found!!!!!!!!!!\n");
	}

	public void clear_list() {
		//Delete all the elements from the list, but not the linked list
		System.out.println("\n****Browser History has been cleared*****\n");
		linked_list_object.clear();
	}

	public void display_list() {

		if (linked_list_object.size() == 0) {
			System.out.println("\n****No Browser History*****\n");
			return ;
		}
		System.out.println("\n****Browser's History*****\n");
		//Ireate through the linked list and display all the elements.
		for (int i = 0; i < linked_list_object.size(); i++) {
			System.out.print(((LinkedListNode) linked_list_object.get(i)).id + ". ");
        	System.out.print("\t" + ((LinkedListNode) linked_list_object.get(i)).getFilename());
        	System.out.println("\t"+((LinkedListNode) linked_list_object.get(i)).getTimestamp());
        }
		System.out.println("\n");
	}

	}
