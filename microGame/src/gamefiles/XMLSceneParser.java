package gamefiles;

import java.util.ArrayList;

import microGame.Portal;
import microGame.Tile;

import org.newdawn.slick.geom.Shape;

import org.w3c.dom.Element;

public class XMLSceneParser extends XMLParser {

	public XMLSceneParser(String rootElementName) {
		super(rootElementName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * <?xml version="1.0"?>
	 * <!DOCTYPE Scene [
	 * 	<!ELEMENT name (#PCDATA)>
	 * 	<!ELEMENT size (width, height)>
	 * 		<!ELEMENT width (#PCDATA)>
	 * 		<!ELEMENT height (#PCDATA)>
	 * 	<!ELEMENT portals (portal+)>
	 * 		<!ELEMENT portal (destination, tile, )>
	 * ]>
	 * @param sceneName
	 * @param width
	 * @param height
	 * @param portalList
	 * @param tileList
	 */
	public void buildSceneXMLTree(String sceneName, int width, int height, ArrayList<Portal> portalList, ArrayList<Tile> tileList) {
		// Write scene name
		
		System.out.println("Title: " + sceneName + "\nWidth: " + width + "\nHeight: " + height);
		
		writeSceneName(sceneName);
		writeSceneSize(width, height);
		writePortals(portalList);
		writeTiles(tileList);
		
		writeToDisk("assets/scenes/" + sceneName + ".xml");
	}
	
	private void writeTiles(ArrayList<Tile> tileList) {
		Element tiles = doc.createElement("tiles");
		
		for (int i = 0; i < tileList.size(); i++) {
			Tile tile = tileList.get(i);
			if (tile.walkable) {
				tiles.appendChild(parseTileAsElement(tile));
			}
		}
		root.appendChild(tiles);
	}
	
	private void writePortals(ArrayList<Portal> portalList) {
		Element portals = doc.createElement("portals");
		
		for (int i = 0; i < portalList.size(); i++) {
			portals.appendChild(parsePortalAsElement(portalList.get(i)));
		}
		root.appendChild(portals);
	}
	
	private Element parseShapeAsElement(Shape shape) {
		Element shapeElmt = doc.createElement("shape");
		float[] points = shape.getPoints();
		
		for (int i = 0; i < points.length; i += 2) {
			Element point = doc.createElement("point");
			point.setAttribute("x", Float.toString(points[i]));
			point.setAttribute("y", Float.toString(points[i+1]));
			shapeElmt.appendChild(point);
		}
	
		return shapeElmt;
	}
	
	private Element parseTileAsElement(Tile tile) {
		Element tileElmt = doc.createElement("tile");
		Element tileIndexX = doc.createElement("ix");
		Element tileIndexY = doc.createElement("iy");
		
		tileElmt.setAttribute("classReference", "Game.Tile");
		tileIndexX.setTextContent(Integer.toString(tile.x));
		tileIndexY.setTextContent(Integer.toString(tile.y));
		
		tileElmt.appendChild(tileIndexX);
		tileElmt.appendChild(tileIndexY);
		
		return tileElmt;
	}
	
	/**
	 * 
	 * <portal>
	 * 	<destination>[String]</destination>
	 * 	<tile classRefrence="Game.Tile">
	 * 		<ix>[int]<ix>
	 * 		<iy>[int]</iy>
	 * 	</tile>
	 * 	<position>
	 * 		<x>[int]</x>
	 * 		<y>[int]</y>
	 * 	</position>
	 * 	<shape>
	 * 		<point x="[float]" y="[Float]" />
	 * 		...
	 * 	</shape>
	 * </portan>
	 * @param portal
	 * @return
	 */
	private Element parsePortalAsElement(Portal portal) {
		Element portalElmt = doc.createElement("portal");
		
		String dest = portal.targetMap;
		Tile targetTile = portal.marvinsTarget;
		Shape shape = portal.portalShape;
		
		portalElmt.setAttribute("classReference", "Game.Portal");
		
		Element destElmt = doc.createElement("destination");
		destElmt.setTextContent(dest);
		portalElmt.appendChild(destElmt);
		
		if (targetTile != null) {
			Element tileElmt = parseTileAsElement(targetTile);
			portalElmt.appendChild(tileElmt);
		}
		
		
		Element pointsElmt = parseShapeAsElement(shape);
		portalElmt.appendChild(pointsElmt);
		
		Element position = doc.createElement("position");
		position.setAttribute("x", Integer.toString(portal.x));
		position.setAttribute("y", Integer.toString(portal.y));
		
		return portalElmt;
	}
	
	private void writeSceneName(String name) {
		Element elmt = doc.createElement("title");
		elmt.setTextContent(name);
		root.appendChild(elmt);
	}
	
 	private void writeSceneSize(int width, int height) {
 		Element sizeElmt = doc.createElement("size");
 		Element widthElmt = doc.createElement("width");
 		Element heightElmt = doc.createElement("height");
 		
 		widthElmt.setTextContent(Integer.toString(width));
 		heightElmt.setTextContent(Integer.toString(height));
 		
 		sizeElmt.appendChild(widthElmt);
 		sizeElmt.appendChild(heightElmt);
		
 		root.appendChild(sizeElmt);
 	}

}
