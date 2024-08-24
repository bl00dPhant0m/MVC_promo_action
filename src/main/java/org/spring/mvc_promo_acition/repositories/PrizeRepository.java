package org.spring.mvc_promo_acition.repositories;

import jakarta.transaction.Transactional;
import org.spring.mvc_promo_acition.dto.PrizeDTO;
import org.spring.mvc_promo_acition.entiies.Prize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrizeRepository extends JpaRepository<Prize, Long> {

    //HQL
    @Transactional
    @Modifying
    @Query("UPDATE Prize p SET p.status = CASE WHEN p.status = true THEN false ELSE true END WHERE p.id = :id")
    void updatePrizeStatusById(@Param("id") Long id);

    @Query("SELECT NEW org.spring.mvc_promo_acition.dto.PrizeDTO(p.nameOfPrize, p.path) FROM Prize p GROUP BY p.nameOfPrize, p.path")
    List<PrizeDTO> findAllUniquePrizes();
//    //SQL
//    @Modifying
//    @Query(value = "UPDATE prizes SET status = not (select status from prizes where id = :id) WHERE id = :id", nativeQuery = true)
//    void updatePrizeStatusById(@Param("id") Long id, @Param("status") boolean status);


    Prize findByCode(String code);
}
