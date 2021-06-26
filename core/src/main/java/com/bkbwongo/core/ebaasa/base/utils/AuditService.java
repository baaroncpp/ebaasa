package com.bkbwongo.core.ebaasa.base.utils;

import com.bkbwongo.core.ebaasa.base.jpa.models.AuditedEntity;
import com.bkbwongo.core.ebaasa.base.jpa.models.BaseEntity;
import com.bkbwongo.core.ebaasa.security.model.EbaasaLoginUser;

/**
 * @author bkaaron
 * @created on 25/06/2021
 * @project ebaasa-sms
 */
public interface AuditService {
    void stampLongEntity(BaseEntity entity);
    void stampAuditedEntity(AuditedEntity auditedEntity);
    EbaasaLoginUser getLoggedInUser();
}
