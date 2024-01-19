package spring.jpa.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;
import spring.jpa.model.Client;
import spring.jpa.model.Facture;
import spring.jpa.model.Produit;
import spring.jpa.repository.FactureRepository;
import spring.jpa.repository.ProduitRepository;
import spring.jpa.repository.ClientRepository;
@Controller
@RequestMapping(value = "/factures")
public class FactureController {
	@Autowired
	private FactureRepository facturetRepos;
	
	@Autowired
	private ProduitRepository prodRepo;
	@Autowired
	private ClientRepository clientRepository;

	@GetMapping("/index")
	public String getAllFactures(Model model) {
		List<Facture> factures = facturetRepos.findAll();
		model.addAttribute("factures", factures);
		return "factures";
	}

	@GetMapping("/form")
	public String formFacture(Model model) {
	    List<Produit> produits = prodRepo.findAll();
	    List<Client> clients = clientRepository.findAll(); // Assurez-vous de récupérer correctement la liste des clients
	    model.addAttribute("produits", produits);
	    model.addAttribute("clients", clients);
	    model.addAttribute("facture", new Facture());
	    
	    // Ajoutez une liste pour stocker la quantité pour chaque produit
	    List<Integer> quantites = new ArrayList<>();
	    for (int i = 0; i < produits.size(); i++) {
	        quantites.add(0); // Initialisez toutes les quantités à 0 par défaut
	    }
	    model.addAttribute("quantites", quantites);
	    
	    return "/formFacture";
	}


@PostMapping("/save")
public String saveFacture(Model model, @Valid Facture facture, @RequestParam List<Integer> quantites) {
    double sommeTotale = 0;
    List<Produit> produits = new ArrayList<>();

    for (int i = 0; i < quantites.size(); i++) {
        int quantite = quantites.get(i);
        if (quantite > 0) {
            Produit produit = prodRepo.findById((long) (i + 1)).orElse(null);
            if (produit != null) {
                produit.setQuantite(produit.getQuantite() - quantite);
                produits.add(produit);
                sommeTotale += produit.getPrix() * quantite;
            }
        }
    }

    facture.setProduits(produits);
    facture.setTotale(sommeTotale);
    facturetRepos.save(facture);

    // Mise à jour de la liste des produits pour éviter la perte de données lors de la redirection
    List<Produit> allProduits = prodRepo.findAll();
    model.addAttribute("produits", allProduits);
    model.addAttribute("total", sommeTotale); // Ajout du total dans le modèle

    return "redirect:/factures/form";
}

	@PostMapping("/edit")
	public String editFacture(@Valid @ModelAttribute("facture") Facture facture, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			// S'il y a des erreurs de validation, retournez à la page d'édition avec les
			// erreurs.
			return "editFacture";
		}

		// Enregistrer directement la facture mise à jour dans la base de données
		facturetRepos.save(facture);

		// Redirection vers la page de confirmation après la mise à jour réussie
		return "factures";
	}

	@PostMapping("/delete")
	public String delete(@RequestParam("id") Long id) {
		facturetRepos.deleteById(id);
		return "redirect:/factures";
	}

