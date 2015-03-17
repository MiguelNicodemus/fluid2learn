package pt.c02classes.s01knowledge.s02app.actors;

import pt.c02classes.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IDeclaracao;
import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IObjetoConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;

import java.util.HashMap;

public class EnquirerAnimals implements IEnquirer {
	
	IObjetoConhecimento obj;
	IResponder responder;
	
	public void connect(IResponder responder) {
		this.responder = responder;
	}
	
	public boolean discover() {
		boolean answer = false;
		IBaseConhecimento bc = new BaseConhecimento();
		String animals[] = bc.listaNomes();
		
		HashMap<String, String> dbQandA = new HashMap<String, String>();
		
		for (int i = 0; i < animals.length; i++) {
			obj = bc.recuperaObjeto(animals[i]);
			IDeclaracao decl = obj.primeira();
			boolean animalEsperado = true;
			
			while (decl != null && animalEsperado) {
				String pergunta = decl.getPropriedade();
				String respostaEsperada = decl.getValor();
				
				
				if (!dbQandA.containsKey(pergunta)) {
					String resposta = responder.ask(pergunta);
					dbQandA.put(pergunta, resposta);
					
					if (resposta.equalsIgnoreCase(respostaEsperada))
						decl = obj.proxima();
					else
						animalEsperado = false;
				
				} else {
					if (dbQandA.get(pergunta).equalsIgnoreCase(respostaEsperada)) 
						decl = obj.proxima();
					else
						animalEsperado = false;
				}
			}
			
			if (animalEsperado) {
				boolean acertei = responder.finalAnswer(animals[i]);
				answer = acertei;
				
				if (acertei)
					System.out.println("Oba! Acertei!");
				else
					System.out.println("fuem! fuem! fuem!");
			}
		}
		
		return answer;
	}
}
