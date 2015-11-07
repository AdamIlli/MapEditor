package microGame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

public class Portal {

 	public int pixelX;
 	public int pixelY;
	public String targetMap; 
	public Shape portalShape;
	public Tile marvinsTarget;
	public int x;
	public int y;
	
	public Portal(Shape shape, String destination, Tile tile) {
		this.portalShape = shape;
		this.targetMap = destination;
		this.marvinsTarget = tile;
		
		this.x = (int) (portalShape.getX() + SetupClass.offsetx);
		this.y = (int) (portalShape.getY() + SetupClass.offsety);
	}
	
	public void setTargetMap(String targetMap){
		this.targetMap = targetMap;
	}
	public void setPortalShape(Shape portalShape){
		this.portalShape = portalShape;
	}
	public void setMarvinsTarget(Tile marvinsTargetTile){
		this.marvinsTarget = marvinsTargetTile;
	}
	
	public void render(Graphics g){
 		this.portalShape.setX(this.portalShape.getX() - SetupClass.offsetx);
 		this.portalShape.setY(this.portalShape.getY() - SetupClass.offsety);
 		
 	}
	
}

