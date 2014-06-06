package score;

import core.OthelloScore;
import core.ScoreObserver;

public class ConsoleScore implements ScoreObserver {
	
	public ConsoleScore() {}
	
	@Override
	public void updateScore(OthelloScore s) {
		System.out.println("White - Black: " + s.white + " - " + s.black);
	}

}
