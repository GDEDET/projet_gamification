package fr.neosoft.todogame.defis.defis_personnes;

import fr.neosoft.todogame.defis.Defi;
import fr.neosoft.todogame.personnes.Personne;

import java.io.Serializable;

public class PKDefiPersonne implements Serializable {
    protected Personne personne;
    protected Defi defi;

    public PKDefiPersonne() {
    }

    public PKDefiPersonne(Personne personne, Defi defi) {
        this.personne = personne;
        this.defi = defi;
    }
}

