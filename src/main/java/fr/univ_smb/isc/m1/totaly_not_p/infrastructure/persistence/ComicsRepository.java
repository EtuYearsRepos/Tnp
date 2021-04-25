package fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository 
public interface ComicsRepository extends JpaRepository<Comic, Long> {

    Comic findByTitle(String title);

    @Query(value="SELECT * FROM Comic c WHERE c.title ILIKE %:keyword% OR c.writer ILIKE %:keyword%", nativeQuery = true)
    List<Comic> findByKeyword(@Param("keyword") String keyword);

    @Query(value="SELECT * FROM Comic c WHERE c.title ILIKE %:keyword% OR c.writer ILIKE %:keyword%", nativeQuery = true)
    List<Comic> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

}