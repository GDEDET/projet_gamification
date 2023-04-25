package fr.neosoft.todogame.taches;

public enum Priorite {
    BASSE(50),
    MOYENNE(100),
    ELEVEE(200);

    private final int nbPoint;

    Priorite(int nbPoint) {
        this.nbPoint = nbPoint;
    }

    public int getNbPoint() {
        return nbPoint;
    }
}
