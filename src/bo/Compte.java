package bo;

public abstract class Compte {

    //Attributs
    protected int id;
    protected double solde;

    //Accesseurs
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    //Constructeur
    public Compte(int id, long solde) {
        this.id = id;
        this.solde = solde;
    }

    //Méthodes
    public abstract void versement(int montant);

    public abstract void retrait(int montant);

}
