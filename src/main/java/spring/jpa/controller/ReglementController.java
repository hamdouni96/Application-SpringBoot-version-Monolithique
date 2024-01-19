package spring.jpa.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import spring.jpa.model.Reglement;
import spring.jpa.repository.ReglementRepository;

@Controller
@RequestMapping(value = "/reglements")
public class ReglementController {
	@Autowired
    private ReglementRepository reglementRepository;

    @GetMapping("/index")
    public String index(Model model) {
        Iterable<Reglement> reglements = reglementRepository.findAll();
        model.addAttribute("reglements", reglements);
        return "reglements";
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Model model, @Valid Reglement reglement, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/formReglement";
        }
        reglementRepository.save(reglement);
        return "redirect:/reglements/index";
    }
  
    @GetMapping("/form")
    public String formReglement(Model model) {
        model.addAttribute("reglement", new Reglement());
        return "/formReglement";
    }
    
      @PostMapping("/edit")
    public String editReglement(@Valid @ModelAttribute("reglement") Reglement reglement, BindingResult bindingResult) {
       
        if (bindingResult.hasErrors()) {
            // S'il y a des erreurs de validation, retournez à la page d'édition avec les erreurs.
            return "editReglement";
        }
        // Enregistrer directement le reglement mis à jour dans la base de données
        reglementRepository.save(reglement);
        // Redirection vers la page de confirmation après la mise à jour réussie
        return "editReglement";
    }
    

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        reglementRepository.deleteById(id);
        return "redirect:/reglements/index";
    }

    // Autres méthodes de manipulation CRUD (delete, edit, update) restent inchangées
    @GetMapping("/search")
    public String search(@RequestParam(name = "modePaiement",required = false) String modePaiement, Model model) {
    	java.util.List<Reglement>  reglements = reglementRepository.findByModePaiementUsingHql(modePaiement);
        model.addAttribute("reglements", reglements);
        return "reglements";
    }
    
}
