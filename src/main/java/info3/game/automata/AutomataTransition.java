package info3.game.automata;

import java.util.ArrayList;

public class AutomataTransition {
	AutomataState sourceState;
	ArrayList<ICondition> conditions;
	ArrayList<IAction> actions;
	AutomataState targetState;

	public AutomataTransition(AutomataState sourceState, ArrayList<ICondition> conditions, ArrayList<IAction> actions,
			AutomataState targetState) {
		this.sourceState = sourceState;
		this.conditions = (ArrayList<ICondition>) conditions.clone();
		this.actions = (ArrayList<IAction>) actions.clone();
		this.targetState = targetState;
	}

	public AutomataState getSourceState() {
		return sourceState;
	}

	public ArrayList<ICondition> getConditions() {
		return conditions;
	}

	public ArrayList<IAction> getActions() {
		return actions;
	}

	public AutomataState getTargetState() {
		return targetState;
	}

	public String toString() {
		String out = this.getSourceState() + ": ";
		out += this.getConditions().get(0);
		for (int i = 1; i < this.getConditions().size(); i++) {
			out += " & " + this.getConditions().get(i);// .getClass().getSimpleName();
		}
		if (this.getActions().size() != 0) {
			out += " ? " + this.getActions().get(0).getClass().getSimpleName();
			for (int i = 1; i < this.getActions().size(); i++) {
				out += " & " + this.getActions().get(i).getClass().getSimpleName();
			}
		}
		out += " :" + this.getTargetState();
		return out;
	}
}
