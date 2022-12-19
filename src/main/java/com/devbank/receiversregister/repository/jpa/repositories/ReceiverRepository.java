package com.devbank.receiversregister.repository.jpa.repositories;

import com.devbank.receiversregister.repository.jpa.entities.BankUserEntity;
import com.devbank.receiversregister.repository.jpa.entities.ReceiverEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface ReceiverRepository extends JpaRepository<ReceiverEntity, Long>, QuerydslPredicateExecutor<BankUserEntity> {
    Page<ReceiverEntity> findAllByBankUser_UserId(String userId, Pageable pageable);

    Optional<ReceiverEntity> findByIdAndBankUser_UserId(Long receiverId, String userId);

    /**
     * Gets count of receiver for a bank user by IBAN and by receiverId if provided.
     * @param userId ID of the bank user.
     * @param IBAN IBAN of the receiver.
     * @param receiverId Match by receiver id if provided. If null value provided this param will be skipped.
     * @return
     */
    @Query("SELECT COUNT(*) FROM ReceiverEntity receiver " +
            "INNER JOIN receiver.bankUser bankUser " +
            "WHERE UPPER(receiver.IBAN) = UPPER(:IBAN) " +
            "AND bankUser.userId = :userId " +
            "AND (:receiverId is null or receiver.id <> :receiverId)")
    int getCountOfUserReceiverByIBANAndReceiverNotMatching(String userId, String IBAN, Long receiverId);
}
