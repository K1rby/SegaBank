package bo;

public class CompteSimple extends Compte {

    public int getDecouvert() {
        return decouvert;
    }

    public void setDecouvert(int decouvert) {
        this.decouvert = decouvert;
    }

    protected int decouvert;

    public CompteSimple(int id, long solde, int decouvert) {
        super(id, solde);
        this.decouvert = decouvert;
    }

    @Override
    public void versement(int montant) {

    }

    @Override
    public void retrait(int montant) {

    }
}
