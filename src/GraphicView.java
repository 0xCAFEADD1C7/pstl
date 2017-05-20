import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import interfaces.Robot;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GraphicView {
	private Game game;
	private GraphicsContext gc;
	private Image bg, obj;
	
	public void init(Stage stage) {
		stage.setTitle( "PSTL" );
		
		System.out.println("Init du stage...");
		
		try {
			bg = new Image(new FileInputStream(new File("moon.jpg")));
			obj = new Image(new FileInputStream(new File("star.png")));
		} catch(IOException t) {
			JOptionPane.showMessageDialog(null, "Impossible de charger les images : "+t.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			System.out.println("Fin du programme...");
			System.exit(1);
		}

        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);  
        
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                	case SPACE :
                		System.out.println("Lancement de la détection des robots !!");
                		game.start();
                	default:
                }
            }
        });
        

        Canvas canvas = new Canvas( Settings.getWinWidth(), Settings.getWinHeight() );
        root.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();
        gc.setFont(new Font(30));

        stage.show();  
	}
	
	public GraphicView(Game g) {
		game = g;
	}
	
	public GraphicView() {
		
	}
	
	public void setGame(Game g) {
		game = g;
	}

	public void draw() {
		// draw BG
        gc.drawImage(bg, 0, 0, Settings.getWinWidth(), Settings.getWinHeight());
        
        // draw obj
        Point2D o = game.getObjective();
        int os = Settings.getObjectiveSize();
        gc.drawImage(obj, o.getX()-(os/2), o.getY()-(os/2), os, os);
        
        // si on a fait la détection des robots
		if (game.hasRobots()) {
			
	        
	        // draw robots and their score
	        Robot r1 = game.getRobot1(), r2 = game.getRobot2();
	        gc.setFill(Color.RED);
	        drawRobot(r1, gc);
	        gc.setFill(Color.YELLOW);
	        gc.fillText(((Integer) game.getRobot1Score()).toString(), r1.posX(), r1.posY());
	        
	        gc.setFill(Color.YELLOW);
	        drawRobot(r2, gc);
	        gc.setFill(Color.RED);
	        gc.fillText(((Integer) game.getRobot2Score()).toString(), r2.posX(), r2.posY());
		} else { /* les robots ne sont pas encore détéctés */
			gc.setFill(Color.WHITE);
	        gc.fillText("Appuyez sur espace pour lancer la détéction des robots", Settings.getWinWidth()/2-400, Settings.getWinHeight()/2);
		}
	}
	
	private void drawRobot(Robot r, GraphicsContext g) {
		double  x = r.posX(), 
				y = r.posY(),
				a = r.angle(),
				w = r.width()/2,
				h = r.height()/2;
		
		double vwx = w*Math.cos(a),
		vwy = w*Math.sin(a),
		vhx = - h*Math.sin(a),
		vhy = h*Math.cos(a);
		
		double[] ptsX = {
			    x+vwx+vhx, 
			    x-vwx+vhx,
			    x-vwx-vhx,
			    x+vwx-vhx
			   };
		double[] ptsY = {
			    y+vwy+vhy, 
			    y-vwy+vhy,
			    y-vwy-vhy,
			    y+vwy-vhy
			   };
		
		g.fillPolygon(ptsX, ptsY, 4);
	}
	
}
