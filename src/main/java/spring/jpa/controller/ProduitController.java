package spring.jpa.controller;import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import spring.jpa.model.Categorie;
import spring.jpa.model.Produit;
import spring.jpa.repository.CategorieRepository;
import spring.jpa.repository.ProduitRepository;

@Controller
@RequestMapping(value = "/produit")
public class ProduitController {

    @Autowired
    private ProduitRepository produitRepos;

    @Autowired
    private CategorieRepository catRepos;

    @RequestMapping(value = "/index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int p,
                        @RequestParam(name = "motCle", defaultValue = "") String mc,
                        @RequestParam(name = "typeProduit", defaultValue = "Tous") String tp,
                        @RequestParam(name = "dateDebut", required = false) LocalDate dateDebut,
                        @RequestParam(name = "dateFin", required = false) LocalDate dateFin,
                        @RequestParam(name = "categorie", required = false) Long categorieId) {

        Page<Produit> pg = null;

        if (tp.equals("Tous")) {
            if (dateDebut != null && dateFin != null) {
                pg = produitRepos.findByDateAchatBetween(dateDebut, dateFin, PageRequest.of(p, 6));
            } else if (dateDebut != null) {
                pg = produitRepos.findByDateAchatAfter(dateDebut, PageRequest.of(p, 6));
            } else if (dateFin != null) {
                pg = produitRepos.findByDateAchatBefore(dateFin, PageRequest.of(p, 6));
            } else if (categorieId != null) {
                pg = produitRepos.findByCategorieIdAndDesignationLike(categorieId, "%" + mc + "%", PageRequest.of(p, 6));
            } else {
                pg = produitRepos.findByDesignationLike("%" + mc + "%", PageRequest.of(p, 6));
            }
        }

        int nbrePages = pg.getTotalPages();
        int[] pages = new int[nbrePages];
        for (int i = 0; i < nbrePages; i++) {
            pages[i] = i;
        }

        model.addAttribute("pages", pages);
        model.addAttribute("pageProduits", pg);
        model.addAttribute("pageCourante", p);
        model.addAttribute("motCle", mc);
        model.addAttribute("typeProduit", tp);

        return "produits";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String formProduit(Model model) {
        List<Categorie> cats = catRepos.findAll();
        model.addAttribute("categories", cats);
        model.addAttribute("produit", new Produit());
        return "/formProduit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Model model, @Valid Produit produit, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "formProduit";
        }
        produitRepos.save(produit);
        return "confirmation";
    }
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam("id") Long id) {
        produitRepos.deleteById(id);
        return "redirect:/produit/index";
    }
    
    

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(@RequestParam("id") Long id, Model model) {
        Produit produit = produitRepos.findById(id).orElse(null);
        if (produit != null) {
            List<Categorie> cats = catRepos.findAll();
            model.addAttribute("categories", cats);
            model.addAttribute("produit", produit);
            return "/formProduit";
        } else {
            // Gérer le cas où le produit n'est pas trouvé
            return "redirect:/produit/index";
        }
    }
   
    private final ProduitRepository produitRepository;

    @Autowired
    public ProduitController(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    
    @GetMapping("/en-rupture-stock")
    public String produitsEnRuptureStock(Model model) {
        List<Produit> produitsEnRuptureStock = produitRepository.findByQuantiteLessThan(5);

        model.addAttribute("produitsEnRuptureStock", produitsEnRuptureStock);
        return "produitsEnRuptureStock";
    }
    @GetMapping("/produitsSollicites")
    public String produitSollicite(Model model) {
        Object[] result = produitRepository.findMostSollicitedProduit();
        if (result != null && result.length == 4) {
            Produit produitSollicite = new Produit();
            produitSollicite.setId((Long) result[0]);
            produitSollicite.setDesignation((String) result[1]);
            produitSollicite.setPrix((double) result[2]);
            produitSollicite.setQuantite(((Integer) result[3]).intValue());
            model.addAttribute("produitsSollicites", produitSollicite);
            return "produitsSollicites"; // Assurez-vous d'avoir la vue correspondante
        } else {
            // Gérer le cas où aucun produit n'est trouvé.
            return "produitsSollicites"; // Assurez-vous d'avoir la vue correspondante
        }
    }
        


}
