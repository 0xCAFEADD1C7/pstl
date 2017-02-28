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
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import TUIO.*;

@SuppressWarnings("serial")
public class GraphicView extends JComponent {

	public Hashtable<Long,TuioBlob>   blobList   = new Hashtable<Long,TuioBlob>();
	private Game game;

	/* table_size should be the resolution of the camera */
	public static final int table_size = 720;
	
	public static int width, height;
	public boolean verbose = false;
	
	private final String moonFile = "moon.jpg";
	private final String starFile = "star.png";
	
	private Image bgImage;
	private Image objectiveImage;
	
	private Image loadImage(String path, int w, int h) {
		Image im = null;
		try {
			im = ImageIO.read(new File(path)).getScaledInstance(w, h, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			System.out.println("Impossible de charger "+path);
			e.printStackTrace();
		}
		return im;
	}
	
	public GraphicView(Game g) {
		super();
		
		game = g;
		
		int width  = Settings.getWinWidth();
		int height = Settings.getWinHeight();
		
		/* loading BG image */
		bgImage = loadImage(moonFile, width, height);
		objectiveImage = loadImage(starFile, Settings.getObjectiveSize()*2, Settings.getObjectiveSize()*2);
	}
	
	@Override
	public void setSize(int w, int h) {
		super.setSize(w,h);
		width = w;
		height = h;
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
		
		// render objective
		int xpos, ypos;
		xpos = game.getObj().x - Settings.getObjectiveSize()/2;
		ypos = game.getObj().y - Settings.getObjectiveSize()/2;
		g2.drawImage(objectiveImage, xpos, ypos, null);
		
//		 draw the blobs
		Enumeration<TuioBlob> blobs = blobList.elements();
		while (blobs.hasMoreElements()) {
			TuioBlob b = blobs.nextElement();
			if (b!=null) {
				int bx = b.getScreenX(width);
				int by = b.getScreenY(height);
				int bw = b.getScreenWidth(width);
				int bh = b.getScreenHeight(height);
				
				bx = (int) (b.getScreenX(Settings.getWinWidth()) + (b.getWidth()/2));
				by = (int) (b.getScreenY(Settings.getWinHeight()) + (b.getHeight()/2));
				
				g2.setPaint(Color.white);	
				g2.fillOval(bx,by,bw,bh);
				g2.setPaint(Color.blue);
				g2.fillArc(bx, by, 10, 10, 0, 360);
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
