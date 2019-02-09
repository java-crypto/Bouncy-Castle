package step01;

public class Block {
	
	public int number;
	public int previousNumber;
	private String data;
	
	//Block Constructor.  
	public Block(int number, int previousNumber, String data) {
		this.number = number;
		this.previousNumber = previousNumber;
		this.data = data;
	}
}
