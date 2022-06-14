package info3.game.automata;

import java.util.ArrayList;

import info3.game.entities.Entity;

public class Automata {
	String name;
	AutomataState initialState;
	ArrayList<AutomataState> states;

	public Automata(String name) {
		this.name = name;
		this.states = new ArrayList<AutomataState>();
	}

	public Automata(String name, ArrayList<AutomataState> states) {
		this.name = name;
		this.states = states;
	}

	void step(Entity e, AutomataState s) {
		s.step(e);
	}

	public void setInitialState(AutomataState state) {
		this.initialState = state;
	}

	public AutomataState getInitialState() {
		return this.initialState;
	}

	public AutomataState addState(AutomataState s) {
		AutomataState state = this.lookup(s.getName());
		if (state != null)
			return state;
		if (this.states.size() == 0) { // || initialState == null
			this.setInitialState(s);
		}
		this.states.add(s);
		return s;
	}

	public AutomataState lookup(String stateName) {
		for (AutomataState state : this.states) {
			if (state.getName().equals(stateName))
				return state;
		}
		return null;
	}

	public String getName() {
		return this.name;
	}

	public String toString() {
		String out = this.getName() + this.getInitialState();
		return out;
	}
}
