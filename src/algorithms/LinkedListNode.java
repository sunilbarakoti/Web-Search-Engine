package algorithms;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.sql.Timestamp;

/*
The class LinkedListNode is used to store the browser history.
Any addition of the node will have time compleixty O(1)
Arrays could have been used as well, but deletion of elments from an array
requires memory shift. But in linked list, its not the case.
*/
public class LinkedListNode{

		public String file_name;
		public Timestamp timestamp;
		public int id;
		private static final AtomicInteger count = new AtomicInteger(0);

		LinkedListNode(String file_name, Timestamp timestamp){
			//Storing the URL(file_name), current timestamp and auto-incremental id.
			this.file_name= file_name;
			this.timestamp = timestamp;
			this.id = count.incrementAndGet();
		}
		public  String getFilename() {
			return this.file_name;
		}
		public Timestamp getTimestamp() {
			return this.timestamp;

		}

}
