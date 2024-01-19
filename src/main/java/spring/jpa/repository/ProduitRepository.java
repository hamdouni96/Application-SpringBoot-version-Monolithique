package spring.jpa.repository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import spring.jpa.model.Produit;
public interface ProduitRepository extends JpaRepository<Produit, Long> {
// Retourner la page des Produits selon une recherche par designation
Page<Produit> findByDesignationLike(String mc, Pageable pageable);


Page<Produit> findByDesignationLikeAndActif(String mc,boolean actif, Pageable pageable);
Page<Produit> findByDateAchatBetween(LocalDate dateDebut, LocalDate dateFin, PageRequest of);
Page<Produit> findByDateAchatAfter(LocalDate dateDebut, PageRequest of);
Page<Produit> findByCategorieIdAndDesignationLike(Long categorieId, String string, PageRequest of);
Page<Produit> findByDateAchatBefore(LocalDate dateFin, PageRequest of);

List<Produit> findByQuantiteLessThan(int quantite);

@Query(value = "SELECT p.id, p.designation, p.prix, COUNT(fp.produit_id) as quantite " +
        "FROM produit p JOIN facture_produit fp ON p.id = fp.produit_id " +
        "GROUP BY p.id, p.designation, p.prix " +
        "ORDER BY COUNT(fp.produit_id) DESC LIMIT 1", nativeQuery = true)
Object[] findMostSollicitedProduit();


}