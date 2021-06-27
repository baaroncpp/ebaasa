package com.bkbwongo.core.ebaasa.base.utils;

import com.bkbwongo.common.utils.DateTimeUtil;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.base.jpa.models.AuditedEntity;
import com.bkbwongo.core.ebaasa.base.jpa.models.BaseEntity;
import com.bkbwongo.core.ebaasa.security.model.EbaasaLoginUser;
import com.bkbwongo.core.ebaasa.usermgt.jpa.models.TUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author bkaaron
 * @created on 25/06/2021
 * @project ebaasa-sms
 */
@Service
public class AuditServiceImp implements AuditService{
    @Override
    public void stampLongEntity(BaseEntity entity) {
        var date = DateTimeUtil.getCurrentUTCTime();
        if(entity.getId() == null){
            entity.setCreatedOn(date);
        }
        entity.setModifiedOn(DateTimeUtil.getCurrentUTCTime());
    }

    @Override
    public void stampAuditedEntity(AuditedEntity auditedEntity) {

        EbaasaLoginUser user = getLoggedInUser();
        Validate.notNull(user, "Only a logged in user can make this change");

        var date = DateTimeUtil.getCurrentUTCTime();
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

        final OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();

        if(authentication.isAuthenticated()){
            Map<String,?> decoded = (LinkedHashMap)( (OAuth2AuthenticationDetails)authentication.getDetails()).getDecodedDetails();
            var user = new EbaasaLoginUser();
            user.setUsername((String)decoded.get("username"));
            user.setId(Long.valueOf((Integer) decoded.get("id")));
            return user;
        }
        return null;
    }
}
