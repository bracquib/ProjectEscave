package info3.game.automata;

public class CurrentState {
	AutomataState state;
	Boolean blocked;
	int remaningTicks;

	public CurrentState(AutomataState s) {
		this.state = s;
		this.blocked = false;
		this.remaningTicks = 0;
	}

	/**
	 * Permet de bloquer l'état courant
	 * 
	 * @param ticks Le nombre de ticks avant de débloquer l'état
	 */
	public void block(int ticks) {
		this.blocked = true;
		this.remaningTicks = ticks;
	}

	public void setState(AutomataState state) {
		this.state = state;
	}
}
