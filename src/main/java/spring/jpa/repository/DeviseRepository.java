package spring.jpa.repository;
import spring.jpa.model.Devise;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeviseRepository extends JpaRepository<Devise, Long> {
    // Using HQL with @Query annotation
    @Query("SELECT d FROM Devise d WHERE d.type = :type")
    List<Devise> findByTypeDeviseUsingHql(@Param("type") String type);
}
