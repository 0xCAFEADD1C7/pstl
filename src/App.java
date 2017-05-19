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
		
		blbl = new BlobListener();
	}
	
	public static void main(String argv[]) {
		
		BlobListener blbl = new BlobListener();
		Game game = new Game(blbl);
		GraphicView view = new GraphicView(game);
	}
	
}