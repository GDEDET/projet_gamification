package fr.neosoft.todogame.defis;

import fr.neosoft.todogame.defis_personnes.DefiPersonne;
import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.personnes.PersonneData;

import java.util.Arrays;
import java.util.List;

public class DefiData {
    public static Defi DEFI_VIDE = new Defi();

    public static Defi DEFI_1 = new Defi(1L, "Défi 1", "Description du défi 1", TypeDefi.QUOTIDIEN, 50, 0, 3, null, null);
    public static Defi DEFI_2 = new Defi(2L, "Défi 2", "Description du défi 2", TypeDefi.HEBDOMADAIRE, 100, 200, 0, null, null);
    public static Defi DEFI_3 = new Defi(3L, "Défi 3", "Description du défi 3", TypeDefi.MENSUEL, 200, 600, 10, null, null);
    public static Defi DEFI_4 = new Defi(4L, "Défi 4", "Description du défi 4", TypeDefi.ANNUEL, 500, 0, 100, null, null);
    public static List<Defi> listDefi = Arrays.asList(DEFI_1, DEFI_2, DEFI_3, DEFI_4);

    public static void addListDefiPersonneToPersonne(Personne personne) {
        personne.setDefisARealiser(List.of(
                new DefiPersonne(personne, DEFI_1),
                new DefiPersonne(personne, DEFI_2),
                new DefiPersonne(personne, DEFI_3),
                new DefiPersonne(personne, DEFI_4)
        ));
    }
}
