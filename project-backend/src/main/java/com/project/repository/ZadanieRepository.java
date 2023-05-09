package com.project.repository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.project.model.Zadanie;

public interface ZadanieRepository extends JpaRepository<Zadanie, Integer> {
    //dwukropkiem oznacza się parametry zapytania
    @Query("SELECT z FROM Zadanie z WHERE z.projekt.projektId = :projektId ORDER BY z.kolejnosc")
    Page<Zadanie> findZadaniaProjektu(@Param("projektId") Integer projektId, Pageable pageable);
    @Query("SELECT z FROM Zadanie z WHERE z.projekt.projektId = :projektId ORDER BY z.kolejnosc")
    List<Zadanie> findZadaniaProjektu(@Param("projektId") Integer projektId);
}