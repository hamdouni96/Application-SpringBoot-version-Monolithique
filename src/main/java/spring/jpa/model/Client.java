package spring.jpa.model;



import jakarta.persistence.*;


@Entity
public class Client {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(length = 50)
    private String nom;
    
    @Column(length = 50)
    private String prenom;

    @Column(length = 15)
    private String telephone;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Client(String nom, String prenom, String telephone) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.telephone = telephone;
	}

	public Client() {
		// TODO Auto-generated constructor stub
	}



    // Nouveaux attributs pour les fonctionnalités spécifiques
   /* @OneToMany(mappedBy = "client")
    private List<Facture> factures;
*/

    // Constructeurs, getters, setters, etc.
    

}