//les autres méyhodes	   
	public double getChiffreAffaireByClient(Client client) {
		List<Facture> factures = facturetRepos.findByClient(client);
		double chiffreAffaire = 0;
		for (Facture f : factures) {
			chiffreAffaire = chiffreAffaire + f.getTotale();
		}
		return chiffreAffaire;
	}

	public double getMontantNonPayeByClient(Client client) {
		List<Facture> factures = facturetRepos.findByClient(client);
		double montantNonPaye = 0;
		for (Facture f : factures) {
			montantNonPaye = (f.isRegle()) ? (montantNonPaye + 0) : (montantNonPaye + f.getTotale());
		}
		return montantNonPaye;
	}

	public List<Facture> getFactureRegleByClient(Client client) {
		return facturetRepos.findByClient(client).stream().filter(f -> f.isRegle()).toList();
	}

	public List<Facture> getFactureNonRegleByClient(Client client) {
		return facturetRepos.findByClient(client).stream().filter(f -> !f.isRegle()).toList();
	}
	@GetMapping("/search")
	public String searchFactures(Model model,
	                             @RequestParam(name = "clientId", required = false) Long clientId,
	                             @RequestParam(name = "regle", required = false) Boolean regle) {
	    // Vérifiez si clientId et regle sont présents dans la requête
	    if (clientId != null && regle != null) {
	        Client client = clientRepository.findById(clientId).orElse(null);

	        if (client != null) {
	            List<Facture> factures;

	            if (regle) {
	                factures = facturetRepos.findByClientAndRegle(client, true);
	                model.addAttribute("title", "Factures Réglées pour " + client.getNom());
	            } else {
	                factures = facturetRepos.findByClientAndRegleIsFalse(client);
	                model.addAttribute("title", "Factures Non Réglées pour " + client.getNom());
	            }

	            model.addAttribute("factures", factures);
	        }
	    } else {
	        // Si les paramètres ne sont pas présents, afficher toutes les factures par défaut
	        List<Facture> allFactures = facturetRepos.findAll();
	        model.addAttribute("factures", allFactures);
	        model.addAttribute("title", "Toutes les factures");
	    }

	    // Assurez-vous que la liste des clients est correctement ajoutée au modèle
	    List<Client> clients = clientRepository.findAll();
	    model.addAttribute("clients", clients);

	    // Autres attributs, si nécessaire...

	    return "factures";
	}


    @GetMapping("/chiffreAffaire")
    public String chiffreAffaire(Model model, @RequestParam("clientId") Long clientId) {
        // Votre logique pour récupérer le chiffre d'affaires
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client != null) {
            double chiffreAffaire = getChiffreAffaireByClient(client);
            model.addAttribute("chiffreAffaire", chiffreAffaire);
        }
        return "chiffreAffaire";
    }

	@GetMapping("/montantNonPaye")
	public String montantNonPaye(Model model, @RequestParam("clientId") Long clientId) {
	    Client client = clientRepository.findById(clientId).orElse(null);
	    if (client != null) {
	        double montantNonPaye = getMontantNonPayeByClient(client);
	        model.addAttribute("montantNonPaye", montantNonPaye);
	    }
	    return "montantNonPaye";
	}
	 @GetMapping("/produitsPlusVendus")
	    public String afficherProduitsPlusVendus(Model model) {
	        // Appeler la méthode du repository pour obtenir les produits les plus vendus
	        List<Object[]> topSellingProducts = facturetRepos.findTopSellingProducts();

	        // Ajouter les résultats à l'objet Model pour les afficher dans la vue
	        model.addAttribute("topSellingProducts", topSellingProducts);

	        // Retourner le nom de la vue à afficher (à créer dans le dossier 'resources/templates')
	        return "produitsPlusVendus";
	    }


	 @GetMapping("/clientsfideles")
	 public String afficherClientsPlusFideles(Model model) {
	     Object[] topClientWithTotalAmount = facturetRepos.findTopFrequentClientWithTotalAmount();

	     // Ajoutez ce bloc pour déboguer
	     for (Object property : topClientWithTotalAmount) {
	         System.out.println("Property: " + property);
	     }

	     // Vérifiez la structure de l'objet retourné
	     if (topClientWithTotalAmount.length >= 2) {
	         System.out.println("ID: " + ((Client) topClientWithTotalAmount[0]).getId());
	         System.out.println("Nom: " + ((Client) topClientWithTotalAmount[0]).getNom());
	         System.out.println("Prenom: " + ((Client) topClientWithTotalAmount[0]).getPrenom());
	         System.out.println("Total Achats: " + topClientWithTotalAmount[1]);
	         model.addAttribute("totalAchats:",topClientWithTotalAmount[1]);
	     } else {
	         System.out.println("Erreur: La structure de l'objet retourné n'est pas conforme aux attentes.");
	     }

	     model.addAttribute("topClientWithTotalAmount", topClientWithTotalAmount);
	     return "clientsfideles";
	 }
	 @GetMapping("/chiffreAffaireTotal")
	 public String chiffreAffaireTotal(Model model) {
	     // Appeler la méthode pour obtenir le chiffre d'affaires total
	     double chiffreAffaireTotal = facturetRepos.calculateTotalRevenue();

	     // Ajouter le chiffre d'affaires total au modèle pour l'afficher dans la vue
	     model.addAttribute("chiffreAffaireTotal", chiffreAffaireTotal);

	     return "chiffreAffaireTotal";
	 }

}