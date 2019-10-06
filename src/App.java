import bo.*;
import dal.IDAO;
import dao.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.*;

public class App {

    private static Scanner sc = new Scanner(System.in).useLocale(Locale.US);
    //private static final String BACKUPS_DIR = "./resources/backups/";
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
            listAgences = daoAgence.findAllAgences();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.err.println(e.getMessage());
        }

        int i = 1;
        for (Agence agence : listAgences) {
            StringBuilder builder = new StringBuilder(i + " - ");
            builder.append("Code : " + agence.getCode() + " ");
            builder.append("Adresse : " + agence.getAdresse());
            System.out.println(builder.toString());
            i++;
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

        Integer choix;
        boolean first = true;
        do {
            if (!first) {
                System.out.println("Mauvais choix... merci de recommencer !");
            }
            System.out.println("Êtes vous bien sûr de vouloir supprimer cette agence ?");
            System.out.println("1 - Oui");
            System.out.println("2 - Non");
            System.out.print("Entrez votre choix : ");

            try {
                choix = sc.nextInt();
            } catch (InputMismatchException e) {
                choix = -1;
            } finally {
                sc.nextLine();
            }
            first = false;
        } while (choix < 1 || choix > 2);

        switch (choix) {
            case 1:
                try {
                    daoAgence.remove(agence);
                } catch (IOException | ClassNotFoundException | SQLException e) {
                    System.err.println(e.getMessage());
                }

                System.out.println("Agence supprimée avec succès!");
                dspMainMenu();
                break;
            case 2:
                dspAgenceMenu(agence);
                break;
        }
    }

    private static void dspMenuAccount(String type_compte, int id_agence) {

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
            System.out.println("5 - Effectuer un versement");
            System.out.println("6 - Effectuer un retrait");
            System.out.println("7 - Export CSV des opérations d'un compte");
            if (type_compte == "epargne") {
                System.out.println("8 - Effectuer le calcul des interets");
                System.out.println("9 - Quitter");
                choix_compte = 9;
            } else {

                System.out.println("8 - Quitter");
                choix_compte = 8;
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
            case 5:
                doPayment(type_compte, id_agence);
                break;
            case 6:
                doWithdrawal(type_compte, id_agence);
                break;
            case 7:
                doCSVExport(type_compte, id_agence);
                break;
            case 8:
                if (type_compte == "epargne") {
                    doAdvantage(type_compte, id_agence);
                } else {
                    dspMainMenu();
                }
                break;
            case 9:
                dspMainMenu();
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
            compte_epargne.setType("epargne");
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
            compte_payant.setType("payant");
            compte_payant.setSolde(0);
            compte_payant.setIdAgence(id_agence);

            try {
                dao_comptePayant.create(compte_payant);
            } catch (SQLException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (type_compte == "simple") {

            CompteSimple compte_simple = new CompteSimple();
            compte_simple.setType("simple");
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
        dspMenuAccount(type_compte, id_agence);
    }

    private static void updateAccount(String type_compte, int id_agence) {

        System.out.println("======================================");
        System.out.println("====== MODIFICATION D'UN COMPTE ======");
        System.out.println("======================================");

        IDAO<Long, CompteEpargne> dao_compteEpargne = new CompteEpargneDAO();
        IDAO<Long, ComptePayant> dao_comptePayant = new ComptePayantDAO();
        IDAO<Long, CompteSimple> dao_compteSimple = new CompteSimpleDAO();

        if (type_compte == "epargne") {

            list(type_compte, id_agence);
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
            double code = sc.nextDouble();

            compte_epargne.setTauxInteret((float)code);
            System.out.printf("Entrez le solde (%s): ", compte_id.getSolde());
            int solde = sc.nextInt();
            compte_epargne.setSolde(solde);
            compte_epargne.setType("epargne");
            compte_epargne.setIdAgence(id_agence);
            compte_epargne.setId(compte_id.getId());

            try {
                dao_compteEpargne.update(compte_epargne);
            } catch (SQLException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (type_compte == "payant") {

            list(type_compte, id_agence);
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
            compte_payant.setType("payant");
            compte_payant.setIdAgence(id_agence);
            compte_payant.setId(compte_id.getId());

            try {
                dao_comptePayant.update(compte_payant);
            } catch (SQLException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (type_compte == "simple") {

            list(type_compte, id_agence);
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
            compte_simple.setType("simple");
            compte_simple.setIdAgence(id_agence);
            compte_simple.setId(compte_id.getId());

            try {
                dao_compteSimple.update(compte_simple);
            } catch (SQLException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Compte modifier avec succès!");
        dspMenuAccount(type_compte, id_agence);

    }

    private static void removeAccount(String typeCompte, int id_agence) {

        System.out.println("======================================");
        System.out.println("====== SUPPRESION D'UN COMPTE ======");
        System.out.println("======================================");

        IDAO<Long, CompteEpargne> dao_compteEpargne = new CompteEpargneDAO();
        IDAO<Long, ComptePayant> dao_comptePayant = new ComptePayantDAO();
        IDAO<Long, CompteSimple> dao_compteSimple = new CompteSimpleDAO();

        if (typeCompte == "epargne") {
            list(typeCompte, id_agence);
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

            list(typeCompte, id_agence);
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
            list(typeCompte, id_agence);
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
        list(typeCompte, id_agence);
        dspMenuAccount(typeCompte, id_agence);
    }

    private static void list(String typeCompte, int idAgence) {

        IDAO<Long, CompteEpargne> dao_compteEpargne = new CompteEpargneDAO();
        IDAO<Long, ComptePayant> dao_comptePayant = new ComptePayantDAO();
        IDAO<Long, CompteSimple> dao_compteSimple = new CompteSimpleDAO();

        int i = 1;

        if (typeCompte == "epargne") {

            try {
                list_epargne = dao_compteEpargne.findAll(idAgence);
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
                list_payant = dao_comptePayant.findAll(idAgence);
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
                list_simple = dao_compteSimple.findAll(idAgence);
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

    private static void doPayment(String type, int idAgence) {

        System.out.println("=====================================");
        System.out.println("============= VERSEMENT =============");
        System.out.println("=====================================");

        boolean first = true;
        int choixAccount;
        int size;
        int i;
        double amount;
        Operation op = new Operation();
        IDAO<Long, Operation> daoOperation = new OperationDAO();

        switch (type) {
            case "simple":
                IDAO<Long, CompteSimple> daoSimpleAccount = new CompteSimpleDAO();

                List<CompteSimple> listSimpleAccounts = new ArrayList<>();
                try {
                    listSimpleAccounts = daoSimpleAccount.findAll(idAgence);
                } catch (IOException | ClassNotFoundException | SQLException e) {
                    System.err.println(e.getMessage());
                }

                i = 1;
                for (CompteSimple simpleAccount : listSimpleAccounts) {
                    StringBuilder builder = new StringBuilder(i + " - ");
                    builder.append("ID : " + simpleAccount.getId() + " ");
                    builder.append("Solde : " + simpleAccount.getSolde() + " €");
                    builder.append("Decouvert : " + simpleAccount.getDecouvert() + " €");
                    System.out.println(builder.toString());
                    i++;
                }

                first = true;
                size = listSimpleAccounts.size();
                do {
                    if (!first) {
                        System.out.println("Mauvais choix... merci de recommencer !");
                    }
                    System.out.print("Veuillez saisir le numero correspondant au compte souhaité : ");
                    try {
                        choixAccount = sc.nextInt();
                    } catch (InputMismatchException e) {
                        choixAccount = -1;
                    } finally {
                        sc.nextLine();
                    }
                    first = false;
                } while (choixAccount < 1 || choixAccount > size);

                CompteSimple account = listSimpleAccounts.get((choixAccount - 1));

                System.out.println("================================");
                System.out.print("Veuillez saisir le montant total du versement : ");
                amount = sc.nextDouble();

                account.versement(amount);

                op.setIdAgence(idAgence);
                op.setIdCompte(account.getId());
                op.setType("retrait");
                op.setMontant((int)amount);

                try {
                    daoSimpleAccount.update(account);
                    daoOperation.create(op);
                } catch (IOException | ClassNotFoundException | SQLException e) {
                    System.err.println(e.getMessage());
                }

                System.out.println("Versement effectué avec succès !");
                dspMenuAccount(type, idAgence);

                break;
            case "payant":

                IDAO<Long, ComptePayant> daoPayingAccount = new ComptePayantDAO();

                List<ComptePayant> listPayingAccounts = new ArrayList<>();
                try {
                    listPayingAccounts = daoPayingAccount.findAll(idAgence);
                } catch (IOException | ClassNotFoundException | SQLException e) {
                    System.err.println(e.getMessage());
                }

                i = 1;
                for (ComptePayant payingAccount : listPayingAccounts) {
                    StringBuilder builder = new StringBuilder(i + " - ");
                    builder.append("ID : " + payingAccount.getId() + " ");
                    builder.append("Solde : " + payingAccount.getSolde() + " €");
                    System.out.println(builder.toString());
                    i++;
                }

                first = true;
                size = listPayingAccounts.size();
                do {
                    if (!first) {
                        System.out.println("Mauvais choix... merci de recommencer !");
                    }
                    System.out.print("Veuillez saisir le numero correspondant au compte souhaité : ");
                    try {
                        choixAccount = sc.nextInt();
                    } catch (InputMismatchException e) {
                        choixAccount = -1;
                    } finally {
                        sc.nextLine();
                    }
                    first = false;
                } while (choixAccount < 1 || choixAccount > size);

                ComptePayant payingAccount = listPayingAccounts.get((choixAccount - 1));

                System.out.println("================================");
                System.out.print("Veuillez saisir le montant total du versement : ");
                amount = sc.nextDouble();

                payingAccount.versement(amount);

                op.setIdAgence(idAgence);
                op.setIdCompte(payingAccount.getId());
                op.setType("retrait");
                op.setMontant((int)amount);

                try {
                    daoPayingAccount.update(payingAccount);
                    daoOperation.create(op);
                } catch (IOException | ClassNotFoundException | SQLException e) {
                    System.err.println(e.getMessage());
                }

                System.out.println("Versement effectué avec succès !");
                dspMenuAccount(type, idAgence);

                break;
            case "epargne":

                IDAO<Long, CompteEpargne> daoSavingAccount = new CompteEpargneDAO();

                List<CompteEpargne> listSavingAccounts = new ArrayList<>();
                try {
                    listSavingAccounts = daoSavingAccount.findAll(idAgence);
                } catch (IOException | ClassNotFoundException | SQLException e) {
                    System.err.println(e.getMessage());
                }

                i = 1;
                for (CompteEpargne savingAccount : listSavingAccounts) {
                    StringBuilder builder = new StringBuilder(i + " - ");
                    builder.append("ID : " + savingAccount.getId() + " ");
                    builder.append("Solde : " + savingAccount.getSolde() + " €");
                    builder.append("Taux d'intérêt : " + savingAccount.getTauxInteret() + " %");
                    System.out.println(builder.toString());
                    i++;
                }

                first = true;
                size = listSavingAccounts.size();
                do {
                    if (!first) {
                        System.out.println("Mauvais choix... merci de recommencer !");
                    }
                    System.out.print("Veuillez saisir le numero correspondant au compte souhaité : ");
                    try {
                        choixAccount = sc.nextInt();
                    } catch (InputMismatchException e) {
                        choixAccount = -1;
                    } finally {
                        sc.nextLine();
                    }
                    first = false;
                } while (choixAccount < 1 || choixAccount > size);

                CompteEpargne savingAccount = listSavingAccounts.get((choixAccount - 1));

                System.out.println("================================");
                System.out.print("Veuillez saisir le montant total du versement : ");
                amount = sc.nextDouble();

                savingAccount.versement(amount);

                op.setIdAgence(idAgence);
                op.setIdCompte(savingAccount.getId());
                op.setType("retrait");
                op.setMontant((int)amount);

                try {
                    daoSavingAccount.update(savingAccount);
                    daoOperation.create(op);
                } catch (IOException | ClassNotFoundException | SQLException e) {
                    System.err.println(e.getMessage());
                }

                System.out.println("Versement effectué avec succès !");
                dspMenuAccount(type, idAgence);

                break;
        }
    }

    private static void doWithdrawal(String type, int idAgence) {

        System.out.println("=====================================");
        System.out.println("============== RETRAIT ==============");
        System.out.println("=====================================");

        boolean first = true;
        int choixAccount;
        int size;
        int i;
        double amount;
        Operation op = new Operation();
        IDAO<Long, Operation> daoOperation = new OperationDAO();

        switch (type) {
            case "simple":
                IDAO<Long, CompteSimple> daoSimpleAccount = new CompteSimpleDAO();

                List<CompteSimple> listSimpleAccounts = new ArrayList<>();
                try {
                    listSimpleAccounts = daoSimpleAccount.findAll(idAgence);
                } catch (IOException | ClassNotFoundException | SQLException e) {
                    System.err.println(e.getMessage());
                }

                i = 1;
                for (CompteSimple simpleAccount : listSimpleAccounts) {
                    StringBuilder builder = new StringBuilder(i + " - ");
                    builder.append("ID : " + simpleAccount.getId() + " ");
                    builder.append("Solde : " + simpleAccount.getSolde() + " €");
                    builder.append("Decouvert : " + simpleAccount.getDecouvert() + " €");
                    System.out.println(builder.toString());
                    i++;
                }

                first = true;
                size = listSimpleAccounts.size();
                do {
                    if (!first) {
                        System.out.println("Mauvais choix... merci de recommencer !");
                    }
                    System.out.print("Veuillez saisir le numero correspondant au compte souhaité : ");
                    try {
                        choixAccount = sc.nextInt();
                    } catch (InputMismatchException e) {
                        choixAccount = -1;
                    } finally {
                        sc.nextLine();
                    }
                    first = false;
                } while (choixAccount < 1 || choixAccount > size);

                CompteSimple account = listSimpleAccounts.get((choixAccount - 1));

                System.out.println("================================");
                System.out.print("Veuillez saisir le montant total du retrait : ");
                amount = sc.nextDouble();

                account.retrait(amount);

                op.setIdAgence(idAgence);
                op.setIdCompte(account.getId());
                op.setType("retrait");
                op.setMontant((int)amount);

                try {
                    daoSimpleAccount.update(account);
                    daoOperation.create(op);
                } catch (IOException | ClassNotFoundException | SQLException e) {
                    System.err.println(e.getMessage());
                }

                dspMenuAccount(type, idAgence);

                break;
            case "payant":

                IDAO<Long, ComptePayant> daoPayingAccount = new ComptePayantDAO();

                List<ComptePayant> listPayingAccounts = new ArrayList<>();
                try {
                    listPayingAccounts = daoPayingAccount.findAll(idAgence);
                } catch (IOException | ClassNotFoundException | SQLException e) {
                    System.err.println(e.getMessage());
                }

                i = 1;
                for (ComptePayant payingAccount : listPayingAccounts) {
                    StringBuilder builder = new StringBuilder(i + " - ");
                    builder.append("ID : " + payingAccount.getId() + " ");
                    builder.append("Solde : " + payingAccount.getSolde() + " €");
                    System.out.println(builder.toString());
                    i++;
                }

                first = true;
                size = listPayingAccounts.size();
                do {
                    if (!first) {
                        System.out.println("Mauvais choix... merci de recommencer !");
                    }
                    System.out.print("Veuillez saisir le numero correspondant au compte souhaité : ");
                    try {
                        choixAccount = sc.nextInt();
                    } catch (InputMismatchException e) {
                        choixAccount = -1;
                    } finally {
                        sc.nextLine();
                    }
                    first = false;
                } while (choixAccount < 1 || choixAccount > size);

                ComptePayant payingAccount = listPayingAccounts.get((choixAccount - 1));

                System.out.println("================================");
                System.out.print("Veuillez saisir le montant total du retrait : ");
                amount = sc.nextDouble();

                payingAccount.retrait(amount);

                op.setIdAgence(idAgence);
                op.setIdCompte(payingAccount.getId());
                op.setType("retrait");
                op.setMontant((int)amount);

                try {
                    daoPayingAccount.update(payingAccount);
                    daoOperation.create(op);
                } catch (IOException | ClassNotFoundException | SQLException e) {
                    System.err.println(e.getMessage());
                }

                System.out.println("Retrait effectué avec succès !");
                dspMenuAccount(type, idAgence);

                break;
            case "epargne":

                IDAO<Long, CompteEpargne> daoSavingAccount = new CompteEpargneDAO();

                List<CompteEpargne> listSavingAccounts = new ArrayList<>();
                try {
                    listSavingAccounts = daoSavingAccount.findAll(idAgence);
                } catch (IOException | ClassNotFoundException | SQLException e) {
                    System.err.println(e.getMessage());
                }

                i = 1;
                for (CompteEpargne savingAccount : listSavingAccounts) {
                    StringBuilder builder = new StringBuilder(i + " - ");
                    builder.append("ID : " + savingAccount.getId() + " ");
                    builder.append("Solde : " + savingAccount.getSolde() + " €");
                    builder.append("Taux d'intérêt : " + savingAccount.getTauxInteret() + " %");
                    System.out.println(builder.toString());
                    i++;
                }

                first = true;
                size = listSavingAccounts.size();
                do {
                    if (!first) {
                        System.out.println("Mauvais choix... merci de recommencer !");
                    }
                    System.out.print("Veuillez saisir le numero correspondant au compte souhaité : ");
                    try {
                        choixAccount = sc.nextInt();
                    } catch (InputMismatchException e) {
                        choixAccount = -1;
                    } finally {
                        sc.nextLine();
                    }
                    first = false;
                } while (choixAccount < 1 || choixAccount > size);

                CompteEpargne savingAccount = listSavingAccounts.get((choixAccount - 1));

                System.out.println("================================");
                System.out.print("Veuillez saisir le montant total du retrait : ");
                amount = sc.nextDouble();

                savingAccount.retrait(amount);

                op.setIdAgence(idAgence);
                op.setIdCompte(savingAccount.getId());
                op.setType("retrait");
                op.setMontant((int)amount);

                try {
                    daoSavingAccount.update(savingAccount);
                    daoOperation.create(op);
                } catch (IOException | ClassNotFoundException | SQLException e) {
                    System.err.println(e.getMessage());
                }

                System.out.println("Retrait effectué avec succès !");
                dspMenuAccount(type, idAgence);

                break;
        }
    }

    private static void doAdvantage(String type, int idAgence) {

        System.out.println("=====================================");
        System.out.println("========== CALCUL INTERÊTS ==========");
        System.out.println("=====================================");

        boolean first = true;
        int choixAccount;
        int size;
        int i;
        double amount;

        IDAO<Long, CompteEpargne> daoSavingAccount = new CompteEpargneDAO();

        List<CompteEpargne> listSavingAccounts = new ArrayList<>();
        try {
            listSavingAccounts = daoSavingAccount.findAll(idAgence);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.err.println(e.getMessage());
        }

        i = 1;
        for (CompteEpargne savingAccount : listSavingAccounts) {
            StringBuilder builder = new StringBuilder(i + " - ");
            builder.append("ID : " + savingAccount.getId() + " ");
            builder.append("Solde : " + savingAccount.getSolde() + " €");
            builder.append("Taux d'intérêt : " + savingAccount.getTauxInteret() + " %");
            System.out.println(builder.toString());
            i++;
        }

        first = true;
        size = listSavingAccounts.size();
        do {
            if (!first) {
                System.out.println("Mauvais choix... merci de recommencer !");
            }
            System.out.print("Veuillez saisir le numero correspondant au compte souhaité : ");
            try {
                choixAccount = sc.nextInt();
            } catch (InputMismatchException e) {
                choixAccount = -1;
            } finally {
                sc.nextLine();
            }
            first = false;
        } while (choixAccount < 1 || choixAccount > size);

        CompteEpargne savingAccount = listSavingAccounts.get((choixAccount - 1));

        savingAccount.calculInteret();

        try {
            daoSavingAccount.update(savingAccount);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("Calcul des intérêts effectué avec succès !");
        dspMenuAccount(type, idAgence);

    }

    private static void doCSVExport(String type, int idAgence) {

        boolean first = true;
        int choixAccount;
        int size;
        int i;
        int idCompte = 0;

        switch (type) {
            case "simple":
                IDAO<Long, CompteSimple> daoSimpleAccount = new CompteSimpleDAO();

                List<CompteSimple> listSimpleAccounts = new ArrayList<>();
                try {
                    listSimpleAccounts = daoSimpleAccount.findAll(idAgence);
                } catch (IOException | ClassNotFoundException | SQLException e) {
                    System.err.println(e.getMessage());
                }

                i = 1;
                for (CompteSimple simpleAccount : listSimpleAccounts) {
                    StringBuilder builder = new StringBuilder(i + " - ");
                    builder.append("ID : " + simpleAccount.getId() + " ");
                    builder.append("Solde : " + simpleAccount.getSolde() + " €");
                    builder.append("Découvert : " + simpleAccount.getDecouvert() + " €");
                    System.out.println(builder.toString());
                    i++;
                }

                first = true;
                size = listSimpleAccounts.size();
                do {
                    if (!first) {
                        System.out.println("Mauvais choix... merci de recommencer !");
                    }
                    System.out.print("Veuillez saisir le numero correspondant au compte souhaité : ");
                    try {
                        choixAccount = sc.nextInt();
                    } catch (InputMismatchException e) {
                        choixAccount = -1;
                    } finally {
                        sc.nextLine();
                    }
                    first = false;
                } while (choixAccount < 1 || choixAccount > size);

                idCompte = listSimpleAccounts.get((choixAccount - 1)).getId();
                break;
            case "payant":
                IDAO<Long, ComptePayant> daoPaymentAccount = new ComptePayantDAO();

                List<ComptePayant> listPaymentAccounts = new ArrayList<>();
                try {
                    listPaymentAccounts = daoPaymentAccount.findAll(idAgence);
                } catch (IOException | ClassNotFoundException | SQLException e) {
                    System.err.println(e.getMessage());
                }

                i = 1;
                for (ComptePayant paymentAccount : listPaymentAccounts) {
                    StringBuilder builder = new StringBuilder(i + " - ");
                    builder.append("ID : " + paymentAccount.getId() + " ");
                    builder.append("Solde : " + paymentAccount.getSolde() + " €");
                    System.out.println(builder.toString());
                    i++;
                }

                first = true;
                size = listPaymentAccounts.size();
                do {
                    if (!first) {
                        System.out.println("Mauvais choix... merci de recommencer !");
                    }
                    System.out.print("Veuillez saisir le numero correspondant au compte souhaité : ");
                    try {
                        choixAccount = sc.nextInt();
                    } catch (InputMismatchException e) {
                        choixAccount = -1;
                    } finally {
                        sc.nextLine();
                    }
                    first = false;
                } while (choixAccount < 1 || choixAccount > size);

                idCompte = listPaymentAccounts.get((choixAccount - 1)).getId();
                break;
            case "epargne":
                IDAO<Long, CompteEpargne> daoSavingAccount = new CompteEpargneDAO();

                List<CompteEpargne> listSavingAccounts = new ArrayList<>();
                try {
                    listSavingAccounts = daoSavingAccount.findAll(idAgence);
                } catch (IOException | ClassNotFoundException | SQLException e) {
                    System.err.println(e.getMessage());
                }

                i = 1;
                for (CompteEpargne savingAccount : listSavingAccounts) {
                    StringBuilder builder = new StringBuilder(i + " - ");
                    builder.append("ID : " + savingAccount.getId() + " ");
                    builder.append("Solde : " + savingAccount.getSolde() + " €");
                    builder.append("Taux d'intérêts : " + savingAccount.getTauxInteret() + " %");
                    System.out.println(builder.toString());
                    i++;
                }

                first = true;
                size = listSavingAccounts.size();
                do {
                    if (!first) {
                        System.out.println("Mauvais choix... merci de recommencer !");
                    }
                    System.out.print("Veuillez saisir le numero correspondant au compte souhaité : ");
                    try {
                        choixAccount = sc.nextInt();
                    } catch (InputMismatchException e) {
                        choixAccount = -1;
                    } finally {
                        sc.nextLine();
                    }
                    first = false;
                } while (choixAccount < 1 || choixAccount > size);

                idCompte = listSavingAccounts.get((choixAccount - 1)).getId();
                break;
        }


     /* System.out.println("Export CSV en cours");
        try {

            String nomDuFichier = "resources/compte"+idCompte+"OperationAvant"+System.currentTimeMillis()+".csv";
            FileOutputStream fileOut = new FileOutputStream(nomDuFichier);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(carnet);
            out.close();
            fileOut.close();
            System.out.println("\nSerialisation terminée avec succès...\n");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        CSVWriter writer = new CSVWriter(new FileWriter(csv));
        //Liste des operations en fonction du compte choisis List data = new ArrayList();
        data.add(new String[]{"India", "43 Run"});
        writer.writeAll(data);
        System.out.println("CSV File written successfully All at a time");
        writer.close();
*/
    }


    public static void main(String[] args) {
        dspMainMenu();
    }
}
