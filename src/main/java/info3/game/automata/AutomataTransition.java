package info3.game.automata;

public class AutomataTransition {
	AutomataState sourceState;
	ICondition condition;
	IAction action;
	AutomataState targetState;

	public AutomataTransition(AutomataState sourceState, ICondition condition, IAction action,
			AutomataState targetState) {
		this.sourceState = sourceState;
		this.condition = condition;
		this.action = action;
		this.targetState = targetState;
	}

	public AutomataState getSourceState() {
		return sourceState;
	}

	public ICondition getCondition() {
		return condition;
	}

	public IAction getAction() {
		return action;
	}

	public AutomataState getTargetState() {
		return targetState;
	}
}
