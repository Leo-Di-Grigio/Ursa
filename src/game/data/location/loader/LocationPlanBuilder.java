package game.data.location.loader;

import game.data.objects.Obj;
import game.data.sql.Database;
import game.data.sql.properties.ObjectProperties;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import com.owlengine.tools.Log;

final class LocationPlanBuilder {

	protected static void buildPlan(TreeMap<Integer, HashSet<Obj>> draw, String filePath) {
		// calculate level image size
		int minX = 0, minY = 0, maxX = 0, maxY = 0, imgSizeX = 0, imgSizeY = 0;
		
		for(HashSet<Obj> layer: draw.values()){
			for(Obj object: layer){
				int objX = (int)object.x();
				int objY = (int)object.y();
				int objSizeX = (int)(object.sizeX()/2);
				int objSizeY = (int)(object.sizeY()/2);
				
				int objMinX = objX - objSizeX;
				int objMinY = objY - objSizeY;
				int objMaxX = objX + objSizeX;
				int objMaxY = objY + objSizeY;
				
				if(objMinX < minX){ minX = objMinX; }
				if(objMinY < minY){ minY = objMinY; }
				if(objMaxX > maxX){ maxX = objMaxX;	}
				if(objMaxY > maxY){ maxY = objMaxY; }	
			}
		}
		
		imgSizeX = (maxX - minX);
		imgSizeY = (maxY - minY);
		
	    // write image
		Log.debug("\n-----------------------------");
		Log.debug("Build level plan image at \"" + filePath + "\"");
		Log.debug("min:(" + minX + ":" + minY + ") max:(" + maxX + ":" + maxY + ")");
		Log.debug("sizex: " + imgSizeX + " sizey: " + imgSizeY + " objects: " + draw.size());
		Log.debug("Image pixels: " + (imgSizeX*imgSizeY));
	    
	    try {
		    File file = null;
		    int counter = 0;
		    
		    while(counter < 1024){
			    file = new File(filePath + counter + ".png");
			    
			    if(!file.exists()){
			    	file.createNewFile();
			    	break;
			    }
			    else{
			    	counter++;
			    }
		    }
		    
		    // build image
		    BufferedImage img = new BufferedImage(imgSizeX, imgSizeY, BufferedImage.TYPE_INT_ARGB);
		    
		    // write images to image			    
			for(HashSet<Obj> layer: draw.values()){
				for(Obj object: layer){
					placeObject(img, object, imgSizeY, minX, minY);
				}
		    }
		    
		    // write image to file
	    	ImageIO.write(resize(img, img.getWidth()*8, img.getHeight()*8), "png", file);
			Log.debug("Done. Image saved at \"" + file.getAbsolutePath() + "\"");
		}
	    catch (IOException e) {
	    	Log.err("LocationPlanBuilder.buildPlan(): IOException");
		}
	    Log.debug("\n-----------------------------\n");
	}

	private static void placeObject(BufferedImage img, Obj object, final int imgHeigth, final int imgMinX, final int imgMinY) {
		ObjectProperties properties = Database.getObject(object.type);
		
		if(!properties.planEditorIgnore){
			int x = (int)object.x();
			int y = (int)object.y();
			int sizex = (int)(object.sizeX()/2);
			int sizey = (int)(object.sizeY()/2);
			
			int xmin = (x - sizex) - imgMinX;
			int ymin = (y - sizey) - imgMinY;
			int xmax = (x + sizex) - imgMinX;
			int ymax = (y + sizey) - imgMinY;
			
			int yMinDraw = imgHeigth - ymin - 1;
			int yMaxDraw = imgHeigth - ymax - 1;
			
			for(int i = xmin; i < xmax; ++i){
				for(int j = yMinDraw; j > yMaxDraw; --j){
					img.setRGB(i, j, getBlendARGBColor(img.getRGB(i, j), properties.planEditorColor));
				}
			}
			
			//System.out.println("x: " + x + " y: " + y + " sizex: " + sizex + " sizey: " + sizey + " imageMin: " + imgMinX + ": " + imgMinY);
			//System.out.println("min(" + xmin + ":" + ymin + ") max(" + xmax + ":" + ymax + ")");
		}
	}
	
	private static int getBlendARGBColor(int src, int dst){
		if(getA(src) == 0){
			return dst;
		}
		else if(getA(dst) == 0){
			return 0; // no color
		}
		else if(getA(dst) == 255){
			int a = 255;
			int r = getR(src)*getA(src) + getR(dst)*(1 - getA(src));
			int g = getG(src)*getA(src) + getG(dst)*(1 - getA(src));
			int b = getB(src)*getA(src) + getB(dst)*(1 - getA(src));
			return (a << 24) | (r << 16) | (g << 8) | b;
		}
		else{
			int r = (getR(dst) * getA(dst)) + (getR(src) * (1 - getA(dst)));
			int g = (getG(dst) * getA(dst)) + (getG(src) * (1 - getA(dst)));
			int b = (getB(dst) * getA(dst)) + (getB(src) * (1 - getA(dst)));
			return (getA(src) << 24) | (r << 16) | (g << 8) | b;
		}
	}

	private static int getA(int color){
		return (color >> 24) & 0xFF;
	}

	private static int getR(int color){
		return (color >> 16) & 0xFF;
	}
	
	private static int getG(int color){
		return (color >> 8) & 0xFF;
	}
	
	private static int getB(int color){
		return (color >> 0) & 0xFF;
	}
	
	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}  
}
