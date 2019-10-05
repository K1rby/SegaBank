import bo.Agence;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class App {

    private static Scanner sc = new Scanner( System.in );
    //private static final String BACKUPS_DIR = "./resources/backups/";

    public static void dspMainMenu() {

        int response;
        boolean first = true;
        do {
            if ( !first ) {
                System.out.println( "Mauvais choix... merci de recommencer !" );
            }
            System.out.println( "=================================================" );
            System.out.println( "=========== MENU - GESTION DES AGENCES ==========" );
            System.out.println( "=================================================" );
            System.out.println( "1 - Ajouter une Agence" );
            System.out.println( "2 - Gestion d'une Agence" );
            System.out.println( "3 - Quitter" );
            System.out.print( "Entrez votre choix : " );
            try {
                response = sc.nextInt();
            } catch ( InputMismatchException e ) {
                response = -1;
            } finally {
                sc.nextLine();
            }
            first = false;
        } while ( response < 1 || response > 3 );

        switch ( response ) {
            case 1:
                createAgence();
                break;
            case 2:
                dspSecondAgenceMenu();
                break;
        }

    }

    private static void createAgence() {
        //IDAO<Long, Agence> daoAgence = new AgenceDAO();

        System.out.println( "======================================" );
        System.out.println( "======== CREATION D'UNE AGENCE =======" );
        System.out.println( "======================================" );
        Agence agence = null;
        System.out.print( "Entrez le code l'agence : " );
        agence.setCode( sc.nextLine() );
        System.out.print( "Entrez l'adresse de l'agence : " );
        agence.setAdresse( sc.nextLine() );
        //dao.create(agence)
        System.out.println( "Agence créée avec succès!" );
        dspMainMenu();
    }

    private static void dspSecondAgenceMenu() {

        //IDAO<Long, Agence> daoAgence = new AgenceDAO();

        System.out.println( "======================================" );
        System.out.println( "========== LISTE DES AGENCES =========" );
        System.out.println( "======================================" );

        //List<Agence> listAgences = daoAgence.findAll();
      /*  for (Agence agence : listAgences) {
            //Faire le string builder pour l'affichage
            System.out.println(agence);
        }
*/
        System.out.println( "======================================" );




        dspMainMenu();
    }

    public static void main(String[] args) {

        System.out.println("Hello world");
    }
}
