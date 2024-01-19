package spring.jpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Reglement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    @NotNull // interdire une valeur null
    @Size(min=3, max=50)//spécifier la taille acceptée
    private String modePaiement;

    // Constructeurs, getters et setters

    public Reglement() {
        // Constructeur par défaut
    }

    public Reglement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

   

    // Getters et setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

   

    @Override
    public String toString() {
        return "Reglement{" +
                "id=" + id +
                ", modePaiement='" + modePaiement + '\'' +
                '}';
    }
 
}
