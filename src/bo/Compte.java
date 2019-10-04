package bo;

public abstract class Compte {

    protected int id;
    protected long solde;

    public Compte(int id, long solde) {
        this.id = id;
        this.solde = solde;
    }

}
