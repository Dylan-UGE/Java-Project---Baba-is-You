package fr.umlv.projet.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.umlv.projet.graphic.Case;
import fr.umlv.projet.structure.Decor;
import fr.umlv.projet.structure.Element;
import fr.umlv.projet.structure.GameBoard;
import fr.umlv.projet.structure.Name;
import fr.umlv.projet.structure.Operator;
import fr.umlv.projet.structure.Property;
import fr.umlv.projet.structure.RuleElement;

/**
 * This class is use for stock and manipulate
 * all of the rule in a level of the game.
 */
public class Rules {
	private List<Rule> rules = new ArrayList<>();
	
	/**
	 * Constructor of the class.
	 * 
	 * @return The list of rule.
	 */
	public List<Rule> rule() {
		return rules;
	}


	/**
	 * Complete the instance of Rules with the rules
	 * situate in the game board.
	 * 
	 * @param board The game board.
	 */
	public void findRule(GameBoard board) {
		if(!rules.isEmpty()) {
			rules.clear();
		}
		
		var is = board.board().keySet().stream().filter(cas -> board.returnElement(cas, Operator.class) != null).collect(Collectors.toList());
		
		for (var cas : is) {
			Name name = board.returnElement(new Case(cas.x() - 1, cas.y()), Name.class);
			RuleElement property = board.returnElement(new Case(cas.x() + 1, cas.y()), Property.class);
			
			if (property == null)
				property = board.returnElement(new Case(cas.x() + 1, cas.y()), Name.class);
			
			if (name != null && property != null)
				rules.add(new Rule(name, Operator.Is, property));
			
			name = board.returnElement(new Case(cas.x(), cas.y() - 1), Name.class);
			property = board.returnElement(new Case(cas.x(), cas.y() + 1), Property.class);
				
			if (property == null)
				property = board.returnElement(new Case(cas.x(), cas.y() + 1), Name.class);
				
			if (name != null && property != null)
				rules.add(new Rule(name, Operator.Is, property));
		}
	}
	
	
	/**
	 * From a property finds the associated elements
	 * 
	 * @param property The property for find the elements.
	 * 
	 * @return A list of element associate to the property.
	 */
	public List<Decor> findElemProperty(Property property){
		var list = new ArrayList<Decor>();
		
		rules.stream().filter(rule -> rule.property().equals(property)).forEach(rule -> list.add(Decor.valueOf(rule.name().toString())));
		
		return list;
	}
	
	
	/**
	 * Look if there is an entity associate 
	 * with the property You.
	 * 
	 * @param board The game board.
	 * 
	 * @return A boolean for true or false.
	 */
	public boolean isAlive(GameBoard board) {
		ArrayList<Element> list;
		Case c;
		
		var listYou = findElemProperty(Property.You);
		
		if (listYou.isEmpty()) {
			return false;
		}
		
		//look if a rule exist with 'You'
		for(var value : listYou) {		
			//look in the board if the Name associate with 'You' exist in the board
			for (var i = 0; i != board.NbCaseX(); i++) {
				for (var j = 0; j != board.NbCaseY(); j++) { 
					c = new Case(i, j);
					
					list = board.board().get(c);
					if(list != null && list.contains(value)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	
	/**
	 * Look if the party is win or continue.
	 * 
	 * @param board The game board.
	 * 
	 * @return A boolean for true or false.
	 */
	public boolean isWin(GameBoard board) {
		var listYou = findElemProperty(Property.You);
		var listWin = findElemProperty(Property.Win);
		
		if (listYou.isEmpty() || listWin.isEmpty())
			return false;	
		
		for (var i = 0; i != board.NbCaseX(); i++) {
			for (var j = 0; j != board.NbCaseY(); j++) { 
				var list = board.board().get(new Case(i, j));
				
				if (list != null) {
					var verifYou = 0;
					var verifWin = 0;
					
					for (var k = 0; k != list.size(); k++) {
						if (listYou.contains(list.get(k)))
							verifYou = 1;
						if (listWin.contains(list.get(k)))
							verifWin = 1;
						
						if (verifYou == 1 && verifWin == 1)
							return true;
					}
				}
			}
		}
		
		return false;
	}
	
	
	/**
	 * Apply the Sink property.
	 * 
	 * @param board The game board.
	 * @param listSink The list of element associate to the property Sink.
	 * @param cas The case where search.
	 */
	public void isSink(GameBoard board, List<Decor> listSink, Case cas) {
		if (!listSink.isEmpty()) {
			var list = board.board().get(cas);
				
			if(list != null && list.size() > 1 ) {
				for(var value : listSink) {
					if(list.contains(value)) {
						board.delAll(cas);
						break;
					}
				}
			}
		}
	}
	
	
	/**
	 * Remove Same list between listMelt and Hot
	 * 
	 * @param board The game board.
	 * @param listMelt List of element associate to Melt property.
	 * @param listHot The list of element associate to Hot.
	 * @param cas The case where search.
	 */
	public void removeSameList(GameBoard board, List<Decor> listMelt, List<Decor> listHot, Case cas) {
		if (!listMelt.isEmpty() && !listHot.isEmpty()) {
			var list = board.board().get(cas);
				
			if(list != null) {
				for (var value : listMelt) {
					if (list.contains(value)) {
						for (var value2 : listHot) {
							if(list.contains(value2)) {
								list.remove(value);								
							}
						}
					}
				}
			}
		}
	}
	
	
	
	
	/**
	 * A method for see if we can push, 
	 * stop or jump an element in a case.
	 * 
	 * @param board The game board.
	 * @param cas The case where look.
	 * 
	 * @return an int who represent if an case contain Push, Stop or Jump element.
	 */
	public int isCrossing(GameBoard board, Case cas) {
		var push = this.findElemProperty(Property.Push);
		var stop = this.findElemProperty(Property.Stop);
		var jump = this.findElemProperty(Property.Jump);
		var TabElement = board.board().get(cas);
		
		if (TabElement == null)
			return 0;
		
		for (var element : TabElement) {
			if (push.contains(element) || element instanceof Decor == false)
				return 1;
			
			else if (stop.contains(element))
				return 2;
			
			else if (jump.contains(element))
				return 3;
		}

		return 0;
	}


	/**
	 * Method who transform elements in other element 
	 * if Rules contain a Name Is Name property
	 * 
	 * @param board The game board.
	 */
	public void Transform(GameBoard board) {
		var rule = rules.stream().filter(element -> element.property().getClass().equals(Name.class)).collect(Collectors.toList());
		for (var key : board.board().entrySet()) {
			for (var value : rule) {
				var nameDecor = Decor.valueOf(value.name().toString()); 
				if (key.getValue().contains(nameDecor)) {
					board.del(key.getKey(), nameDecor);
					board.add(key.getKey(), Decor.valueOf(value.property().toString()));
				}
			}
		}
	}
}
