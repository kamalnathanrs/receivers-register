package com.devbank.receiversregister.repository.jpa.repositories;

import com.devbank.receiversregister.repository.jpa.entities.BankUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface BankUserRepository extends JpaRepository<BankUserEntity, Long>, QuerydslPredicateExecutor<BankUserEntity> {
    Optional<BankUserEntity> findByUserId(String bankUserId);
}
