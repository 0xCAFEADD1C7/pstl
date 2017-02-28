import java.awt.Point;

public class Game {
	private Robot[] robots;
	private Point obj;
	
	private GraphicView view;
	
	/* génére un objectif à une position aléatoire */
	public void genRandomObjective() {
		obj = new Point((int)(Math.random()*Settings.getWinWidth()), (int)(Math.random()*Settings.getWinHeight()));
	}
	
	public Game() {
		genRandomObjective();
	}
	
	public Robot[] getRobots() {
		return robots;
	}

	public void setRobots(Robot[] robots) {
		this.robots = robots;
	}

	public Point getObj() {
		return obj;
	}

	public void setObj(Point obj) {
		this.obj = obj;
	}

	public GraphicView getView() {
		return view;
	}

	public void setView(GraphicView view) {
		this.view = view;
	}	
}
