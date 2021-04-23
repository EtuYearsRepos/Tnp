package fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository 
public interface UsersRepository extends JpaRepository<User, Long> {

    Comic findByPseudo(String pseudo);

    @Query(value="SELECT * FROM User u WHERE u.pseudo ILIKE %:keyword%", nativeQuery = true)
    List<User> findByKeyword(@Param("keyword") String keyword);

    @Query(value="SELECT * FROM User u WHERE u.pseudo ILIKE %:keyword%", nativeQuery = true)
    List<User> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

}