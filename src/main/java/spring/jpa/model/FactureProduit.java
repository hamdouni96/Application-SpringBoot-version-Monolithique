package spring.jpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "facture_produit")
public class FactureProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "facture_id")
    private Facture facture;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;

    
    
    
    
    @Column(name = "quantite") // Ajoutez cette annotation pour spécifier la colonne
    private int quantite;

    // Constructeurs, getters et setters

    public FactureProduit() {
    }

    public FactureProduit(Facture facture, Produit produit, int quantite) {
        this.facture = facture;
        this.produit = produit;
        this.quantite = quantite;
    }

	public Facture getFacture() {
		return facture;
	}

	public void setFacture(Facture facture) {
		this.facture = facture;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
    
    

    // Getters and setters
}
