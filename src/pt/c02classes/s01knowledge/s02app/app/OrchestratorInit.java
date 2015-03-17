package pt.c02classes.s01knowledge.s02app.app;

import java.util.Scanner;

import pt.c02classes.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c02classes.s01knowledge.s01base.impl.Statistics;
import pt.c02classes.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;
import pt.c02classes.s01knowledge.s01base.inter.IStatistics;
import pt.c02classes.s01knowledge.s02app.actors.EnquirerMaze;
import pt.c02classes.s01knowledge.s02app.actors.ResponderMaze;
import pt.c02classes.s01knowledge.s02app.actors.EnquirerAnimals;
import pt.c02classes.s01knowledge.s02app.actors.ResponderAnimals;


public class OrchestratorInit {
	public static void main(String[] args) {
		IEnquirer enq;
		IResponder resp;
		IStatistics stat;
		IBaseConhecimento base = new BaseConhecimento();		
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Quer jogar comigo, humano?");
		System.out.println("Entao qual jogo voce deseja jogar?");
		String gameType = scanner.nextLine();
		
		switch (gameType) {
		case "Maze":
			System.out.println("Entao quer desafiar o meu gigantesco tamanho? Diga, qual seu mapa, criatura.");
			String mazeType = scanner.nextLine();
			stat = new Statistics();
			resp = new ResponderMaze(stat, mazeType);
			enq = new EnquirerMaze();
			enq.connect(resp);
			enq.discover();
			break;
		 case "Animals":
			System.out.println("Entao quer desafiar o meu gigantesco intelecto? Diga, o que voce esta pensando, criatura.");
			String animalType = scanner.nextLine();
			base.setScenario(animalType);
			String animalsList[] = base.listaNomes();
			for (int animal = 0; animal < animalsList.length; animal++) {	
				stat = new Statistics();
				resp = new ResponderAnimals(stat, animalsList[animal]);
				enq = new EnquirerAnimals();
				enq.connect(resp);
				enq.discover();
			}
			break; 
		}
	
	scanner.close();
	}
}
