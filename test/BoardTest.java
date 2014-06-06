import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.*;


public class BoardTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	//(w) = disk to be placed, b,w = disks already on the board
	//(w),b,w
	@Test
	public void swapsOneLine_1() {
		
		Board b = new Board();
		b.initialize();
		b.setNeighbours();

		Disk diskToPlace = b.diskList.get(Board.index(5,3));
		Disk neighbour = b.diskList.get(Board.index(5,4));
		diskToPlace.setColor("white");
		ArrayList<Disk> Acc = new ArrayList<Disk>();
		ArrayList<Disk> ResultList = b.getReversibleDisks(diskToPlace,neighbour,Acc);
		assertTrue(ResultList.size() == 1);
		assertEquals(null, ResultList.get(0), b.diskList.get(Board.index(5,4)));
	}
	
	//
	@Test
	public void swapsOneLine_2() {
		
		Board b = new Board();
		b.initialize();
		b.setNeighbours();

		Disk diskToPlace = b.diskList.get(Board.index(5,3));
		Disk neighbour = b.diskList.get(Board.index(5,4));
		diskToPlace.setColor("black");
		ArrayList<Disk> Acc = new ArrayList<Disk>();
		ArrayList<Disk> ResultList = b.getReversibleDisks(diskToPlace,neighbour,Acc);
		assertTrue(ResultList.size() == 0);
	}
	
	@Test
	public void noPossiblePlacement() {
		Board b = new Board();
		b.setNeighbours();
		b.diskList.get(Board.index(1,1)).setColor("black");
		b.diskList.get(Board.index(2,2)).setColor("white");
		b.setActivePlayer("black");
		b.changeTurn();
		//Should still be the black player
		assertTrue(b.getActivePlayer() == "black");
	}
	
	

}
