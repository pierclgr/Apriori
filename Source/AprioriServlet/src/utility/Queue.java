package utility;
/**
 * <p>Title: Queue</p>
 * <p>Description: A queue.</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: This class represents a queue.</p>
 * @author Pier
 * @version 1.0
 */
public class Queue<T> {

		private Record begin = null;

		private Record end = null;
		/**
		 * <p>Title: Record</p>
		 * <p>Description: A record.</p>
		 * <p>Copyright: Copyright (c) 2017</p>
		 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
		 * <p>Class description: This class is used represents a record (current element and his successor).</p>
		 * @author Pier
		 * @version 1.0
		 */
		private class Record {

	 		private Object elem;

	 		private Record next;
	 		/**
	 		 * This constructor method sets the current element of the record and sets to null its successor.
	 		 * @param e Current element.
	 		 */
			private Record(Object e) {
				this.elem = e; 
				this.next = null;
			}
		}
		/**
		 * This method checks if the queue is empty.
		 * @return Boolean representing the result of the check.
		 */
		public boolean isEmpty() {
			return this.begin == null;
		}
		/**
		 * This method adds an element to the queue.
		 * @param e Element to add to the queue.
		 */
		public void enqueue(Object e) {
			if (this.isEmpty())
				this.begin = this.end = new Record(e);
			else {
				this.end.next = new Record(e);
				this.end = this.end.next;
			}
		}
		/**
		 * This method returns the first element of the queue.
		 * @return First element of the queue.
		 * @throws EmptyQueueException Throws an EmptyQueueException in case the queue is empty.
		 */
		public Object first() throws EmptyQueueException{
			if(this.begin==null){
				throw new EmptyQueueException("Empty queue");
			}
			return this.begin.elem;
		}
		/**
		 * This method removes the first element of the queue.
		 * @throws EmptyQueueException Throws an EmptyQueueException in case the queue is empty.
		 */
		public void dequeue() throws EmptyQueueException{
			if(this.begin==this.end){
				if(this.begin==null)
					throw new EmptyQueueException("Empty queue");
				else
					this.begin=this.end=null;
			}
			else{
				begin=begin.next;
			}
		}
	}