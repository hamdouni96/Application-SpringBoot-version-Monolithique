package spring.jpa.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import spring.jpa.model.Client;
import spring.jpa.repository.ClientRepository;
@Controller
@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/index")
    public String index(Model model) {
        Iterable<Client> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);
        return "clients";
    }
    @GetMapping("/form")
    public String formClient(Model model) {
        model.addAttribute("client", new Client());
        return "/formClient";
    }
    

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Model model, @Valid Client client, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/formClient";
        }
        clientRepository.save(client);
        return "confirmationClient";
    }
 
    @PostMapping("/edit")
    public String editClient(@Valid @ModelAttribute("client") Client client, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // S'il y a des erreurs de validation, retournez à la page d'édition avec les erreurs.
            return "editClient";
        }

        // Enregistrer directement le client mis à jour dans la base de données
        clientRepository.save(client);

        // Redirection vers la page de confirmation après la mise à jour réussie
        return "editClient";
    }


    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        clientRepository.deleteById(id);
        return "redirect:/clients/index";
    }
    

    @GetMapping("/search")
    public String search(@RequestParam(name = "nom",required = false) String nom, Model model) {
    	java.util.List<Client>  clients = clientRepository.findByNomUsingHql(nom);
        model.addAttribute("clients", clients);
        return "clients";
    }
    
    
    
    
    
  

   
}
