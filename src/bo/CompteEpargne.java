package bo;

import java.util.TimerTask;

public class CompteEpargne extends Compte {

    //Attribut
    protected float tauxInteret;

    //Accesseurs
    public float getTauxInteret() {
        return tauxInteret;
    }

    public void setTauxInteret(float tauxInteret) {
        this.tauxInteret = tauxInteret;
    }

    //Constructeur
    public CompteEpargne() { }

    //Méthodes
    @Override
    public void versement(double montant) {
        this.solde = this.solde + montant;
    }

    @Override
    public void retrait(double montant) {
        this.solde = this.solde - montant;
    }

    public void calculInteret() {
        this.solde = this.solde * (this.tauxInteret / 100 + 1);
    }

}
