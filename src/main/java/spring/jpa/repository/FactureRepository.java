package spring.jpa.repository;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import spring.jpa.model.Client;
import spring.jpa.model.Facture;
import spring.jpa.model.Produit;

public interface FactureRepository extends JpaRepository<Facture,Long> {
	
	List<Facture> findByClient(Client client);
	List<Produit> findProduitByIdFacture(Long idFacture);
	  @Query("SELECT f FROM Facture f WHERE f.client = :client AND f.regle = true")
	    List<Facture> findByClientAndRegle(@Param("client") Client client);

	    @Query("SELECT f FROM Facture f WHERE f.client = :client AND f.regle = false")
	    List<Facture> findByClientAndNonRegle(@Param("client") Client client);
	    

	    List<Facture> findByClientAndRegle(Client client, boolean regle);

	    List<Facture> findByClientAndRegleIsFalse(Client client);
	    @Query(value = "SELECT p.id, p.designation, p.prix, COALESCE(SUM(fp.quantite), 0) AS totalQuantiteVendue, COUNT(fp.facture_id) AS nombreVentes " +
	            "FROM produit p " +
	            "LEFT JOIN facture_produit fp ON p.id = fp.produit_id " +
	            "GROUP BY p.id, p.designation, p.prix " +
	            "ORDER BY nombreVentes DESC", nativeQuery = true)
	    List<Object[]> findTopSellingProducts();


	   /* @Query("SELECT c.id, c.nom, c.prenom, COUNT(f.idFacture) AS totalFactures " +
	           "FROM Client c " +
	           "JOIN Facture f ON c.id = f.client.id " +
	           "GROUP BY c.id " +
	           "ORDER BY totalFactures DESC")
	    List<Object[]> findTopFrequentClients();
*/
	        @Query("SELECT f.client, SUM(f.montant) FROM Facture f GROUP BY f.client ORDER BY SUM(f.montant) DESC LIMIT 1")
	        Object[] findTopFrequentClientWithTotalAmount();
	        @Query("SELECT COALESCE(SUM(f.totale), 0) FROM Facture f")
	        double calculateTotalRevenue();
	    
	    }