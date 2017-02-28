import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import TUIO.*;

public class App  {
	
	private GraphicView view;
	private BlobListener blbl;
	private Game game;
	private JFrame frame;
	
	public App() {
		game = new Game();
		view = new GraphicView(game);
		blbl = new BlobListener(game);
		game.setView(view);
		
		setupWindow();
		showWindow();
		
		TuioClient client = new TuioClient();
		 
		client.addTuioListener(blbl);
		client.connect();
		
		if(!client.isConnected()) { /* erreur */
			JOptionPane.showMessageDialog(null, "Impossible d'écouter sur le port 3333!!", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void setupWindow() {
	
		frame = new JFrame();
		frame.add(view);

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
		int width  = Settings.getWinWidth();
		int height = Settings.getWinHeight();
		view.setSize(width,height);
		
		frame.pack();
		Insets insets = frame.getInsets();			
		frame.setSize(width,height +insets.top);
		frame.setCursor(Cursor.getDefaultCursor());
		
		frame.setVisible(true);
		frame.repaint();
	}
	
	public static void main(String argv[]) {
		
		@SuppressWarnings("unused")
		App demo = new App();
	}
	
}