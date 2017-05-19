package interfaces;

public interface Robot {
	int posX();
	int posY();
	int width();
	int height();
	double angle();
	
	void set(int x, int y, int w, int h, double a);
}
