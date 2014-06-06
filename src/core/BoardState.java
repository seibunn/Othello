package core;

import java.util.ArrayList;

//TODO create setters/getters
public class BoardState {
	
	public ArrayList<Disk> diskList = new ArrayList<Disk>();
	public String turn;
	public boolean ongoing = false;
		
	public BoardState(ArrayList<Disk> d, String turn, boolean ongoing) {
		this.diskList = d;
		this.turn = turn;
		this.ongoing = ongoing;
	}
}
