package fr.umlv.projet.rule;

import fr.umlv.projet.graphic.Case;
import fr.umlv.projet.structure.GameBoard;
import fr.umlv.projet.structure.Property;

/**
 * This class apply all of the properties.
 */
public class ApplieRule {
	/**
	 * Apply the rules.
	 * 
	 * @param board The game board.
	 * @param rules List of Rule.
	 */
	public static void applieRule(GameBoard board, Rules rules) {
		var listSink = rules.findElemProperty(Property.Sink);
		var listYou = rules.findElemProperty(Property.You);
		var listDefeat = rules.findElemProperty(Property.Defeat);
		var listHot = rules.findElemProperty(Property.Hot);
		var listMelt = rules.findElemProperty(Property.Melt);
		
		rules.Transform(board);
		
		for (var i = 0; i != board.NbCaseX(); i++) {
			for (var j = 0; j != board.NbCaseY(); j++) { 
				var cas = new Case(i, j);
				
				rules.isSink(board, listSink, cas);
				rules.removeSameList(board, listYou, listDefeat, cas);
				rules.removeSameList(board, listMelt, listHot, cas);
			}
		}
	}
}
