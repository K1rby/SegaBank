package bo;

public class Agence {

    //Attributs
    private int id;
    private String code;
    private String adresse;

    //Accesseurs
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    //Constructeur
    public Agence(){}

    public Agence(String code, String adresse) {
        this.code = code;
        this.adresse = adresse;
    }
}
