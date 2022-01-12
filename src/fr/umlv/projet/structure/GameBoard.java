package fr.umlv.projet.structure;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import fr.umlv.projet.graphic.Case;

/**
* The class who represent the game board.
*/
public class GameBoard {
	private LinkedHashMap<Case, ArrayList<Element>> board;
	private final int NbCaseX;
	private final int NbCaseY;
	
	/**
	 * Constructor of the class.
	 * 
	 * @param NbCaseX The number of case for the X coordinate.
	 * @param NbCaseY The number of case for the Y coordinate.
	 */
	public GameBoard(int NbCaseX, int NbCaseY) {
		this.board = new LinkedHashMap<Case, ArrayList<Element>>();
		this.NbCaseX = NbCaseX;
		this.NbCaseY = NbCaseY;
		
		for (var i = 0; i != NbCaseY; i++) {
			for (var j = 0; j != NbCaseX; j++) {
				board.put(new Case(j, i), new ArrayList<Element>());
			}
		}
	}
	
	/**
	 * Getter for board.
	 * 
	 * @return the value of board.
	 */
	public LinkedHashMap<Case, ArrayList<Element>> board() {
		return board;
	}

	/**
	 * Getter for NbCaseX.
	 * 
	 * @return the value of NbCaseX.
	 */
	public int NbCaseX() {
		return NbCaseX;
	}

	/**
	 * Getter for NbCaseY.
	 * 
	 * @return the value of NbCaseY
	 */
	public int NbCaseY() {
		return NbCaseY;
	}

	
	/**
	 * Return the element in case1 of class clas.
	 * 
	 * @param <T> The type of element.
	 * @param case1 The case where found the element.
	 * @param clas The class for search the element.
	 * 
	 * @return The element in case1.
	 */
	@SuppressWarnings("unchecked")
	public <T> T returnElement(Case case1, Class<T> clas){
		var list = board.get(case1);
		
		if(list == null) 
			return null;
		
		for(var value : list) {
			if (value.getClass().equals(clas))
				return (T) value;
		}
		
		return null;
	}


	/**
	 * Add an element in a specific case.
	 * 
	 * @param cas Where add the element.
	 * @param element The element to add.
	 */
	public void add(Case cas, Element element) {
		var previousList = board.get(cas);
		previousList.add(element);
		board.put(cas, previousList);
	}
	
	
	/**
	 * Delete an element in a specific case.
	 * 
	 * @param cas Where delete the element.
	 * @param element The element to delete.
	 */
	public void del(Case cas, Element element) {
		var previousList = board.get(cas);
		previousList.remove(element);
		board.put(cas, previousList);
	}
	
	
	/**
	 * Delete all element in a case.
	 * 
	 * @param cas The case to clear in game board.
	 */
	public void delAll(Case cas) {
		board.remove(cas);
		board.put(cas, new ArrayList<Element>());
	}
}
