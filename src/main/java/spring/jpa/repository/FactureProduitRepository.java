package spring.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.jpa.model.FactureProduit;

public interface FactureProduitRepository extends JpaRepository<FactureProduit, Long> {
    // Ajoutez des méthodes personnalisées si nécessaire
}
