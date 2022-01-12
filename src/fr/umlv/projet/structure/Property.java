package fr.umlv.projet.structure;

/**
* Enum who represent the Properties of the game
* use for the rules.
*/
public enum Property implements Element, RuleElement{    
	/** You */ 
	You,
	/** Win */
	Win,
	/** Stop */
	Stop,
	/** Push */
	Push,
	/** Melt */
	Melt,
	/** Hot */
	Hot,
	/** Defeat */
	Defeat,
	/** Sink */
	Sink,
	/** Jum^p */
	Jump;
}