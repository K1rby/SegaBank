package bo;

public abstract class Compte {

    //Attributs
    protected int id;
    protected double solde;
    protected String type;
    protected int idAgence;

    public int getIdAgence() {
        return idAgence;
    }

    public void setIdAgence(int idAgence) {
        this.idAgence = idAgence;
    }

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
    public Compte() { }

    //Méthodes
    public abstract void versement(int montant);

    public abstract void retrait(int montant);

}
