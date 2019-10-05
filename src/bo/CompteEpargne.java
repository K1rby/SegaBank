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
    public CompteEpargne(int id, long solde, String type, float tauxInteret) {
        super(id, solde, type);
        this.tauxInteret = tauxInteret;
    }

    //Méthodes
    @Override
    public void versement(int montant) {
        this.solde = this.solde + montant;
    }

    @Override
    public void retrait(int montant) {
        this.solde = this.solde - montant;
    }

    public void calculInteret() {
        this.solde = this.solde * (this.tauxInteret / 100 + 1);
    }

}
