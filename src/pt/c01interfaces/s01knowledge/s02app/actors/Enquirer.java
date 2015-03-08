package pt.c01interfaces.s01knowledge.s02app.actors;

import pt.c01interfaces.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01knowledge.s01base.inter.IEnquirer;
import pt.c01interfaces.s01knowledge.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IResponder;

public class Enquirer implements IEnquirer
{
	IObjetoConhecimento obj;
	
	@Override
	public void connect(IResponder responder)
	{
		IBaseConhecimento bc = new BaseConhecimento();
		String animals[] = bc.listaNomes();
		String dbQandA[][] = new String[20][2];
		
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 2; j++) {
				dbQandA[i][j] = "DEFAULT";
			}
		}
		
		for (int i = 0; i < animals.length; i++) {
			obj = bc.recuperaObjeto(animals[i]);
			IDeclaracao decl = obj.primeira();
			boolean animalEsperado = true;
			
			while (decl != null && animalEsperado) {
				String pergunta = decl.getPropriedade();
				String respostaEsperada = decl.getValor();
				
				boolean questionFound = false;
				
				int k = 0;
				
				for (k = 0; k < 20 && !questionFound; k++) {
					if (dbQandA[k][0].equalsIgnoreCase(pergunta)) {
						questionFound = true;
					}
				}
				
				if (!questionFound) {
					String resposta = responder.ask(pergunta);
					
					int j = 0;
					while (j < 100 && !dbQandA[j][1].equalsIgnoreCase("DEFAULT")) {
						j++;
					}
					
					dbQandA[j][0] = pergunta;
					dbQandA[j][1] = resposta;
					
					if (resposta.equalsIgnoreCase(respostaEsperada))
						decl = obj.proxima();
					else
						animalEsperado = false;
				
				} else {
					if (dbQandA[k][1].equalsIgnoreCase(respostaEsperada)) 
						decl = obj.proxima();
					else
						animalEsperado = false;
				}
			}
			
			if (animalEsperado) {
				boolean acertei = responder.finalAnswer(animals[i]);
				
				if (acertei)
					System.out.println("Oba! Acertei!");
				else
					System.out.println("fuem! fuem! fuem!");
			}
		}
	}
}