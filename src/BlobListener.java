import javax.swing.JOptionPane;

import interfaces.Robot;
import TUIO.*;

public class BlobListener implements TuioListener, interfaces.BlobListener {	
	
	private Robot r1, r2;
	private long lur1, lur2; // dernier timestamp de mise à jour de la position des robots
//	private 

	public void addTuioBlob(TuioBlob tblb) {
		// nouveau blob détécté
		if (r1 == null) {
			r1 = new RobotImpl();
			r1.set(
				tblb.getScreenX(Settings.getWinWidth()),
				tblb.getScreenY(Settings.getWinHeight()),
				tblb.getScreenWidth(Settings.getWinWidth()),
				tblb.getScreenHeight(Settings.getWinHeight()),
				tblb.getAngle());
		}
		
		if (r2 == null) {
			r2 = new RobotImpl();
			r2.set(
				tblb.getScreenX(Settings.getWinWidth()),
				tblb.getScreenY(Settings.getWinHeight()),
				tblb.getScreenWidth(Settings.getWinWidth()),
				tblb.getScreenHeight(Settings.getWinHeight()),
				tblb.getAngle());
		}
	}
	
	public void updateTuioBlob(TuioBlob tblb) {
		// màj d'un blob
	}
	
	public void removeTuioBlob(TuioBlob tblb) {
		// blob perdu de vue
		
	}

	@Override
	public Robot getRobot1() {
		return r1;
	}

	@Override
	public Robot getRobot2() {
		return r2;
	}
	
	public BlobListener() {
		TuioClient client = new TuioClient(); 
		client.addTuioListener(this);
		client.connect();
		
		if(!client.isConnected()) { /* erreur */
			JOptionPane.showMessageDialog(null, "Impossible d'écouter sur le port 3333!!", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@Override
	public void refresh(TuioTime ftime) {} //TODO ?	
	public void addTuioObject(TuioObject tobj) {}
	public void updateTuioObject(TuioObject tobj) {}	
	public void removeTuioObject(TuioObject tobj) {}
	public void addTuioCursor(TuioCursor tcur) {}
	public void updateTuioCursor(TuioCursor tcur) {}	
	public void removeTuioCursor(TuioCursor tcur) {}
}
