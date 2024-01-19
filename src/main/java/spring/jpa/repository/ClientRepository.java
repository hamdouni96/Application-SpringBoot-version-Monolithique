package spring.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import spring.jpa.model.Client;
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByNom(String nom);
    // Using HQL with @Query annotation
    @Query("SELECT c FROM Client c WHERE c.nom = :nom")
    List<Client> findByNomUsingHql(@Param("nom") String nom);

	
	
}

