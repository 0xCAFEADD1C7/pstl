package interfaces;

import java.awt.geom.Point2D;

public interface Game {
	int getRobot1Score();
	int getRobot2Score();
	
	Robot getRobot1();
	Robot getRobot2();
	
	boolean hasRobots();
	void start();
	
	Point2D getObjective();
	
	void update();
}
