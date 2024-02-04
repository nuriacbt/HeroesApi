package es.heroesfactory.repositories;

import es.heroesfactory.repositories.entities.Heroe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroesRepository extends JpaRepository<Heroe, Integer> {

    @Query("SELECT h FROM Heroe h WHERE UPPER(h.name) LIKE %:name%")
    public List<Heroe> findByName(@Param("name") String name);


}
