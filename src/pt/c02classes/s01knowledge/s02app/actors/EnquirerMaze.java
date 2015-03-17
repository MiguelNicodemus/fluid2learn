package pt.c02classes.s01knowledge.s02app.actors;

import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;
import java.util.Stack;

public class EnquirerMaze implements IEnquirer {

	IResponder responder;	
	Stack<String> pathCovered;
	
	int posX,posY;
	
	public void connect(IResponder responder) {
		this.responder = responder;
	}
	
	public boolean discover() {
		
		pathCovered = new Stack<String>();
		posX = 0;
		posY = 0;
		
		solveMaze();
		
		if (responder.finalAnswer("cheguei")) {
			System.out.println("chegueiiii!!!");
		}else{
			System.out.println("fuck!");
		}
		
		
		return true;
	}
	
	public boolean solveMaze() {
		
		if (responder.ask("aqui").equalsIgnoreCase("saida"))
			return true;
		
		int numberMoves = pathCovered.size();
		
		for (int i = 0; i < numberMoves - 1; i++) {
			if (pathCovered.elementAt(i).equalsIgnoreCase(pathCovered.peek())) {
				return false;
			}
		}
		
		if (responder.ask("norte").equalsIgnoreCase("passagem") || responder.ask("norte").equalsIgnoreCase("saida")) {
			posY++;
			pathCovered.push(posX + ";" + posY);
			responder.move("norte");
			if (solveMaze())
				return true;
			else {
				responder.move("sul");
				pathCovered.pop();
			}
		}
		if (responder.ask("sul").equalsIgnoreCase("passagem") || responder.ask("sul").equalsIgnoreCase("saida")) {
			posY--;
			pathCovered.push(posX + ";" + posY);
			responder.move("sul");
			if (solveMaze())
				return true;
			else {
				responder.move("norte");
				pathCovered.pop();
			}
		}
		if (responder.ask("leste").equalsIgnoreCase("passagem") || responder.ask("leste").equalsIgnoreCase("saida")) {
			posX++;
			pathCovered.push(posX + ";" + posY);
			responder.move("leste");
			if (solveMaze())
				return true;
			else {
				responder.move("oeste");
				pathCovered.pop();
			}
		}
		if (responder.ask("oeste").equalsIgnoreCase("passagem") || responder.ask("oeste").equalsIgnoreCase("saida")) {
			posX--;
			pathCovered.push(posX + ";" + posY);
			responder.move("oeste");
			if (solveMaze())
				return true;
			else {
				responder.move("leste");
				pathCovered.pop();
			}
		}
		
		return false;
	}
}
