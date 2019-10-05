package bo;

public abstract class Compte {

    //Attributs
    protected int id;
    protected double solde;
    protected String type;

    //Accesseurs
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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
    public Compte(int id, long solde, String type) {
        this.id = id;
        this.solde = solde;
        this.type = type;
    }

    //Méthodes
    public abstract void versement(int montant);

    public abstract void retrait(int montant);

}
