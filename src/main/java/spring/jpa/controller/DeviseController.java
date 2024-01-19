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
import spring.jpa.model.Devise;
import spring.jpa.repository.DeviseRepository;
@Controller
@RequestMapping(value="/devises")
public class DeviseController {
	@Autowired
    private DeviseRepository deviseRepository;

    @GetMapping("/index")
    public String index(Model model) {
        Iterable<Devise> devises = deviseRepository.findAll();
        model.addAttribute("devises", devises);
        return "devises";
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Model model, @Valid Devise devise, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/formDevise";
        }
        deviseRepository.save(devise);
        return "redirect:/devises/index";
    }
  
    @GetMapping("/form")
    public String formDevise(Model model) {
        model.addAttribute("devise", new Devise());
        return "/formDevise";
    }
    
      @PostMapping("/edit")
    public String editDevise(@Valid @ModelAttribute("devise") Devise devise, BindingResult bindingResult) {
       
        if (bindingResult.hasErrors()) {
            return "editDevise";
        }
        deviseRepository.save(devise);
        return "editDevise";
    }
    

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        deviseRepository.deleteById(id);
        return "redirect:/devises/index";
    }

    // Autres méthodes de manipulation CRUD (delete, edit, update) restent inchangées
    @GetMapping("/search")
    public String search(@RequestParam(name = "type",required = false) String type, Model model) {
    	java.util.List<Devise>  devises = deviseRepository.findByTypeDeviseUsingHql(type);
        model.addAttribute("devises", devises);
        return "devises";
    }
    
}
