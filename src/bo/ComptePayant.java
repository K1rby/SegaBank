package bo;

public class ComptePayant extends Compte {

    //Constructeur
    public ComptePayant(){}

    //Méthodes
    @Override
    public void versement(double montant) {
        this.solde = this.solde + (montant * 0.95);
    }

    @Override
    public void retrait(double montant) {
        this.solde = this.solde - (montant * 1.05);
    }

}
