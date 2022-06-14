package info3.game.automata;

import java.util.ArrayList;

import info3.game.entities.Entity;

public class Automata {
	String name;
	AutomataState initialState;
	ArrayList<AutomataState> states;

	public Automata() {
		this.states = new ArrayList<AutomataState>();
	}

	public Automata(ArrayList<AutomataState> states) {
		this.states = states;
	}

	void execute(Entity e, AutomataState s) {

	}

	public AutomataState getInitialState() {
		return this.initialState;
	}

	public void addState(AutomataState s) {
		this.states.add(s);
	}
}
