package fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChuckFactsRepository extends JpaRepository<ChuckFact, Long> {

    ChuckFact findByName(String name);

}