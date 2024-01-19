package spring.jpa.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFacture;

    @Temporal(TemporalType.DATE)
    private Date dateFacture;
    
    @Column
    private double totale;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    private BigDecimal montant;
    @ManyToMany
    @JoinTable(
            name = "facture_produit",
            joinColumns = @JoinColumn(name = "facture_id"),
            inverseJoinColumns = @JoinColumn(name = "produit_id")
    )
    
    private List<Produit> produits;

    @ManyToOne
    private Reglement reglement;

    /*@ManyToMany
    @JoinTable(
            name = "facture_devise",
            joinColumns = @JoinColumn(name = "facture_id"),
            inverseJoinColumns = @JoinColumn(name = "devise_id")
    )
    private List<Devise> devises;*/

    private boolean regle;

    // Constructeur par défaut nécessaire pour JPA
    public Facture() {
    }

    public Facture(Long idFacture, Date dateFacture,Long totale, Client client,BigDecimal montant , List<Produit> produits, Reglement reglement/*, List<Devise> devises*/, boolean regle) {
        this.idFacture = idFacture;
        this.dateFacture = dateFacture;
        this.client = client;
        this.montant=montant;
        this.produits = produits;
        this.reglement = reglement;
       // this.devises = devises;
        this.regle = regle;
        this.totale = totale;
    }
    
    
    
    public BigDecimal getMontant() {
		return montant;
	}

	public void setMontant(BigDecimal montant) {
		this.montant = montant;
	}

	public double getTotale() {
        return totale;
    }

	public void setTotale(double totale) {
		this.totale = totale;
	}

	public Long getIdFacture() {
		return idFacture;
	}
	public void setIdFacture(Long idFacture) {
		this.idFacture = idFacture;
	}
	public Date getDateFacture() {
		return dateFacture;
	}
	public void setDateFacture(Date dateFacture) {
		this.dateFacture = dateFacture;
	}
	public Client getClients() {
		return client;
	}
	public void setClients(Client client) {
		this.client = client;
	}
	public Reglement getReglement() {
		return reglement;
	}
	public void setReglement(Reglement reglement) {
		this.reglement = reglement;
	}
	public boolean isRegle() {
		return regle;
	}
	public void setRegle(boolean regle) {
		this.regle = regle;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Produit> getProduits() {
		return produits;
	}

	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}
	   private Long selectedProductId;

	    public Long getSelectedProductId() {
	        return selectedProductId;
	    }

	    public void setSelectedProductId(Long selectedProductId) {
	        this.selectedProductId = selectedProductId;
	    }
	
}
