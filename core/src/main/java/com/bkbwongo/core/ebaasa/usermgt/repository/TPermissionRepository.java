package com.bkbwongo.core.ebaasa.usermgt.repository;

import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author bkaaron
 * @created on 13/05/2021
 * @project ebaasa-sms
 */
@Repository
public interface TPermissionRepository extends JpaRepository<TPermission, Long> {
}
