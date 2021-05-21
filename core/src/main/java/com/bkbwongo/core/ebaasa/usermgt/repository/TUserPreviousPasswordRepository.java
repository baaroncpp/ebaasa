package com.bkbwongo.core.ebaasa.usermgt.repository;

import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUserPreviousPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author bkaaron
 * @created on 21/05/2021
 * @project ebaasa-sms
 */
@Repository
public interface TUserPreviousPasswordRepository extends JpaRepository<TUserPreviousPassword, Long> {
}
