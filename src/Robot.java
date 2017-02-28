import java.awt.Point;

import TUIO.TuioBlob;

public class Robot {
	public int x,y,vx,vy;
	public TuioBlob blb;
	
	public boolean isOnObjective(Point obj)
	{
//		System.out.println((obj.x-x)*(obj.x-x) + (obj.y-y)*(obj.y-y));
	    return ((obj.x-x)*(obj.x-x) + (obj.y-y)*(obj.y-y)) <= Settings.getObjectiveSize()*Settings.getObjectiveSize();
	}
}
