package com.bkbwongo.core.ebaasa.base.utils;

import com.bkbwongo.common.utils.DateTimeUtil;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.base.jpa.models.AuditedEntity;
import com.bkbwongo.core.ebaasa.base.jpa.models.BaseEntity;
import com.bkbwongo.core.ebaasa.security.model.EbaasaLoginUser;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author bkaaron
 * @created on 25/06/2021
 * @project ebaasa-sms
 */
@Service
public class AuditServiceImp implements AuditService{
    @Override
    public void stampLongEntity(BaseEntity entity) {
        Date date = DateTimeUtil.getCurrentUTCTime();
        if(entity.getId() == null){
            entity.setCreatedOn(date);
        }
        entity.setModifiedOn(DateTimeUtil.getCurrentUTCTime());
    }

    @Override
    public void stampAuditedEntity(AuditedEntity auditedEntity) {

        EbaasaLoginUser user = getLoggedInUser();
        Validate.notNull(user, "Only a logged in user can make this change");

        Date date = DateTimeUtil.getCurrentUTCTime();
        var tUser = new TUser();
        tUser.setId(user.getId());

        if(auditedEntity.getId() == null){
            auditedEntity.setCreatedOn(date);
            auditedEntity.setCreatedBy(tUser);
        }

        auditedEntity.setModifiedBy(tUser);
        auditedEntity.setModifiedOn(date);
    }

    @Override
    public EbaasaLoginUser getLoggedInUser() {
        return null;
    }
}
