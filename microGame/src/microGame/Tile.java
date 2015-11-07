package microGame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape; 
 
 
public class Tile { 

	public int x; 
 	public int y; 
 	public int pixelX;
 	public int pixelY;
 	private int size = 31; 
 	public Shape tile;
 	public boolean walkable = false;
 	
 	public Tile (int x, int y){
 		this.x = x;
 		this.y = y;
 	 	pixelX = (x * 32);
 	 	pixelY = (y * 32 );
 		
 		tile = new Rectangle(pixelX, pixelX, size, size);
 	}
 	
 	public void render(Graphics g){
 		this.tile.setX(pixelX + SetupClass.offsetx);
 		this.tile.setY(pixelY + SetupClass.offsety);
 		if(SetupClass.mode==0 || SetupClass.mode==1 || SetupClass.mode==4 || SetupClass.portalCreatorState==3){
 			if(this.walkable==true){
 			g.fill(this.tile);
 			}
 			else {g.draw(this.tile);
 			}
 		}
 	}
 	
 	public void clickedTile(int x, int y){
 		if(SetupClass.portalCreatorState==3){
 			if(this.tile.contains(x, y))
 				SetupClass.portalTile = this;
 				Portal portal = new Portal(SetupClass.portalShape, SetupClass.portalName, SetupClass.portalTile);
 				SetupClass.portals.add(portal);
 				SetupClass.portalCreatorState=3;
 				SetupClass.mode=4;
 				SetupClass.portalShape = null;
 				SetupClass.portalName = null;
 				SetupClass.portalTile = null;
 				SetupClass.portalCreatorState=0;
 				SetupClass.nodes.clear();
 		}
 		if(SetupClass.mode==0 || SetupClass.mode==1){
 			if(this.tile.contains(x, y)){
 			
 				if(SetupClass.mode == 0){
 					this.walkable = true;
 				}
 				if(SetupClass.mode == 1){
 				this.walkable = false;	
 				}
 		}
 	}
 		
 }}
	   
 
