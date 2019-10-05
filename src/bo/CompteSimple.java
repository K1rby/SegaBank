package bo;

public class CompteSimple extends Compte {

    //Attribut
    protected int decouvert;

    //Accesseurs
    public int getDecouvert() {
        return decouvert;
    }

    public void setDecouvert(int decouvert) {
        this.decouvert = decouvert;
    }

    //Constructeur
    public CompteSimple(int id, long solde, int decouvert) {
        super(id, solde);
        this.decouvert = decouvert;
    }

    //Méthodes
    @Override
    public void versement(int montant) {
        this.solde = this.solde + montant;
    }

    @Override
    public void retrait(int montant) {
        if((this.solde - montant) < this.decouvert){
            this.solde = this.solde - montant;
        }
    }
}
