package fr.umlv.projet.event;

import java.util.ArrayList;
import fr.umlv.projet.structure.Decor;
import fr.umlv.projet.structure.Element;
import fr.umlv.projet.graphic.Case;
import fr.umlv.projet.rule.Rules;
import fr.umlv.projet.structure.GameBoard;
import fr.umlv.projet.structure.Property;
import fr.umlv.zen5.KeyboardKey;

/**
 * Class for move Method.
 */
public class Events {
	private GameBoard board;
	private int verif;
	
	/**
	 * Constructor of the class.
	 * 
	 * @param board the game board.
	 */
	public Events(GameBoard board) {
		this.board = board;
		this.verif = 1;
	}
	
	
	/**
	 * Search the element associate to prop in rules.
	 * 
	 * @param cas The where search the element.
	 * @param rules The list of Rule.
	 * @param prop Property use for find the element.
	 * 
	 * @return The element associate to prop.
	 */
	public Element ElementInCase(Case cas, Rules rules, Property prop) {
		for (var element : board.board().get(cas)) {
			for (var r : rules.rule()) {
				if (element.getClass().equals(Decor.class) == false)
					return element;
				
				if (r.property().equals(prop)) {
					if (r.name().name().equals(element.toString()))
						return element;
					}
				}
			}
		
		return null;
		
		}
	
	
	/**
	 * Method associate to the property Is Jump.
	 * 
	 * @param cas The case of the object associated to the property Jump.
	 * @param element The graphical element associated to the property Jump.
	 * 
	 * @return The next case of an object associated to the property Jump.
	 */
	public Case jump(Case cas, Element element) {
		var cas2 = cas;
		
		for (var x = cas.x() + 1; x != cas.x(); x = (x + 1) % board.NbCaseX()) {
			if (board.board().get(new Case(x, cas.y())).contains(element))
				return new Case(x, cas.y());
		}
		
		for (var y = cas.y() + 1; y != cas.y();  y = (y + 1) % board.NbCaseY()) {
			if (board.board().get(new Case(cas.x(), y)).contains(element))
				return new Case(cas.x(), y);
		}
		
		return cas2;
	}
	
	
	/**
	 * Decide and do the action for the move.
	 * 
	 * @param key A keyboard key who can be UP/DOWN/LEFT/RIGHT 
	 * @param cas The case of the element to move
	 * @param cas2 The case of destination
	 * @param element The element to move
	 * @param rules List of Rule.
	 */
	public void pushMoveStopOrJump(KeyboardKey key, Case cas, Case cas2, Element element, Rules rules) {	
		var CrossingRule = rules.isCrossing(board, cas2);
		
		if (CrossingRule == 0) {
			board.del(cas, element);
			board.add(cas2, element);
		}
		
		else if (CrossingRule == 1) {
			verif = 1;
			
			if (element != null)
				move(key, cas2, ElementInCase(cas2, rules, Property.Push), rules);
			
			if (verif != 2) {
				board.del(cas, element);
				board.add(cas2, element);
			}
		}
		
		else if (CrossingRule == 2)
			verif = 2;
		
		else if (CrossingRule == 3) {
			board.del(cas, element);
			board.add(jump(cas2, ElementInCase(cas2, rules, Property.Jump)), element);
		}
	}
	
	
	/**
	 * Main method for move an element
	 * 
	 * @param key A keyboard key who can be UP/DOWN/LEFT/RIGHT 
	 * @param cas The case of the element to move
	 * @param element The element to move
	 * @param rules List of Rule.
	 * 
	 * @return The new case of the element move.
	 */
	public Case move(KeyboardKey key, Case cas, Element element, Rules rules) {
		if (cas.x() < 0 || cas.x() >= board.NbCaseX() || cas.y() < 0 || cas.y() >= board.NbCaseY())
			throw new ArrayIndexOutOfBoundsException("The case is outside the board");
	
		Case cas2 = cas;
		
		switch(key) {
			case UP :
				if (cas.y() > 0) 
					cas2 = new Case(cas.x(), cas.y() - 1);
				break;
			case DOWN :
				if (cas.y() < board.NbCaseY() - 1)
					cas2 = new Case(cas.x(), cas.y() + 1);
				break;
			case LEFT :
				if (cas.x() > 0)
					cas2 = new Case(cas.x() - 1, cas.y());
				break;
			case RIGHT :
				if (cas.x() < board.NbCaseX() - 1)
					cas2 = new Case(cas.x() + 1, cas.y());
				break;
			default:
				break;
		}
		
		if (cas.equals(cas2) == false) 
			pushMoveStopOrJump(key, cas, cas2, element, rules);
		
		else
			verif = 2;
		
		return cas2;
	}	
	
	
	/**
	 * Method for move all object element
	 * 
	 * @param key A keyboard key who can be UP/DOWN/LEFT/RIGHT 
	 * @param element The element to move
	 * @param rules List of Rule.
	 */
	public void moveAll(KeyboardKey key, Element element, Rules rules) {	
		var CaseList = new ArrayList<Case>();
		
		for (var cas : board.board().keySet()) {
			var TabValue = board.board().get(cas);
			
			if (TabValue == null)
				continue;
			
			else if (CaseList.contains(cas) && TabValue.indexOf(element) == TabValue.lastIndexOf(element))
				continue;
			
			else if (TabValue.contains(element))
				CaseList.add(this.move(key, cas, element, rules));
		}
	}
}