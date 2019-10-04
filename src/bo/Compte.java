package bo;

public abstract class Compte {

    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSolde() {
        return solde;
    }

    public void setSolde(long solde) {
        this.solde = solde;
    }

    protected long solde;

    public Compte(int id, long solde) {
        this.id = id;
        this.solde = solde;
    }

    public abstract void versement(int montant);

    public abstract void retrait(int montant);

}
