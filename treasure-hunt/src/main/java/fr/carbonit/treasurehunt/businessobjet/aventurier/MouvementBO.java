package fr.carbonit.treasurehunt.businessobjet.aventurier;

import fr.carbonit.treasurehunt.transverse.constantes.ActionEnum;

public class MouvementBO {

    private ActionEnum action;
    private Boolean estExecute;

    public ActionEnum getAction() {
        return action;
    }

    public void setAction(ActionEnum action) {
        this.action = action;
    }

    public Boolean isAlreadyExecuted() {
        return estExecute;
    }

    public void setAlreadyExecuted(Boolean alreadyExecuted) {
        estExecute = alreadyExecuted;
    }

    public MouvementBO(ActionEnum action, Boolean isAlreadyExecuted) {
        this.action = action;
        this.estExecute = isAlreadyExecuted;
    }
}
