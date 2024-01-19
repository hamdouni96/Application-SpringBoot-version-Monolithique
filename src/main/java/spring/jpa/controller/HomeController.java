package spring.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import spring.jpa.repository.FactureRepository;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "newhome";
    }
    @Autowired
	private FactureRepository facturetRepos;
    @GetMapping("/chiffreAffaireTotal")
	 public String chiffreAffaireTotal(Model model) {
	     // Appeler la méthode pour obtenir le chiffre d'affaires total
	     double chiffreAffaireTotal = facturetRepos.calculateTotalRevenue();

	     // Ajouter le chiffre d'affaires total au modèle pour l'afficher dans la vue
	     model.addAttribute("chiffreAffaireTotal", chiffreAffaireTotal);

	     return "newhome";
	 }
}
