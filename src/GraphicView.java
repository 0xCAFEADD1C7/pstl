/*
 TUIO Java GUI Demo
 Copyright (c) 2005-2016 Martin Kaltenbrunner <martin@tuio.org>
 
 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files
 (the "Software"), to deal in the Software without restriction,
 including without limitation the rights to use, copy, modify, merge,
 publish, distribute, sublicense, and/or sell copies of the Software,
 and to permit persons to whom the Software is furnished to do so,
 subject to the following conditions:
 
 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

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

@SuppressWarnings("serial")
public class GraphicView extends JComponent implements TuioListener {

	private Hashtable<Long,TuioBlob>   blobList   = new Hashtable<Long,TuioBlob>();

	/* table_size should be the resolution of the camera */
	public static final int table_size = 720;
	
	public static int width, height;
	private float scale = 1.0f;
	public boolean verbose = false;
	
	private Image bgImage;
	private Image objectiveImage;
	
	public GraphicView() {
		super();
		
		int width  = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		
		/* loading BG image */
		BufferedImage img = null;
		final String moonFile = "moon.jpg";
		try {
			bgImage = ImageIO.read(new File(moonFile)).getScaledInstance(width, height, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			System.out.println("Impossible de charger "+moonFile);
			e.printStackTrace();
		}
	}
	
	@Override
	public void setSize(int w, int h) {
		super.setSize(w,h);
		width = w;
		height = h;
		scale  = height/(float)GraphicView.table_size;	
	}
	
	public void addTuioObject(TuioObject tobj) {}

	public void updateTuioObject(TuioObject tobj) {}
	
	public void removeTuioObject(TuioObject tobj) {}
	public void addTuioCursor(TuioCursor tcur) {}

	public void updateTuioCursor(TuioCursor tcur) {}
	
	public void removeTuioCursor(TuioCursor tcur) {}

	public void addTuioBlob(TuioBlob tblb) {
		blobList.put(tblb.getSessionID(),tblb);
		
		if (verbose) 
			System.out.println("add blb "+tblb.getBlobID()+" ("+tblb.getSessionID()+") "+tblb.getX()+" "+tblb.getY()+" "+tblb.getAngle());	
	}
	
	public void updateTuioBlob(TuioBlob tblb) {
		
		if (verbose) 
			System.out.println("set blb "+tblb.getBlobID()+" ("+tblb.getSessionID()+") "+tblb.getX()+" "+tblb.getY()+" "+tblb.getAngle()+" "+tblb.getMotionSpeed()+" "+tblb.getRotationSpeed()+" "+tblb.getMotionAccel()+" "+tblb.getRotationAccel()); 	
	}
	
	public void removeTuioBlob(TuioBlob tblb) {
		blobList.remove(tblb.getSessionID());
		
		if (verbose) 
			System.out.println("del blb "+tblb.getBlobID()+" ("+tblb.getSessionID()+")");	
	}
	
	
	public void refresh(TuioTime frameTime) {
		repaint();
	}
	
	public void paint(Graphics g) {
		update(g);
	}

	public void update(Graphics g) {
	
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		// render BG
		g2.drawImage(bgImage, 0,0,null);

		
//		 draw the blobs
		Enumeration<TuioBlob> blobs = blobList.elements();
		while (blobs.hasMoreElements()) {
			TuioBlob b = blobs.nextElement();
			if (b!=null) {
				int bx = b.getScreenX(width);
				int by = b.getScreenY(height);
				int bw = b.getScreenWidth(width);
				int bh = b.getScreenHeight(height);
				
				g2.setPaint(Color.white);	
				g2.fillOval(bx,by,bw,bh);
			}
		}
		
		/*Enumeration<TuioBlob> blobs = blobList.elements();
		while (blobs.hasMoreElements()) {
			TuioBlob tblb = blobs.nextElement();
			if (tblb!=null) {
			
				float bx = tblb.getScreenX(width);
				float by = tblb.getScreenY(height);
				float bw = tblb.getScreenWidth(width);
				float bh = tblb.getScreenHeight(height);
				Ellipse2D ellipse = new Ellipse2D.Float(-bw/2.0f,-bh/2.0f,bw,bh);
		
				AffineTransform transform = new AffineTransform();
				transform.rotate(tblb.getAngle(),bx,by);
				transform.translate(bx,by);
				
				g2.setPaint(Color.white);
				g2.fill(transform.createTransformedShape(ellipse));
				g2.setPaint(Color.green);
				g2.drawString(tblb.getBlobID()+"",bx-10,by);
			}
		}*/
	}
}
