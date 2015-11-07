package microGame;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.sql.Array;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.Input;

public class SetupClass extends BasicGame{
	
	String activeMap = "No active map";
	Image background;
	ArrayList<Tile> tiles = new ArrayList<Tile>();
	public static ArrayList<Portal> portals = new ArrayList<Portal>();
	public static ArrayList<Node> nodes = new ArrayList<Node>();
	static int mode = 0;
	TrueTypeFont font;
	public static int offsetx = 0;
	public static int offsety = 0;
	static int portalCreatorState = 0;
	public static Shape portalShape;
	public static String portalName;
	public static Tile portalTile;
	
	public SetupClass(String title) {
		super(title);
	}
	
	
	public static void main(String[] args) throws SlickException {	
		AppGameContainer app = new AppGameContainer(new SetupClass("Map Editor"));

		
		app.setDisplayMode(1024, 768, false);
		
		app.start();

	}

	@Override
	public void init(GameContainer container) throws SlickException {
		background = new Image("sprites/none.png");
        int columns = (background.getWidth() / 32);
        int rows = (background.getHeight() / 32);
        for(int c=0; c<columns; c++){
        	for(int r=0; r<rows; r++){
        		tiles.add(new Tile(r, c));
        	}
        }
        Font awtFont = new Font("Times New Roman", Font.BOLD, 12);
        this.font = new TrueTypeFont(awtFont, false);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {

		g.drawImage(background, 0 + offsetx, 0 + offsety);
		for (int i=0; i<tiles.size(); i++){
			if(tiles.get(i).walkable==true){
				g.setColor(new Color(255,0,0));
			}
			else{
				g.setColor(new Color(0,0,255));
			}
			//g.draw(tiles.get(i).tile);
			tiles.get(i).render(g);
			if(font==null){
				
			} else {
				if(mode==0 || mode==1 || mode==4 || portalCreatorState==3){
				font.drawString(tiles.get(i).pixelX + offsetx, tiles.get(i).pixelY + offsety, tiles.get(i).x + "," + tiles.get(i).y, Color.black);
				}
			}
		}
		if(portalCreatorState==1){
			for(int i=0; i<nodes.size(); i++){
				Shape circle = new Circle(nodes.get(i).x, nodes.get(i).y, 3);
				g.fill(circle);
				g.draw(circle);
			}
		}
		if(portalCreatorState==2 || portalCreatorState==3){
			g.setColor(new Color(0,0,255));
			g.fill(portalShape);
			g.draw(portalShape);
			
		}
		if(mode==4){
			for(int i=0; i<portals.size(); i++){
				Portal portal = portals.get(i);
				portal.portalShape.setX(portal.x + SetupClass.offsetx);
				portal.portalShape.setY(portal.y + SetupClass.offsety);
				g.setColor(new Color(0,0,255));
				g.fill(portals.get(i).portalShape);
				g.draw(portals.get(i).portalShape);
			}
			
		}
		
		
		g.setColor(new Color(30,30,30));
		g.fillRect(49, 49, 150, 275);
		g.setColor(new Color(0,255,0));
		g.drawString("1: New map", 50, 50);
		g.drawString("2: Load map", 50, 75);
		g.drawString("3: Save map", 50, 100);
		g.drawString("4: Walkable ON", 50, 125);
		g.drawString("5: Walkable OFF", 50, 150);
		g.drawString("6: New portal", 50, 175);
		g.drawString("Portals:" + portals.size(), 50, 200);
		g.drawString("7: render all", 50, 250);
		g.drawString("Escape: Exit", 50, 275);
	}

	@Override
	
	public void update(GameContainer container, int Delta) throws SlickException {
		
		Input input = container.getInput();
		if(input.isKeyPressed(input.KEY_ESCAPE)){
			container.exit();
		}
		if(input.isKeyPressed(input.KEY_RIGHT)){
			offsetx -= 32;
		}
		if(input.isKeyPressed(input.KEY_LEFT)){
			offsetx += 32;
		}
		if(input.isKeyPressed(input.KEY_DOWN)){
			offsety -= 32;
		}
		if(input.isKeyPressed(input.KEY_UP)){
			offsety += 32;
		}

		if(input.isKeyPressed(input.KEY_4)){
			mode=0;
		}
		if(input.isKeyPressed(input.KEY_5)){
			mode=1;
		}
		if(input.isKeyPressed(input.KEY_7)){
			mode=4;
		}
		if(input.isKeyPressed(input.KEY_6)){
			mode=2;
			portalCreatorState=1;
		}
		if(input.isKeyPressed(input.KEY_ENTER)) {
			if(portalCreatorState==1){
				float[] points = new float[nodes.size() * 2];
				int pointIndex = 0;
				for (int i = 0; i < nodes.size(); i++) {
					points[pointIndex] = nodes.get(i).x;
					points[pointIndex + 1] = nodes.get(i).y;
					pointIndex += 2;
				}
				portalShape = new Polygon(points);
				portalCreatorState=2;
				String s = (String)JOptionPane.showInputDialog(
						"Leading where?",
	            JOptionPane.PLAIN_MESSAGE);
				portalName = s;
				portalCreatorState=3;
				
			}
		}
		
		
		if(input.isKeyPressed(input.KEY_1)){
			final JFileChooser fc = new JFileChooser();
			Component aComponent = null;
			int returnVal = fc.showOpenDialog(aComponent);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
	            tiles.clear();
				File file = fc.getSelectedFile();
	            activeMap = file.getPath();
	            background = new Image(activeMap);
	            int rows = (background.getWidth() / 32);
	            int columns = (background.getHeight() / 32);
	            for(int c=0; c<columns; c++){
	            	for(int r=0; r<rows; r++){
	            		tiles.add(new Tile(r, c));
	            	}
	            }
	        } else {
	            
	        }

			
		}
		if(input.isMousePressed(0)){
			if(portalCreatorState==1){
				if(mode==2){
					Node newnode = new Node();
					newnode.x=input.getMouseX() + offsetx;
					newnode.y=input.getMouseY() + offsety;
					nodes.add(newnode);
				}
			}
			if(portalCreatorState==3){
				for (int i=0; i<tiles.size(); i++){
				tiles.get(i).clickedTile(input.getMouseX(),input.getMouseY());
				}
			}
		}
		
		if(input.isMouseButtonDown(0)){
			if(mode==0 || mode==1){
				for (int i=0; i<tiles.size(); i++){
				tiles.get(i).clickedTile(input.getMouseX(),input.getMouseY());
				}
			}
		}
		
		
		
	}

}
