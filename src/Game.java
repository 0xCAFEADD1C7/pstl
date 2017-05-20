import interfaces.BlobListener;
import interfaces.Robot;

import java.awt.Point;
import java.awt.geom.Point2D;

public class Game implements interfaces.Game {
	private BlobListener bl;
	
	private Point obj;
	
	private int score1, score2;
	private Robot rob1, rob2;

	private boolean started;
	
	
	/* génére un objectif à une position aléatoire */
	public void genObjective() {
		obj = new Point((int)(Math.random()*Settings.getWinWidth()), (int)(Math.random()*Settings.getWinHeight()));
	}
	
	public Game(BlobListener b) {
		genObjective();
		bl = b;
	}

	@Override
	public int getRobot1Score() {
		return score1;
	}

	@Override
	public int getRobot2Score() {
		return score2;
	}

	@Override
	public Robot getRobot1() {
		Robot r = new RobotImpl();
		r.set(1000,500,100,100,Math.PI/3);
		return r;
	}

	@Override
	public Robot getRobot2() {
		Robot r = new RobotImpl();
		r.set(200,200,100,100,Math.PI/3);
		return r;
	}

	@Override
	public Point2D getObjective() {
		return obj;
	}

	@Override
	public void update() {
		
		rob1 = bl.getRobot1();
		rob2 = bl.getRobot2();
		
		if(hasRobots()) {
					
			boolean r1OnPoint = robotOnPoint(rob1, obj);
			boolean r2OnPoint = robotOnPoint(rob2, obj);
			
			if(r1OnPoint || r2OnPoint) {
				if(r1OnPoint) {
					score1++;
				} else {
					score2++;
				}
				genObjective();
			}
		}
	}
	
	private boolean robotOnPoint(Robot r, Point p) {
		return false; // TODO
	}

	@Override
	public boolean hasRobots() {
		return rob1 != null && rob2 != null;
	}

	@Override
	public void start() {
		started = true;
		bl.captureRobots();
	}
}
