package bo;

public class CompteSimple extends Compte {

    protected int decouvert;

    public CompteSimple(int id, long solde, int decouvert) {
        super(id, solde);
        this.decouvert = decouvert;
    }

}
