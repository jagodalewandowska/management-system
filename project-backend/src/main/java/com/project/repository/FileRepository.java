package com.project.repository;

import com.project.model.FileInfo;
import com.project.model.Projekt;
import com.project.model.Zadanie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileInfo, Integer> {
    @Query("SELECT f FROM FileInfo f WHERE f.projekt.projektId = :projektId")
    Page<FileInfo> findFileProjektu(@Param("projektId") Integer projektId, Pageable pageable);
    @Query("SELECT f FROM FileInfo f WHERE f.projekt.projektId = :projektId")
    List<FileInfo> findFileProjektu(@Param("projektId") Integer projektId);
    Page<FileInfo> findByProjektProjektId(Integer projektId, Pageable pageable);
    Page<FileInfo> findByProjekt(Projekt projekt, Pageable pageable);
}
