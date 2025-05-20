
public class Car {
    private int id;
    private String nom;
    private int prix;

    public Car(int id, String nom, int prix) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
    }

   
    public int getId() {
        return id;
    }
    

    public String getNom() {
        return nom;
    }
    public void SetNom(String nom) {
        this.nom = nom;
    }
    public int getPrix(){
        return prix;
    }
    public void SetPrix(int prix){
        this.prix = prix;
    }
}