package spring.jpa;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import spring.jpa.model.Categorie;
import spring.jpa.model.Client;
import spring.jpa.model.Devise;
import spring.jpa.model.Facture;
import spring.jpa.model.FactureProduit;
import spring.jpa.model.Produit;
import spring.jpa.model.Reglement;
import spring.jpa.repository.CategorieRepository;
import spring.jpa.repository.ClientRepository;
import spring.jpa.repository.DeviseRepository;
import spring.jpa.repository.FactureProduitRepository;
import spring.jpa.repository.FactureRepository;
import spring.jpa.repository.ProduitRepository;
import spring.jpa.repository.ReglementRepository;
@SpringBootApplication
public class JpaSpringBootThymeleafApplication {
// Déclarer une référence des inetrface repository
static ProduitRepository produitRepos ;
static CategorieRepository categorieRepos;
static ClientRepository clientRepo;
static ReglementRepository reglementRepo;
static FactureRepository factureRepo;
static DeviseRepository deviseRepo;
static  FactureProduitRepository factureProduitRepos;
public static void main(String[] args) {
// référencer le contexte
ApplicationContext contexte =SpringApplication.run(JpaSpringBootThymeleafApplication.class, args);
// Récupérer une implémentation de l'interface "ProduitRepository" par injection de dépendance

produitRepos =contexte.getBean(ProduitRepository.class);
categorieRepos =contexte.getBean(CategorieRepository.class);
clientRepo = contexte.getBean(ClientRepository.class);
reglementRepo = contexte.getBean(ReglementRepository.class);
deviseRepo = contexte.getBean(DeviseRepository.class);
factureRepo = contexte.getBean(FactureRepository.class);
 factureProduitRepos = contexte.getBean(FactureProduitRepository.class);
// créer deux catégories;
Categorie cat1 = new Categorie("A", "Appareils ");
Categorie cat2 = new Categorie("P", "peripheriques ");
Categorie cat3 = new Categorie("Ac", "Accessoires  ");
//Attacher les deux catégories à la BD (insertion)
categorieRepos.save(cat1);
categorieRepos.save(cat2);
categorieRepos.save(cat3);

SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
java.util.Date date1 = null;
java.util.Date date2 = null;
java.util.Date date3 = null;
try {
date1 = sdf.parse("2022-04-15");
date2 = sdf.parse("2022-02-15");
date3 = sdf.parse("2022-05-15");
} catch (ParseException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
// Insérer 3 produits
Produit p1 =new Produit("SmartPhone",1000, 20, date1 , cat1);
Produit p2 =new Produit("Clavier", 2000.0, 2, date2, cat1);
Produit p3 =new Produit("Cable", 1.200, 30, date3, cat2);
produitRepos.save(p1);
produitRepos.save(p2);
produitRepos.save(p3);
//Insérer une nouvelle catégorie avec l'ajout d'un nouveu produit
Produit p4 =new Produit("ordinateur", 4000, 20, date1 );
Categorie cat4 = new Categorie("BR", "BUREAUTIQUE");
p4.setCategorie(cat4);
cat3.getProduits().add(p4);
categorieRepos.save(cat4);

//Insérer 4 reglements
Reglement r1 = new Reglement("Especes");
Reglement r2 = new Reglement("Cheques ");
Reglement r3 = new Reglement("Virements bancaires ");
Reglement r4 = new Reglement("Cartes de credit  ");
//Enregistrement des Règlements dans la base de données
reglementRepo.save(r1);
reglementRepo.save(r2);
reglementRepo.save(r3);
reglementRepo.save(r4);

//Insérer 3 clients
Client client1 = new Client("Hamdouni", "noura","55037723");
Client client2 = new Client("neyli", "eya","55037723");
Client client3 = new Client("neyli", "May","55037723");
// Enregistrement des clients dans la base de données
clientRepo.save(client1);
clientRepo.save(client2);
clientRepo.save(client3);
//Insérer 4 devise
Devise d1 = new Devise("Dinar");
Devise d2 = new Devise("Dollar ");
Devise d3 = new Devise("Euro ");
Devise d4 = new Devise("Livre sterling");
//Enregistrement des Règlements dans la base de données
deviseRepo.save(d1);
deviseRepo.save(d2);
deviseRepo.save(d3);
deviseRepo.save(d4);

//Afficher la liste des produits

afficherTousLesProduits();




insereTroisFacturesAvecTroisProduits();
}//...
static void insereTroisFacturesAvecTroisProduits() {
    // Récupérer tous les clients
    List<Client> clients = clientRepo.findAll();

    // Récupérer tous les produits
    List<Produit> produits = produitRepos.findAll();

    // Récupérer un règlement existant (vous pouvez ajuster cela selon vos besoins)
    Reglement reglement = reglementRepo.findById(1L).orElse(null);

    if (reglement == null) {
        System.out.println("Règlement non trouvé. Assurez-vous qu'un règlement avec l'ID 1 existe dans la base de données.");
        return;
    }

    // Insérer 3 factures pour chaque client
    for (Client client : clients) {
        for (int i = 0; i < 3; i++) {
            // Créer une nouvelle instance de Facture pour chaque itération
            Facture facture = new Facture();
            facture.setRegle(false); // Initialiser à false, la facture n'est pas encore réglée
            facture.setTotale(0.0); // Le total sera calculé à partir de la somme des produits
            facture.setClient(client);
            facture.setReglement(reglement);

            // Générer une date aléatoire dans une plage donnée (vous pouvez ajuster cela selon vos besoins)
            Date dateFacture = getRandomDate();
            facture.setDateFacture(dateFacture);

            // Enregistrez la facture dans la base de données
            factureRepo.save(facture);

            // Sélectionner 3 produits au hasard pour chaque client
            List<Produit> produitsClient = new ArrayList<>();

            for (int j = 0; j < 3; j++) {
                // Sélectionnez un produit au hasard pour chaque client
                Produit produit = getRandomProduct(produits);

                // Définir une quantité aléatoire (vous pouvez ajuster cela selon vos besoins)
                int quantite = new Random().nextInt(10) + 1; // Quantité entre 1 et 10

                // Ajouter le produit avec la quantité à la liste
                produitsClient.add(produit);

                // Enregistrer la relation dans la table de liaison
                FactureProduit factureProduit = new FactureProduit();
                factureProduit.setFacture(facture);
                factureProduit.setProduit(produit);
                factureProduit.setQuantite(quantite);
                factureProduitRepos.save(factureProduit);
            }

            // Associer les produits à la facture
            facture.setProduits(produitsClient);

            // Calculer le total à partir de la somme des prix des produits
            double total = produitsClient.stream().mapToDouble(p -> p.getPrix() * p.getQuantite()).sum();
            facture.setTotale(total);

            // Enregistrez la facture mise à jour dans la base de données
            factureRepo.save(facture);
        }
    }
}


// Méthode pour obtenir une date aléatoire dans une plage donnée
private static Date getRandomDate() {
    long millisInDay = 24 * 60 * 60 * 1000;
    long currentTime = System.currentTimeMillis();
    long randomTimeOffset = (long) (Math.random() * 30 * millisInDay); // Plage de 30 jours

    return new Date(currentTime - randomTimeOffset);
}
// Méthode pour obtenir un produit au hasard à partir de la liste de produits
private static Produit getRandomProduct(List<Produit> produits) {
    return produits.get(new Random().nextInt(produits.size()));
}
static void afficherTousLesProduits()
{
System.out.println("***************************************");
// Lister l'ensemble des produits
System.out.println("Afficher tous les produits...");

System.out.println("***************************************");

List<Produit> lp = produitRepos.findAll();
for (Produit p : lp)
{
System.out.println(p);
}
System.out.println("***************************************");
}
}