package spring.jpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Devise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    @NotNull // interdire une valeur null
    private String type;

    @Column(length = 50)
    private Long montant;

    public Devise() {

    }

    public Devise(String type) {
        this.type = type;
    }

    public Devise(String type, Long montant) {
        this.type = type;
        this.montant = montant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getMontant() {
        return montant;
    }

    public void setMontant(Long montant) {
        this.montant = montant;
    }

    @Override
    public String toString() {
        return "Devise [id=" + id + ", type=" + type + ", montant=" + montant + "]";
    }
}
