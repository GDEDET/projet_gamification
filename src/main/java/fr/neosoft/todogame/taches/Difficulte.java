package fr.neosoft.todogame.taches;

public enum Difficulte {
    FACILE(50),
    MOYENNE(100),
    DIFFICILE(200);

    private final int nbPoint;

    Difficulte(int nbPoint) {
        this.nbPoint = nbPoint;
    }

    public int getNbPoint() {
        return nbPoint;
    }
}
