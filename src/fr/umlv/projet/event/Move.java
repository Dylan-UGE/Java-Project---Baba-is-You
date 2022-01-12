package fr.umlv.projet.event;

import java.util.ArrayList;
import fr.umlv.projet.rule.Rules;
import fr.umlv.projet.structure.Decor;
import fr.umlv.projet.structure.GameBoard;
import fr.umlv.projet.structure.Property;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;

/**
 * Class who associate keyboard event and move method.
 */
public class Move {
	/**
	 * Decide and do the action for the move.
	 * 
	 * @param rules List of Rule.
	 * 
	 * @return The list of all element associate to the property You.
	 */
	public static ArrayList<Decor> player(Rules rules) {
		ArrayList<Decor> player = new ArrayList<>();
		
		for (var elem : rules.findElemProperty(Property.You)) {
			if (player.contains(elem) == false)
				player.add(elem);
		}
		
		return player;
	}
	
	/**
	 * Detect the event and if the event is a one 
	 * of the four directional arrows execute the move
	 * method.
	 * 
	 * @param board Represent the game board.
	 * @param context Where the event is detect
	 * @param rules List of Rule.
	 */
	public static void moves(GameBoard board, ApplicationContext context, Rules rules) {
		Event event = context.pollEvent();
    
        if (event == null) {  // no event
        	return;
        }
        
        var events = new Events(board);
        
        if (event.getAction() == Action.KEY_PRESSED) {
        	for (var Player : player(rules)) {
        		events.moveAll(event.getKey(), Player, rules);
        	}
        }
	}
}



