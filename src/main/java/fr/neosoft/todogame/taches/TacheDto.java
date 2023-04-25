package fr.neosoft.todogame.taches;

import lombok.Data;

import java.util.Date;

@Data
public class TacheDto {

    private String description;

    private Date dateEcheance;

    private Priorite priorite;

    private Difficulte difficulte;

}
