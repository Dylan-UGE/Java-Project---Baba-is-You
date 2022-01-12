package fr.umlv.projet.rule;

import java.util.Objects;

import fr.umlv.projet.structure.Name;
import fr.umlv.projet.structure.Operator;
import fr.umlv.projet.structure.RuleElement;

/**
 * The class who represent a rule.
 */
public class Rule {
	private Name name;
	private Operator op;
	private RuleElement property;
	
	/**
	 * Constructor of the class.
	 * 
	 * @param name The element of the rule.
	 * @param op The operator of the rule.
	 * @param prop The property or name of the rule.
	 */
	public Rule(Name name, Operator op, RuleElement prop) {
		this.name = Objects.requireNonNull(name);
		this.op = Objects.requireNonNull(op);
		this.property = Objects.requireNonNull(prop);
	}
	
	/**
	 * Getter for property.
	 * 
	 * @return The property value.
	 */
	public RuleElement property() {
		return this.property;
	}
	
	/**
	 * Getter for name.
	 * 
	 * @return The name value.
	 */
	public Name name() {
		return this.name;
	}
}
