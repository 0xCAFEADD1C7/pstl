import interfaces.Robot;


public class RobotImpl implements Robot {
	
	private int posX, posY, width, height;
	private double angle;
	
	public RobotImpl() {
		
	}

	@Override
	public int posX() {
		return posX;
	}

	@Override
	public int posY() {
		return posY;
	}

	@Override
	public int width() {
		return width;
	}

	@Override
	public int height() {
		return height;
	}

	@Override
	public double angle() {
		return angle;
	}

	@Override
	public void set(int x, int y, int w, int h, double a) {
		posX = x;
		posY = y;
		width = w;
		height = h;
		angle = a;
	}
	
}
