package info3.game.automata;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import info3.game.automata.ast.AST;
import info3.game.automata.ast.Action;
import info3.game.automata.ast.Automaton;
import info3.game.automata.ast.Behaviour;
import info3.game.automata.ast.BinaryOp;
import info3.game.automata.ast.Category;
import info3.game.automata.ast.Condition;
import info3.game.automata.ast.Direction;
import info3.game.automata.ast.FunCall;
import info3.game.automata.ast.IVisitor;
import info3.game.automata.ast.Key;
import info3.game.automata.ast.Mode;
import info3.game.automata.ast.State;
import info3.game.automata.ast.Transition;
import info3.game.automata.ast.UnaryOp;
import info3.game.automata.ast.Underscore;
import info3.game.automata.ast.Value;
import info3.game.automata.parser.AutomataParser;

public class BotBuilder implements IVisitor {

	public enum BotAutomata {
		INITIAL, MODE, CREATING_TRANSITION
	}

	ArrayList<Automata> automatas;
	Automata currentAutomata;
	AutomataState sourceState;
	ICondition condition;
	IAction action;
	BotAutomata botState;

	public static void main(String[] args) throws Exception {
		AST ast;
		ast = new AutomataParser(new BufferedReader(new FileReader("src/main/resources/test_automata.gal"))).Run();
//		AstPrinter ast_printer = new AstPrinter();
//		ast.accept(ast_printer);
//		System.out.println(ast_printer.to_dot());

		BotBuilder bot_builder = new BotBuilder();
		ast.accept(bot_builder);
	}

	public BotBuilder() {
		this.botState = BotAutomata.INITIAL;
		this.automatas = new ArrayList<Automata>();
		this.sourceState = null;
	}

	@Override
	public Object visit(Category cat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Direction dir) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Key key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Value v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Underscore u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enter(FunCall funcall) {
	}

	public info3.game.automata.Direction astDirectionToinfo3Direction(Direction d) {
		switch (d.terminal.content.charAt(0)) {
		case 'N':
			return info3.game.automata.Direction.NORTH;
		case 'S':
			return info3.game.automata.Direction.SOUTH;
		case 'W':
			return info3.game.automata.Direction.WEST;
		case 'E':
			return info3.game.automata.Direction.EST;
		}
		return null;
	}

	public info3.game.automata.Category astCategoryToinfo3Category(Category c) {
		switch (c.terminal.content.charAt(0)) {
		case 'A':
			return info3.game.automata.Category.ADVERSAIRE;
		case 'J':
			return info3.game.automata.Category.JUMPABLE;
		case 'O':
			return info3.game.automata.Category.OBJECT;
		case 'P':
			return info3.game.automata.Category.PLAYER;
		case 'T':
			return info3.game.automata.Category.TEAM;
		case '_':
			return info3.game.automata.Category.SOMETHING;
		case 'V':
			return info3.game.automata.Category.VOID;
		case 'C':
			return info3.game.automata.Category.C;
		case 'D':
			return info3.game.automata.Category.D;
		case 'G':
			return info3.game.automata.Category.G;
		case 'M':
			return info3.game.automata.Category.M;
		case 'X':
			return info3.game.automata.Category.X;
		}
		return null;
	}

	@Override
	public Object exit(FunCall funcall, List<Object> parameters) {
		info3.game.automata.Direction d;
		info3.game.automata.Category c;
		switch (funcall.name) {
		case "True":
			System.out.println("Created True condition");
			return this.condition = new True();
		case "Cell":
			System.out.println("Created Cell condition");
			d = this.astDirectionToinfo3Direction((Direction) parameters.get(0));
			c = this.astCategoryToinfo3Category((Category) parameters.get(1));
			return this.condition = new Cell(d, c);
		case "Explode":
			System.out.println("Created Explode action");
			return this.action = new Explode();
		case "Move":
			System.out.println("Created Move action");
			d = this.astDirectionToinfo3Direction((Direction) parameters.get(0));
			return this.action = new Move(d);
		case "Egg":
			System.out.println("Created Egg action");
			return this.action = new Egg();
		default:
			System.out.println("[WARNING] Unexpected condition or action: " + funcall.name);
			return null;
		}
	}

	@Override
	public Object visit(BinaryOp operator, Object left, Object right) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(UnaryOp operator, Object expression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(State state) {
		System.out.println("Visited state: " + state.name);
		AutomataState newState = new AutomataState(state.name);
		newState = this.currentAutomata.addState(newState);
		switch (this.botState) {
		case MODE:
			System.out.println("Setting source state to: " + newState.getName());
			this.sourceState = newState;
			return newState;
		case CREATING_TRANSITION:
			System.out.println("Adding transition from " + this.sourceState.getName() + " to " + newState.getName());
			AutomataTransition transition = new AutomataTransition(this.sourceState, this.condition, this.action,
					newState);
			this.sourceState.addTransition(transition);
			this.condition = null;
			this.action = null;
			return newState;
		default:
			return newState;
		}
	}

	@Override
	public void enter(Mode mode) {
		System.out.println("Entered Mode: switch to MODE");
		this.botState = BotAutomata.MODE;
	}

	@Override
	public Object exit(Mode mode, Object source_state, Object behaviour) {
		System.out.println("Exited Mode: switch to INITIAL");
		this.botState = BotAutomata.INITIAL;
		return null;
	}

	@Override
	public Object visit(Behaviour behaviour, List<Object> transitions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enter(Condition condition) {
		System.out.println("Entered Condition: switch to CREATING_TRANSITION");
		this.botState = BotAutomata.CREATING_TRANSITION;
	}

	@Override
	public Object exit(Condition condition, Object expression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enter(Action acton) {
		if (this.botState == BotAutomata.CREATING_TRANSITION)
			return;
		System.out.println("Unexpected behaviour ! Entered Action without Condition");
		this.botState = BotAutomata.CREATING_TRANSITION;
	}

	@Override
	public Object exit(Action action, List<Object> funcalls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Transition transition, Object condition, Object action, Object target_state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enter(Automaton automaton) {
		System.out.println("Entered Automaton: " + automaton.name);
		this.currentAutomata = new Automata(automaton.name);
		this.automatas.add(currentAutomata);
	}

	@Override
	public Object exit(Automaton automaton, Object initial_state, List<Object> modes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(AST bot, List<Object> automata) {
		// TODO Auto-generated method stub
		return null;
	}

}
