package com.bkbwongo.core.ebaasa.walletmgt.repository;

import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWallet;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWalletGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 10/06/2021
 * @project ebaasa-sms
 */
@Repository
public interface TWalletRepository extends JpaRepository<TWallet, Long> {
    List<TWallet> findAllByTWalletGroup(Pageable pageable, TWalletGroup walletGroup);
    List<TWallet> findAllByCreatedOn(Pageable pageable);
    Optional<TWallet> findByCode(String code);
    Optional<TWallet> findByName(String name);
}
