import java.util.ArrayList;

import javax.swing.JOptionPane;

import interfaces.Robot;
import TUIO.*;

public class BlobListener implements TuioListener, interfaces.BlobListener {	
	
	private Robot r1, r2;
	private long lur1, lur2; // dernier timestamp de mise à jour de la position des robots
	private int lid1, lid2;
	
	boolean capture = false;
	ArrayList<TuioBlob> blobs = new ArrayList<>();
	
	@Override
	public void captureRobots() {
		capture = true;
	}
	
	private void endCapture() {
		capture = false;
		
		r1 = new RobotImpl();
		r2 = new RobotImpl();
		assign(blobs.get(0), 1);
		assign(blobs.get(1), 2);
	}
	
	private void assign(TuioBlob tblb, int r) {
		if (r == 1) {
			if(r1 == null) r1 = new RobotImpl();
			r1.set(
					tblb.getScreenX(Settings.getWinWidth()),
					tblb.getScreenY(Settings.getWinHeight()),
					tblb.getScreenWidth(Settings.getWinWidth()),
					tblb.getScreenHeight(Settings.getWinHeight()),
					tblb.getAngle());
			lur1 = System.currentTimeMillis();
			lid1 = tblb.getBlobID();
		} else if(r == 2) {
			if(r2 == null) r2 = new RobotImpl();
			r2.set(
					tblb.getScreenX(Settings.getWinWidth()),
					tblb.getScreenY(Settings.getWinHeight()),
					tblb.getScreenWidth(Settings.getWinWidth()),
					tblb.getScreenHeight(Settings.getWinHeight()),
					tblb.getAngle());
			lur2 = System.currentTimeMillis();
			lid2 = tblb.getBlobID();
		}
	}

	public void addTuioBlob(TuioBlob tblb) {
//		tblb.getScreenX(Settings.getWinWidth()),
//		tblb.getScreenY(Settings.getWinHeight()),
//		tblb.getScreenWidth(Settings.getWinWidth()),
//		tblb.getScreenHeight(Settings.getWinHeight()),
//		tblb.getAngle();
		blobs.add(tblb);
		
		/* check if capture is ok */
		if (capture && blobs.size() == 2) {
			endCapture();
		}
	}
	
	public void updateTuioBlob(TuioBlob tblb) {
		if(tblb.getBlobID() == lid1) {
			assign(tblb, 1);
		} else if(tblb.getBlobID() == lid2) {
			assign(tblb, 2);
		}
	}
	
	public void removeTuioBlob(TuioBlob tblb) {
		/* update blob list */
		for(int i = 0; i < blobs.size(); i++) {
			TuioBlob b = blobs.get(i);
			if(b.getBlobID() == tblb.getBlobID()) {
				blobs.remove(i);
				break;
			}
		}
		
		/* check if capture is ok */
		if (capture && blobs.size() == 2) {
			endCapture();
		}
	
		
		/* check if lost blob isn't one of a robot */
		//TODO
		
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
		
		System.out.println("Ecoute TUIO sur le port 3333...");
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
