package bo;

public class CompteEpargne extends Compte {

    public float getTauxInteret() {
        return tauxInteret;
    }

    public void setTauxInteret(float tauxInteret) {
        this.tauxInteret = tauxInteret;
    }

    protected float tauxInteret;

    public CompteEpargne(int id, long solde, float tauxInteret) {
        super(id, solde);
        this.tauxInteret = tauxInteret;
    }


    @Override
    public void versement(int montant) {

    }

    @Override
    public void retrait(int montant) {

    }
}
