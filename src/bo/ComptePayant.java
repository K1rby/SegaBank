package bo;

public class ComptePayant extends Compte {

    //Constructeur
    public ComptePayant(int id, long solde, String type) {
        super(id, solde, type);
    }

    //Méthodes
    @Override
    public void versement(int montant) {
        this.solde = this.solde + (montant * 0.95);
    }

    @Override
    public void retrait(int montant) {
        this.solde = this.solde - (montant * 1.05);
    }

}
