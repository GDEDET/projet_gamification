package fr.neosoft.todogame.defis;

import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.utils.CRUDInterface;

public interface GestionDefiInterface extends CRUDInterface<Defi> {

    public Iterable<Defi> findAllByUser(Long idUser);

    void incrementerNbTachesTerminees(Personne personne);

    void incrementerNbPointsGagnes(Personne personne, int nbPoints);
}
