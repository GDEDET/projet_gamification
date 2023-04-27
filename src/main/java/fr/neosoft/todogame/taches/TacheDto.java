package fr.neosoft.todogame.taches;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
public class TacheDto {

    private String description;

    private Date dateEcheance;

    private Priorite priorite;

    private Difficulte difficulte;
}
