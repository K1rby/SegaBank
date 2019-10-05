import bo.*;
import dal.IDAO;
import dao.CompteEpargneDAO;
import dao.ComptePayantDAO;
import dao.CompteSimpleDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    private static Scanner sc = new Scanner(System.in);
    //private static final String BACKUPS_DIR = "./resources/backups/";
    private static String type_compte_copie = "";

    public static void dspMainMenu() {

        int response;
        boolean first = true;
        do {
            if (!first) {
                System.out.println("Mauvais choix... merci de recommencer !");
            }
            System.out.println("=================================================");
            System.out.println("=========== MENU - GESTION DES AGENCES ==========");
            System.out.println("=================================================");
            System.out.println("1 - Ajouter une Agence");
            System.out.println("2 - Gestion d'une Agence");
            System.out.println("3 - Quitter");
            System.out.print("Entrez votre choix : ");
            try {
                response = sc.nextInt();
            } catch (InputMismatchException e) {
                response = -1;
            } finally {
                sc.nextLine();
            }
            first = false;
        } while (response < 1 || response > 3);

        switch (response) {
            case 1:
                createAgence();
                break;
            case 2:
                dspSecondAgenceMenu();
                break;
            case 3:
                //dspThirdComptePayantMenu();
                break;
        }

    }

    private static void createAgence() {
        //IDAO<Long, Agence> daoAgence = new AgenceDAO();

        System.out.println("======================================");
        System.out.println("======== CREATION D'UNE AGENCE =======");
        System.out.println("======================================");
        Agence agence = null;
        System.out.print("Entrez le code l'agence : ");
        agence.setCode(sc.nextLine());
        System.out.print("Entrez l'adresse de l'agence : ");
        agence.setAdresse(sc.nextLine());
        //dao.create(agence)
        System.out.println("Agence créée avec succès!");
        dspMainMenu();
    }

    private static void dspSecondAgenceMenu() {

        //IDAO<Long, Agence> daoAgence = new AgenceDAO();

        System.out.println("======================================");
        System.out.println("========== LISTE DES AGENCES =========");
        System.out.println("======================================");

        //List<Agence> listAgences = daoAgence.findAll();
      /*  for (Agence agence : listAgences) {
            //Faire le string builder pour l'affichage
            System.out.println(agence);
        }
*/
        System.out.println("======================================");


        dspMainMenu();
    }

    private static void dspMenuAccount(String type_compte) {

        type_compte_copie = type_compte;
        int response;
        boolean first = true;
        byte choix_compte = 0;
        do {
            if (!first) {
                System.out.println("Mauvais choix... merci de recommencer !");
            }

            System.out.println("======================================");
            System.out.println("========== GESTION DES COMPTES =========");
            System.out.println("======================================");
            System.out.println("1 - Ajouter un Compte");
            System.out.println("2 - Modifier un Compte");
            System.out.println("3 - Supprimer un Compte");
            System.out.println("4 - Lister les Comptes");
            System.out.println("5 - Effectuer un virement");
            System.out.println("6 - Effectuer un retrait");
            if (type_compte == "epargne") {
                System.out.println("7 - Effectuer le calcul des interets");
                System.out.println("8 - Quitter");
                choix_compte = 8;
            }
            else {

                System.out.println("7 - Quitter");
                choix_compte = 7;
            }
            System.out.print("Entrez votre choix : ");
            try {
                response = sc.nextInt();
            } catch (InputMismatchException e) {
                response = -1;
            } finally {
                sc.nextLine();
            }
            first = false;
        } while (response < 1 || response > choix_compte);

        switch (response) {
            case 1:
                createAccount();
                break;
            case 2:
                dspSecondAgenceMenu();
                break;
            case 3:
               // dspThirdComptePayantMenu();
                break;
        }

    }

    private static void createAccount() {

        System.out.println("======================================");
        System.out.println("======== CREATION D'UN COMPTE =======");
        System.out.println("======================================");

        IDAO<Long, CompteEpargne> dao_compteEpargne = new CompteEpargneDAO();
        IDAO<Long, ComptePayant> dao_comptePayant = new ComptePayantDAO();
        IDAO<Long, CompteSimple> dao_compteSimple = new CompteSimpleDAO();
        System.out.println("Choisir le type de compte : ");
        System.out.println("1 - Epargne");
        System.out.println("2 - Payant");
        System.out.println("3 - Simple");
        byte type = sc.nextByte();

        if (type == 1) {

            CompteEpargne compte_epargne = new CompteEpargne();
            compte_epargne.setType("Epargne");
            compte_epargne.setSolde(0);
            compte_epargne.setTauxInteret((float) 0.02);
            compte_epargne.setIdAgence(1);

            try {
                dao_compteEpargne.create(compte_epargne);
            } catch (SQLException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else if (type == 2) {

            ComptePayant compte_payant = new ComptePayant();
            compte_payant.setType("Payant");
            compte_payant.setSolde(0);
            compte_payant.setIdAgence(1);

            try {
                dao_comptePayant.create(compte_payant);
            } catch (SQLException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else if (type == 3) {

            CompteSimple compte_simple = new CompteSimple();
            compte_simple.setType("Simple");
            compte_simple.setSolde(0);
            compte_simple.setDecouvert(-400);
            compte_simple.setIdAgence(1);

            try {
                dao_compteSimple.create(compte_simple);
            } catch (SQLException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Compte créée avec succès!");
        dspMenuAccount(type_compte_copie);
    }

    public static void main(String[] args) {

        dspMenuAccount("epargne");
        System.out.println("Hello world");
    }
}
