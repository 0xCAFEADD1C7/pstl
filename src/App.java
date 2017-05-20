import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application {	
	public static void main(String argv[]) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		BlobListener blbl = new BlobListener();
		
		Game game = new Game(blbl);
		
		GraphicView view = new GraphicView(game);
		view.init(stage);
		
		new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				game.update();
				view.draw();
			}
		}.start();
		
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent event) {
		    	System.out.println("Fin du programme...");
		    	System.exit(0);
		    }
		});
	}
	
}