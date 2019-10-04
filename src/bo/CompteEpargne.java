package bo;

public class CompteEpargne extends Compte {
    protected float tauxInteret;

    public CompteEpargne(int id, long solde, float tauxInteret) {
        super(id, solde);
        this.tauxInteret = tauxInteret;
    }

}
