package com.devbank.receiversregister.repository.jpa.repositories;

import com.devbank.receiversregister.repository.jpa.entities.BankUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface BankUserRepository extends JpaRepository<BankUserEntity, Long>, QuerydslPredicateExecutor<BankUserEntity> {

    Optional<BankUserEntity> findByUserId(String bankUserId);

    @Query("SELECT COUNT(*) FROM BankUserEntity bankUser " +
            "INNER JOIN bankUser.receivers userReceivers " +
            "WHERE bankUser.userId = :userId AND userReceivers.IBAN = :IBAN ")
    int getCountOfReceiverByIBAN(String userId, String IBAN);

}
