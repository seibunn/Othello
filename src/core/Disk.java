package core;
public class Disk{

	private Disk[] neighbours = new Disk[8];
	private int i,j;
	private OthelloColor color = OthelloColor.EMPTY;
	static final int nrOfNeighbours = 8;
	
	Disk(int i, int j) {
		this.i = i;
		this.j = j;
	}
	
	Disk(int i, int j, OthelloColor color) {
		this.i = i;
		this.j = j;
		this.color = color;		
	}
	
	public void setPos(int i, int j) {
		this.i = i;
		this.j = j;
	}
	
	public void setColor(String c) {
		if(c == "white") {
			color = OthelloColor.WHITE;
		}
		else if(c == "black") {
			color = OthelloColor.BLACK;
		}
		else {
			color = OthelloColor.EMPTY;
		}
	}
	
	public boolean isEmpty() {
		return color == OthelloColor.EMPTY;
	}
	
	public void setNeighbours(Disk[] n) {
		neighbours = n;
	}
	
	public void printNeighbours() {
		for(int i = 0; i < 8; i++)
			System.out.print(neighbours[i].color + ", ");
	}
	
	public Disk getNeighbour(int i) {
		return neighbours[i];
	}
	
	public static boolean oppositeColor(Disk d1, Disk d2) {
		if(d1.color == d2.color)
			return false;
		else 
			return true;
	}
	
	public int findNext(Disk startDisk) {
		int iDiff = this.i - startDisk.i;
		int jDiff = this.j - startDisk.j;
		int i = this.i + (iDiff == 0 ? iDiff : (iDiff/Math.abs(iDiff)));
		int j = this.j + (jDiff == 0 ? jDiff : (jDiff/Math.abs(jDiff)));
		return 10*i+j;
	}
	
	public String color() {
		String s; 
		if(color == OthelloColor.BLACK)
			s = "b";
		else if(color == OthelloColor.WHITE)
			s = "w";
		else 
			s = "";
		return s;
	}
	
	public void print() {
		if(color == OthelloColor.WHITE)
			System.out.print("w");
		else if(color == OthelloColor.EMPTY)
			System.out.print(0);
		else
			System.out.print("b");
	}
}