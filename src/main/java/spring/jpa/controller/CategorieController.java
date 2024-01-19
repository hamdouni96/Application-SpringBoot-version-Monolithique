package spring.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import spring.jpa.model.Categorie;
import spring.jpa.repository.CategorieRepository;
@Controller
@RequestMapping(value = "/categories")
public class CategorieController {

    @Autowired
    private CategorieRepository categorieRepository;

    @GetMapping("/index")
    public String index(Model model) {
        Iterable<Categorie> categories = categorieRepository.findAll();
        model.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("/form")
    public String formCategorie(Model model) {
        model.addAttribute("categorie", new Categorie());
        return "/formCategorie";
    }
    
    /*

    @PostMapping("/save")
    public String save(@Valid Categorie categorie, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/formCategorie";
        }

        categorieRepository.save(categorie);
        return "redirect:/categories/index";
    }
    
    
    */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Model model, @Valid Categorie categorie, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/formCategorie";
        }
        categorieRepository.save(categorie);
        return "ConfirmCat";
    }
    
   
    @PostMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        Categorie categorie = categorieRepository.findById(id).orElse(null);
        if (categorie != null) {
            model.addAttribute("categorie", categorie);
            return "/formCategorie";
        } else {
            return "redirect:/categories/index";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        categorieRepository.deleteById(id);
        return "redirect:/categories/index";
    }

    // Autres méthodes de manipulation CRUD (delete, edit, update) restent inchangées
}
