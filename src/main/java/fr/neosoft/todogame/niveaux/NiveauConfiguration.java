package fr.neosoft.todogame.niveaux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;
import java.util.List;

@Configuration
public class NiveauConfiguration {
	private final NiveauRepository niveauRepository;

	public NiveauConfiguration(NiveauRepository niveauRepository) {
		this.niveauRepository = niveauRepository;
	}

	@Autowired
	private void init(NiveauService service){
		if(niveauRepository.count() == 0) {
			int nbPointsRequis = 0;

			for (int i = 1; i < 100; i++) {
				// Utilisation d'un nombre généré aléatoirement
				// Référence utilisée : https://info.clg.qc.ca/java/elements/generer-nombres-aleatoires
				SecureRandom random = new SecureRandom();
				int nbAleatoire = 5 + random.nextInt(20);
				nbPointsRequis = nbPointsRequis + nbAleatoire * i * 10;
				niveauRepository.save(new Niveau(i, nbPointsRequis));
			}
		}

	}
}
