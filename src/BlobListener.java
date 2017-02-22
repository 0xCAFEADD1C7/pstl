import TUIO.*;

public class BlobListener implements TuioListener {	
	
	private Game game;
	
	public BlobListener(Game g) {
		game = g;
	}

	public void addTuioBlob(TuioBlob tblb) {
		if(game.getView() == null)
				//.put(tblb.getSessionID(), tblb);
		System.out.println("received");
	}
	
	public void updateTuioBlob(TuioBlob tblb) {
		game.getView().blobList.put(tblb.getSessionID(), tblb);
		System.out.println("udate");
	}
	
	public void removeTuioBlob(TuioBlob tblb) {
		game.getView().blobList.remove(tblb.getSessionID());
		System.out.println("removve");	
	}

	@Override
	public void refresh(TuioTime ftime) {
		game.getView().repaint();
	}
	
	public void addTuioObject(TuioObject tobj) {}
	public void updateTuioObject(TuioObject tobj) {}	
	public void removeTuioObject(TuioObject tobj) {}
	public void addTuioCursor(TuioCursor tcur) {}
	public void updateTuioCursor(TuioCursor tcur) {}	
	public void removeTuioCursor(TuioCursor tcur) {}
}
