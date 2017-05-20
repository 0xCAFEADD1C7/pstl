import java.awt.Toolkit;

public class Settings {
	public static int getWinWidth() {
		return (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	}
	
	public static int getWinHeight() {
		return (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	}
	
	public static int getObjectiveSize() {
		return 60;
	}
}
