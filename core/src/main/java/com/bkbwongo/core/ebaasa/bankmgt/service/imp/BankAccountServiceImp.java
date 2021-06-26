package com.bkbwongo.core.ebaasa.bankmgt.service.imp;

import com.bkbwongo.common.constants.ErrorMessageConstants;
import com.bkbwongo.common.utils.DateTimeUtil;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.bankmgt.dto.BankAccountDto;
import com.bkbwongo.core.ebaasa.bankmgt.dto.service.BankManagementDTOMapperService;
import com.bkbwongo.core.ebaasa.bankmgt.jpa.models.TBankAccount;
import com.bkbwongo.core.ebaasa.bankmgt.repository.TBankAccountRepository;
import com.bkbwongo.core.ebaasa.bankmgt.service.BankAccountService;
import com.bkbwongo.core.ebaasa.base.jpa.models.TCountry;
import com.bkbwongo.core.ebaasa.base.repository.TCountryRepository;
import com.bkbwongo.core.ebaasa.base.utils.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author bkaaron
 * @created on 23/06/2021
 * @project ebaasa-sms
 */
@Service
public class BankAccountServiceImp implements BankAccountService {

    private TBankAccountRepository bankAccountRepository;
    private BankManagementDTOMapperService bankManagementDTOMapperService;
    private TCountryRepository countryRepository;
    private AuditService auditService;

    @Autowired
    public BankAccountServiceImp(TBankAccountRepository bankAccountRepository,
                                 BankManagementDTOMapperService bankManagementDTOMapperService,
                                 TCountryRepository countryRepository,
                                 AuditService auditService) {
        this.bankAccountRepository = bankAccountRepository;
        this.bankManagementDTOMapperService = bankManagementDTOMapperService;
        this.countryRepository = countryRepository;
        this.auditService = auditService;
    }

    @Override
    public Optional<TBankAccount> addBankAccount(BankAccountDto bankAccountDto) {

        bankAccountDto.validate();

        Validate.notNull(bankAccountDto.getCountry(), ErrorMessageConstants.NULL_COUNTRY_VALUE_OR_OBJECT);

        Optional<TCountry> country = countryRepository.findByIsoAlpha2(bankAccountDto.getCountry().getIsoAlpha2());
        Validate.isTrue(country.isPresent(),"Country code %s is not supported", bankAccountDto.getCountry().getIsoAlpha2());

        var bankAccount = bankManagementDTOMapperService.convertDTOToTBankAccount(bankAccountDto);
        bankAccount.setCountry(country.get());
        auditService.stampLongEntity(bankAccount);

        bankAccountRepository.save(bankAccount);

        Optional<TBankAccount> account = bankAccountRepository.findByAccountNumber(bankAccount.getAccountNumber());
        Validate.isTrue(account.isPresent(),"Failed to create entry for bank account with account number %s",bankAccount.getAccountName());

        return Optional.of(bankAccount);
    }

    @Override
    public Optional<TBankAccount> updateBankAccount(BankAccountDto bankAccountDto) {

        bankAccountDto.validate();

        Optional<TBankAccount> bankAccount = bankAccountRepository.findById(bankAccountDto.getId());

        Validate.isTrue(bankAccount.isPresent(),"Failed to locate bank account with id %s for update",String.valueOf(bankAccountDto.getId()));

        var account = bankManagementDTOMapperService.convertDTOToTBankAccount(bankAccountDto);
        account.setModifiedOn(DateTimeUtil.getCurrentUTCTime());

        return Optional.of(bankAccountRepository.save(account));
    }

    @Override
    public Optional<TBankAccount> getBankAccountById(Long id) {

        Optional<TBankAccount> bankAccount = bankAccountRepository.findById(id);
        Validate.isTrue(bankAccount.isPresent(), ErrorMessageConstants.NULL_BANK_ACCOUNT);

        return bankAccount;
    }

    @Override
    public List<TBankAccount> getAllBankAccounts() {
        Iterable<TBankAccount> bankAccounts = bankAccountRepository.findAll();

        final List<TBankAccount> listOfBanks = new ArrayList<>();

        if (bankAccounts != null){
            bankAccounts.forEach(listOfBanks::add);
        }

        return listOfBanks;
    }
}
