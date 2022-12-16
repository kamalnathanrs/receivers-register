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
}
