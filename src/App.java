import bo.*;
import dal.IDAO;
import dao.CompteEpargneDAO;
import dao.ComptePayantDAO;
import dao.CompteSimpleDAO;
import dao.AgenceDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    private static Scanner sc = new Scanner(System.in);
    //private static final String BACKUPS_DIR = "./resources/backups/";
    private static String type_compte_copie = "";
    private static List<CompteEpargne> list_epargne = new ArrayList<>();
    private static List<ComptePayant> list_payant = new ArrayList<>();
    private static List<CompteSimple> list_simple = new ArrayList<>();

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
                dspChoixAgenceMenu();
                break;
            case 3:
                //dspThirdComptePayantMenu();
                break;
        }

    }

    private static void createAgence() {

        IDAO<Long, Agence> daoAgence = new AgenceDAO();
        String code;
        String adresse;

        System.out.println("======================================");
        System.out.println("======== CREATION D'UNE AGENCE =======");
        System.out.println("======================================");

        System.out.print("Entrez le code l'agence : ");
        code = sc.nextLine();
        System.out.print("Entrez l'adresse de l'agence : ");
        adresse = sc.nextLine();

        Agence agence = new Agence(code, adresse);

        try {
            daoAgence.create(agence);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("Agence créée avec succès!");
        dspMainMenu();
    }

    private static void dspChoixAgenceMenu() {

        IDAO<Long, Agence> daoAgence = new AgenceDAO();

        System.out.println("======================================");
        System.out.println("========== LISTE DES AGENCES =========");
        System.out.println("======================================");

        List<Agence> listAgences = new ArrayList<>();
        try {
            listAgences = daoAgence.findAll();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.err.println(e.getMessage());
        }

        int i = 1;
        for (Agence agence : listAgences) {
            StringBuilder builder = new StringBuilder(i + " - ");
            builder.append("Code : " + agence.getCode() + " ");
            builder.append("Adresse : " + agence.getAdresse());
            System.out.println(builder.toString());
        }

        boolean first = true;
        int choixAgence;
        int size = listAgences.size();
        do {
            if (!first) {
                System.out.println("Mauvais choix... merci de recommencer !");
            }
            System.out.print("Veuillez saisir le numero correspondant à votre agence : ");
            try {
                choixAgence = sc.nextInt();
            } catch (InputMismatchException e) {
                choixAgence = -1;
            } finally {
                sc.nextLine();
            }
            first = false;
        } while (choixAgence < 1 || choixAgence > size);

        Agence agence = listAgences.get((choixAgence - 1));

        dspAgenceMenu(agence);
    }


    private static void dspAgenceMenu(Agence agence) {

        int response;
        boolean first = true;
        do {
            if (!first) {
                System.out.println("Mauvais choix... merci de recommencer !");
            }
            System.out.println("======== BIENVENUE DANS L'ESPACE DE GESTION DE L'AGENCE " + agence.getCode() + " ========");
            System.out.println("1 - Modifier l'agence");
            System.out.println("2 - Supprimer l'agence");
            System.out.println("3 - Gestion des comptes simple");
            System.out.println("4 - Gestion des  comptes payant");
            System.out.println("5 - Gestion des comptes épargnes");
            System.out.println("6 - Quitter");

            System.out.print("Entrez votre choix : ");
            try {
                response = sc.nextInt();
            } catch (InputMismatchException e) {
                response = -1;
            } finally {
                sc.nextLine();
            }
            first = false;
        } while (response < 1 || response > 6);

        switch (response) {
            case 1:
                updateAgence(agence);
                break;
            case 2:
                removeAgence(agence);
                break;
            case 3:
                dspMenuAccount("simple", agence.getId());
                break;
            case 4:
                dspMenuAccount("payant", agence.getId());
                break;
            case 5:
                dspMenuAccount("epargne", agence.getId());
                break;
            case 6:
                dspMainMenu();
                break;
        }
    }

    private static void updateAgence(Agence agence) {

        IDAO<Long, Agence> daoAgence = new AgenceDAO();

        System.out.println("======================================");
        System.out.println("====== MODIFICATION D'UNE AGENCE =====");
        System.out.println("======================================");

        System.out.printf("Entrez le code (%s): ", agence.getCode());
        String code = sc.nextLine();
        if (code != null && !code.isEmpty()) {
            agence.setCode(code);
        }
        System.out.printf("Entrez l'adresse (%s): ", agence.getAdresse());
        String adresse = sc.nextLine();
        if (adresse != null && !adresse.isEmpty()) {
            agence.setAdresse(adresse);
        }

        try {
            daoAgence.update(agence);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("Agence modifiée avec succès!");
        dspAgenceMenu(agence);
    }

    private static void removeAgence(Agence agence) {

        IDAO<Long, Agence> daoAgence = new AgenceDAO();

        System.out.println("======================================");
        System.out.println("====== SUPPRESSION D'UNE AGENCE =====");
        System.out.println("======================================");

        String choix;
        boolean first = true;
        do {
            if (!first) {
                System.out.println("Mauvais choix... merci de recommencer !");
            }
            System.out.printf("Êtes vous bien sûr de vouloir supprimer cette agence (O/N) ?");
            System.out.print("Entrez votre choix : ");
            try {
                choix = sc.nextLine();
            } finally {
                sc.nextLine();
            }
            first = false;
        } while (choix != "O" || choix != "N");

        switch (choix) {
            case "O":
                try {
                    daoAgence.remove(agence);
                } catch (IOException | ClassNotFoundException | SQLException e) {
                    System.err.println(e.getMessage());
                }

                System.out.println("Agence supprimée avec succès!");
                dspAgenceMenu(agence);
                break;
            case "N":
                dspAgenceMenu(agence);
                break;
        }
    }

    private static void dspMenuAccount(String type_compte, int id_agence) {

        type_compte_copie = type_compte;
        int response;
        boolean first = true;
        byte choix_compte = 0;

        do {
            if (!first) {
                System.out.println("Mauvais choix... merci de recommencer !");
            }

            System.out.println("======================================");
            System.out.println("========== GESTION DES COMPTES =======");
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
            } else {

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
                createAccount(type_compte, id_agence);
                break;
            case 2:
                updateAccount(type_compte, id_agence);
                break;
            case 3:
                removeAccount(type_compte, id_agence);
                break;
            case 4:
                listAccount(type_compte, id_agence);
                break;
        }

    }

    private static void createAccount(String type_compte, int id_agence) {

        System.out.println("======================================");
        System.out.println("======== CREATION D'UN COMPTE ========");
        System.out.println("======================================");

        IDAO<Long, CompteEpargne> dao_compteEpargne = new CompteEpargneDAO();
        IDAO<Long, ComptePayant> dao_comptePayant = new ComptePayantDAO();
        IDAO<Long, CompteSimple> dao_compteSimple = new CompteSimpleDAO();


        if (type_compte == "epargne") {

            CompteEpargne compte_epargne = new CompteEpargne();
            compte_epargne.setType("Epargne");
            compte_epargne.setSolde(0);
            compte_epargne.setTauxInteret((float) 0.02);
            compte_epargne.setIdAgence(id_agence);

            try {
                dao_compteEpargne.create(compte_epargne);
            } catch (SQLException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (type_compte == "payant") {

            ComptePayant compte_payant = new ComptePayant();
            compte_payant.setType("Payant");
            compte_payant.setSolde(0);
            compte_payant.setIdAgence(id_agence);

            try {
                dao_comptePayant.create(compte_payant);
            } catch (SQLException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (type_compte == "simple") {

            CompteSimple compte_simple = new CompteSimple();
            compte_simple.setType("Simple");
            compte_simple.setSolde(0);
            compte_simple.setDecouvert(-400);
            compte_simple.setIdAgence(id_agence);

            try {
                dao_compteSimple.create(compte_simple);
            } catch (SQLException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Compte créée avec succès!");
        dspMenuAccount(type_compte_copie, id_agence);
    }

    private static void updateAccount(String type_compte, int id_agence) {

        System.out.println("======================================");
        System.out.println("====== MODIFICATION D'UN COMPTE ======");
        System.out.println("======================================");

        IDAO<Long, CompteEpargne> dao_compteEpargne = new CompteEpargneDAO();
        IDAO<Long, ComptePayant> dao_comptePayant = new ComptePayantDAO();
        IDAO<Long, CompteSimple> dao_compteSimple = new CompteSimpleDAO();

        if (type_compte == "epargne") {

            list(type_compte);
            System.out.println("Choisissez le compte à modifier ...");
            boolean first = true;
            int response, size = list_epargne.size();
            do {
                if (!first) {
                    System.out.println("Mauvais choix... merci de recommencer !");
                }
                System.out.print("Votre choix : ");
                try {
                    response = sc.nextInt();
                } catch (InputMismatchException e) {
                    response = -1;
                } finally {
                    sc.nextLine();
                }
                first = false;
            } while (response < 1 || response > size);

            CompteEpargne compte_epargne = new CompteEpargne();

            CompteEpargne compte_id = list_epargne.get(response - 1);


            System.out.printf("Entrez le taux interet (%s): ", compte_id.getTauxInteret());
            float code = sc.nextFloat();

            compte_epargne.setTauxInteret(code);
            System.out.printf("Entrez le solde (%s): ", compte_id.getSolde());
            int solde = sc.nextInt();
            compte_epargne.setSolde(solde);
            compte_epargne.setType("Epargne");
            compte_epargne.setIdAgence(id_agence);
            compte_epargne.setId(compte_id.getId());

            try {
                dao_compteEpargne.update(compte_epargne);
            } catch (SQLException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (type_compte == "payant") {

            list(type_compte);
            System.out.println("Choisissez le compte à modifier ...");
            boolean first = true;
            int response, size = list_payant.size();
            do {
                if (!first) {
                    System.out.println("Mauvais choix... merci de recommencer !");
                }
                System.out.print("Votre choix : ");
                try {
                    response = sc.nextInt();
                } catch (InputMismatchException e) {
                    response = -1;
                } finally {
                    sc.nextLine();
                }
                first = false;
            } while (response < 1 || response > size);

            ComptePayant compte_payant = new ComptePayant();

            ComptePayant compte_id = list_payant.get(response - 1);

            System.out.printf("Entrez le solde (%s): ", compte_id.getSolde());
            int solde = sc.nextInt();
            compte_payant.setSolde(solde);
            compte_payant.setType("Payant");
            compte_payant.setIdAgence(id_agence);
            compte_payant.setId(compte_id.getId());

            try {
                dao_comptePayant.update(compte_payant);
            } catch (SQLException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (type_compte == "simple") {

            list(type_compte);
            System.out.println("Choisissez le compte à modifier ...");
            boolean first = true;
            int response, size = list_simple.size();
            System.out.println(list_simple.size());
            do {
                if (!first) {
                    System.out.println("Mauvais choix... merci de recommencer !");
                }
                System.out.print("Votre choix : ");
                try {
                    response = sc.nextInt();
                } catch (InputMismatchException e) {
                    response = -1;
                } finally {
                    sc.nextLine();
                }
                first = false;
            } while (response < 1 || response > size);

            CompteSimple compte_simple = new CompteSimple();

            CompteSimple compte_id = list_simple.get(response - 1);


            System.out.printf("Entrez le découvert (%s): ", compte_id.getDecouvert());
            int code = sc.nextInt();

            compte_simple.setDecouvert(code);
            System.out.printf("Entrez le solde (%s): ", compte_id.getSolde());
            int solde = sc.nextInt();
            compte_simple.setSolde(solde);
            compte_simple.setType("Simple");
            compte_simple.setIdAgence(id_agence);
            compte_simple.setId(compte_id.getId());

            try {
                dao_compteSimple.update(compte_simple);
            } catch (SQLException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Compte modifier avec succès!");
        dspMenuAccount(type_compte_copie, id_agence);

    }

    private static void removeAccount(String typeCompte, int id_agence) {

        System.out.println("======================================");
        System.out.println("====== SUPPRESION D'UN COMPTE ======");
        System.out.println("======================================");

        IDAO<Long, CompteEpargne> dao_compteEpargne = new CompteEpargneDAO();
        IDAO<Long, ComptePayant> dao_comptePayant = new ComptePayantDAO();
        IDAO<Long, CompteSimple> dao_compteSimple = new CompteSimpleDAO();

        if (typeCompte == "epargne") {
            list(typeCompte);
            System.out.println("Choisissez le compte à supprimer ...");
            boolean first = true;
            int response, size = list_epargne.size();
            do {
                if (!first) {
                    System.out.println("Mauvais choix... merci de recommencer !");
                }
                System.out.print("Votre choix : ");
                try {
                    response = sc.nextInt();
                } catch (InputMismatchException e) {
                    response = -1;
                } finally {
                    sc.nextLine();
                }
                first = false;
            } while (response < 1 || response > size);

            try {
                CompteEpargne compte_id = list_epargne.get(response - 1);
                dao_compteEpargne.remove(compte_id);
            } catch (IOException | ClassNotFoundException | SQLException e) {
                System.err.println(e.getMessage());
            }

            System.out.println("Compte supprimée avec succès!");
            dspMenuAccount(typeCompte, id_agence);
            dspMenuAccount(typeCompte, id_agence);

        } else if (typeCompte == "simple") {

            list(typeCompte);
            System.out.println("Choisissez le compte à supprimer ...");
            boolean first = true;
            int response, size = list_simple.size();
            do {
                if (!first) {
                    System.out.println("Mauvais choix... merci de recommencer !");
                }
                System.out.print("Votre choix : ");
                try {
                    response = sc.nextInt();
                } catch (InputMismatchException e) {
                    response = -1;
                } finally {
                    sc.nextLine();
                }
                first = false;
            } while (response < 1 || response > size);

            try {
                CompteSimple compte_id = list_simple.get(response - 1);
                dao_compteSimple.remove(compte_id);
            } catch (IOException | ClassNotFoundException | SQLException e) {
                System.err.println(e.getMessage());
            }

            System.out.println("Compte supprimée avec succès!");
            dspMenuAccount(typeCompte, id_agence);

            dspMenuAccount(typeCompte, id_agence);

        } else if (typeCompte == "payant") {
            list(typeCompte);
            System.out.println("Choisissez le compte à supprimer ...");
            boolean first = true;
            int response, size = list_payant.size();
            do {
                if (!first) {
                    System.out.println("Mauvais choix... merci de recommencer !");
                }
                System.out.print("Votre choix : ");
                try {
                    response = sc.nextInt();
                } catch (InputMismatchException e) {
                    response = -1;
                } finally {
                    sc.nextLine();
                }
                first = false;
            } while (response < 1 || response > size);

            try {
                ComptePayant compte_id = list_payant.get(response - 1);
                dao_comptePayant.remove(compte_id);
            } catch (IOException | ClassNotFoundException | SQLException e) {
                System.err.println(e.getMessage());
            }

            System.out.println("Compte supprimée avec succès!");
            dspMenuAccount(typeCompte, id_agence);
            dspMenuAccount(typeCompte, id_agence);
        }

    }

    private static void listAccount(String typeCompte, int id_agence) {

        System.out.println("======================================");
        System.out.println("====== LISTE DES COMPTES ==============");
        System.out.println("======================================");
        list(typeCompte);
        dspMenuAccount(typeCompte, id_agence);
    }

    private static void list(String typeCompte) {

        IDAO<Long, CompteEpargne> dao_compteEpargne = new CompteEpargneDAO();
        IDAO<Long, ComptePayant> dao_comptePayant = new ComptePayantDAO();
        IDAO<Long, CompteSimple> dao_compteSimple = new CompteSimpleDAO();

        int i = 1;

        if (typeCompte == "epargne") {

            try {
                list_epargne = dao_compteEpargne.findAll();
            } catch (SQLException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            for (CompteEpargne s : list_epargne) {

                StringBuilder builder = new StringBuilder(i + " - ");
                builder.append("Id Compte : " + s.getId() + " ");
                builder.append("Taux Interet : " + s.getTauxInteret() + " ");
                builder.append("Solde : " + s.getSolde());
                System.out.println(builder.toString());
                i++;
            }
        } else if (typeCompte == "payant") {

            try {
                list_payant = dao_comptePayant.findAll();
            } catch (SQLException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            for (ComptePayant s : list_payant) {

                StringBuilder builder = new StringBuilder(i + " - ");
                builder.append("Id Compte : " + s.getId() + " ");
                builder.append("Solde : " + s.getSolde());
                System.out.println(builder.toString());
                i++;
            }
        } else if (typeCompte == "simple") {

            try {
                list_simple = dao_compteSimple.findAll();
            } catch (SQLException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            for (CompteSimple s : list_simple) {

                StringBuilder builder = new StringBuilder(i + " - ");
                builder.append("Id Compte : " + s.getId() + " ");
                builder.append("Découvert : " + s.getDecouvert() + " ");
                builder.append("Solde : " + s.getSolde());
                System.out.println(builder.toString());
                i++;
            }
        }
    }


    public static void main(String[] args) {
        dspMainMenu();
    }
}
