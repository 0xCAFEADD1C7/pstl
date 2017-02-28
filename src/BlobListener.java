import TUIO.*;

public class BlobListener implements TuioListener {	
	
	private Game game;
	
	public BlobListener(Game g) {
		game = g;
	}
	
	/** TODO : Move this **/
	public void checkObjOk(TuioBlob tblb) {
		Robot r = new Robot();
		r.x = (int) (tblb.getScreenX(Settings.getWinWidth()) + (tblb.getWidth()/2));
		r.y = (int) (tblb.getScreenY(Settings.getWinHeight()) + (tblb.getHeight()/2));
		
		if(r.isOnObjective(game.getObj())) {
			game.genRandomObjective();
		}
	}

	public void addTuioBlob(TuioBlob tblb) {
		game.getView().blobList.put(tblb.getSessionID(), tblb);
//		System.out.println("received");
		
		checkObjOk(tblb);
	}
	
	public void updateTuioBlob(TuioBlob tblb) {
		game.getView().blobList.put(tblb.getSessionID(), tblb);
//		System.out.println("udate");
		
		checkObjOk(tblb);
	}
	
	public void removeTuioBlob(TuioBlob tblb) {
		game.getView().blobList.remove(tblb.getSessionID());
//		System.out.println("removve");	
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
