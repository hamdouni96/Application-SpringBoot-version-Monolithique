package spring.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import spring.jpa.model.Reglement;

public interface ReglementRepository extends JpaRepository<Reglement, Long> {
	  
    // Using HQL with @Query annotation
    @Query("SELECT r FROM Reglement r WHERE r.modePaiement = :modePaiement")
    List<Reglement> findByModePaiementUsingHql(@Param("modePaiement") String modePaiement);
}
