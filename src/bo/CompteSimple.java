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
    public CompteSimple() { }

    //Méthodes
    @Override
    public void versement(double montant) {
        this.solde = this.solde + montant;
    }

    @Override
    public void retrait(double montant) {
        if((this.solde - montant) > this.decouvert){
            this.solde = this.solde - montant;
            System.out.println("Retrait effectué avec succès !");
        }else{
            System.out.println("Retrait annulé, vous avez dépassé le découvert !");

        }
    }
}
