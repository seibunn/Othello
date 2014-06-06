package core;
import java.util.ArrayList;

public class Board implements Observable{
	
	public ArrayList<Disk> diskList = new ArrayList<Disk>();
	private String turn = "white";
	private boolean ongoing = true;
	private OthelloScore score = new OthelloScore();
	private ArrayList<ScoreObserver> scoreObservers = new ArrayList<ScoreObserver>();
	private ArrayList<StateObserver> stateObservers = new ArrayList<StateObserver>();
	
	public Board() {
		for(int i=0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {	
				Disk d = new Disk(i,j);
				diskList.add(index(i,j),d);
			}
		}		
	}	
	
	public void initialize() {
		diskList.get(index(4,4)).setColor("white");
		diskList.get(index(4,5)).setColor("black");
		diskList.get(index(5,4)).setColor("black");
		diskList.get(index(5,5)).setColor("white");		
	}
		
	public static int index(int i,int j) {
		return i*10+j;
	}
	
	public void setNeighbours() {
		for(int i=1; i < 9; i++) {
			for(int j = 1; j < 9; j++) {
				Disk[] neighbours = new Disk[8];
				neighbours[0] = diskList.get(index(i-1,j-1));
				neighbours[1] = diskList.get(index(i-1,j));
				neighbours[2] = diskList.get(index(i-1,j+1));
				neighbours[3] = diskList.get(index(i,j+1));
				neighbours[4] = diskList.get(index(i+1,j+1));
				neighbours[5] = diskList.get(index(i+1,j));
				neighbours[6] = diskList.get(index(i+1,j-1));
				neighbours[7] = diskList.get(index(i,j-1));
				diskList.get(index(i,j)).setNeighbours(neighbours);
			}
		}
	}
	
	public void print() {
		for(int i = 0; i < 10; i++)	{
			for(int j = 0; j < 10; j++)	{
				diskList.get(index(i,j)).print();
			}	
			System.out.println();
		}
	}
	
	public void putDisk(String color, int index) {
		if(!diskList.get(index).isEmpty()) {
			System.out.println("Occupied moron!");
			return; 
		}
		
		Disk diskToPlace = diskList.get(index);
		diskToPlace.setColor(color);
		ArrayList<Disk> disksToSwap = new ArrayList<Disk>();
		
		//Check in all directions for disks to swap
		for(int k = 0; k < Disk.nrOfNeighbours; k++) {
			Disk neighbour = diskList.get(index).getNeighbour(k);
			ArrayList<Disk> Acc = new ArrayList<Disk>();
			disksToSwap.addAll(getReversibleDisks(diskToPlace,neighbour,Acc));
		}
		if(disksToSwap.size() != 0) {
			for(int k = 0; k < disksToSwap.size(); k++) {
				disksToSwap.get(k).setColor(color);
			}
			updateScore();
			changeTurn();
			notifyScoreObservers();
			notifyStateObservers();
		}
		else {
			System.out.println("Not possible to put disk at: " + index);
			diskToPlace.setColor("empty");
		}
	}
	
	private void updateScore() {
		score.white = 0;
		score.black = 0;
		for(int i = 0; i < diskList.size(); ++i) {
			if(diskList.get(i).color() == "w")
				score.white++;
			if(diskList.get(i).color() == "b")
				score.black++;
		}
	}
	
	//Checks one direction.
	public ArrayList<Disk> getReversibleDisks(Disk diskToPlace, Disk diskToCheck, ArrayList<Disk> acc) {
		if(diskToCheck.isEmpty()) {
			acc.clear();
			return acc;
		}
		if(Disk.oppositeColor(diskToPlace,diskToCheck)) {
			acc.add(diskToCheck);
			Disk nextDiskToCheck = diskList.get(diskToCheck.findNext(diskToPlace));
			return getReversibleDisks(diskToPlace,nextDiskToCheck,acc);
		}
		return acc;
	}
	
	public String getActivePlayer() {
		return turn;
	}
	
	public void setActivePlayer(String color) {
		turn = color;
	}
	
	public String getPassivePlayer() {
		return (turn == "black") ? "white" : "black";  
	}
	
	private boolean placementPossible(String color) {
		ArrayList<Disk> acc = new ArrayList<Disk>();
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				Disk diskToPlace = diskList.get(index(i+1,j+1));
				if(diskToPlace.isEmpty()) {
					for(int k = 0; k < Disk.nrOfNeighbours; k++) {
						Disk neighbour = diskList.get(index(i+1,j+1)).getNeighbour(k);
						diskToPlace.setColor(color); //Ugly
						getReversibleDisks(diskToPlace,neighbour,acc);
						diskToPlace.setColor("empty");
						if(acc.size() != 0)
							return true;
					}
				}
			}
		}
		return false;
	}
	
	public void changeTurn() {
		if(placementPossible(getPassivePlayer())) {
			turn = getPassivePlayer();
		}
		else if(placementPossible(getActivePlayer())) {
			turn = getActivePlayer();
		}
		
		//No one can (full board is included here)
		else {
			ongoing = false;
		}		
	}
	
	@Override
	public void registerScoreObserver(ScoreObserver o) {
		scoreObservers.add(o);
	}

	@Override
	public void removeScoreObserver(ScoreObserver o) {
		scoreObservers.remove(o);
	}

	@Override
	public void notifyScoreObservers() {
		for(int i = 0; i < scoreObservers.size(); ++i) {
			scoreObservers.get(i).updateScore(score);
		}		
	}
	
	@Override
	public void registerStateObserver(StateObserver o) {
		stateObservers.add(o);
		notifyStateObservers();
	}

	@Override
	public void removeStateObserver(StateObserver o) {
		stateObservers.remove(o);
	}

	@Override
	public void notifyStateObservers() {
		for(int i = 0; i < stateObservers.size(); ++i) {
			stateObservers.get(i).updateState(new BoardState(diskList,turn,ongoing));
		}		
	}
}