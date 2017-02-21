import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import TUIO.*;

public class App  {

	private final int window_width  = 640;
	private final int window_height = 480;

	private boolean fullscreen = false;
	
	private GraphicView demo;
	private JFrame frame;
	private Cursor invisibleCursor;
	
	public App() {
		demo = new GraphicView();
		invisibleCursor = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "invisible cursor");
		setupWindow();
		showWindow();
	}
	
	public TuioListener getTuioListener() {
		return demo;
	}
	
	public void setupWindow() {
	
		frame = new JFrame();
		frame.add(demo);

		frame.setTitle("PSTL app");
		frame.setResizable(false);

		/* exit */
		frame.addWindowListener( new WindowAdapter() { public void windowClosing(WindowEvent evt) {
				System.exit(0);
			}});
	}
	
	public void destroyWindow() {
	
		frame.setVisible(false);
		frame = null;
	}
	
	public void showWindow() {
		int width  = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		demo.setSize(width,height);
		
		frame.pack();
		Insets insets = frame.getInsets();			
		frame.setSize(width,height +insets.top);
		frame.setCursor(Cursor.getDefaultCursor());
		
		frame.setVisible(true);
		frame.repaint();
	}
	
	public static void main(String argv[]) {
		
		App demo = new App();
		TuioClient client = new TuioClient();
 
		client.addTuioListener(demo.getTuioListener());
		client.connect();
	}
	
}