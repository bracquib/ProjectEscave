package info3.game.automata;

import java.util.ArrayList;

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
}
