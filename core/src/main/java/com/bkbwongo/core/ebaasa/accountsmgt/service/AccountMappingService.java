package com.bkbwongo.core.ebaasa.accountsmgt.service;

import com.bkbwongo.core.ebaasa.accountsmgt.dto.AccountLinkDto;
import com.bkbwongo.core.ebaasa.accountsmgt.dto.AccountMappingDto;
import com.bkbwongo.core.ebaasa.accountsmgt.jpa.models.TAccountMapping;

import java.util.Optional;

/**
 * @author bkaaron
 * @created on 29/06/2021
 * @project ebaasa-sms
 */
public interface AccountMappingService {
    Optional<TAccountMapping> addAccountMapping(AccountMappingDto accountMappingDto);
    Optional<TAccountMapping> linkAccount(AccountLinkDto accountLinkDto);
    Optional<TAccountMapping> unlinkAccount(AccountLinkDto accountLinkDto);
    Optional<TAccountMapping> linkSystemAccount(AccountLinkDto accountLinkDto);
    Optional<TAccountMapping> unlinkSystemAccount(AccountLinkDto accountLinkDto);
    Optional<TAccountMapping> getAccountMappingById(Long id);
    Optional<TAccountMapping> getAccountMappingByUserId(Long id);
}
