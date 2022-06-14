package info3.game.automata;

import java.util.ArrayList;

import info3.game.entities.Entity;

public class AutomataState {
	ArrayList<AutomataTransition> transitions;

	public AutomataState() {
		this.transitions = new ArrayList<AutomataTransition>();
	}

	public AutomataState(ArrayList<AutomataTransition> transitions) {
		this.transitions = transitions;
	}

	public void addTransition(AutomataTransition t) {
		this.transitions.add(t);
	}

	public ArrayList<AutomataTransition> getTransitions() {
		return this.transitions;
	}

	public AutomataState step(Entity e) {
		// TODO Vérifier si une transition est faisable et retourner le nouvel état
		return null;
	}
}
