package com.bkbwongo.core.ebaasa.walletmgt.utitlities;

import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.common.constants.ErrorMessageConstants;
import com.bkbwongo.core.ebaasa.base.enums.AccountStatusEnum;
import com.bkbwongo.core.ebaasa.base.enums.WalletAccountTypeEnum;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWallet;

import java.math.BigDecimal;

/**
 * @author bkaaron
 * @created on 16/06/2021
 * @project ebaasa-sms
 */
public class WalletUtilities {

    public static  void checkThatAccountCanBeAssignedAsMain(TWallet wallet){
        WalletUtilities.checkThatAccountCanBeAssigned(wallet);
        Validate.isTrue(wallet.getWalletGroup().getGroupType().equals(WalletAccountTypeEnum.MAIN_CASH),"Only a main account can be assigned to a bank");

    }

    public static void checkThatAccountCanBeAssigned(TWallet wallet){
        Validate.isTrue(!wallet.getAssigned(), ErrorMessageConstants.ACCOUNT_ALREADY_ASSIGNED, wallet.getId());
        Validate.isTrue(!wallet.getAccountStatus().equals(AccountStatusEnum.CLOSED), ErrorMessageConstants.ACCOUNT_IS_CLOSED);
        Validate.isTrue(wallet.getAccountStatus().equals(AccountStatusEnum.NOT_ACTIVE), ErrorMessageConstants.ACCOUNT_ALREADY_ASSIGNED, wallet.getId());
        Validate.isTrue(wallet.getAvailableBalance().compareTo(BigDecimal.ZERO) == 0,ErrorMessageConstants.ACCOUNT_BALANCE_MUST_BE_ZERO);
    }

    public static void checkThatTransactionWontResultInNegativeBalance(TWallet wallet, BigDecimal transactionAmount){
        final BigDecimal fromAccountBalance = wallet.getAvailableBalance();
        final BigDecimal newFromAccountBalance = fromAccountBalance.subtract(transactionAmount);

        /*
        compareTo on bigDecimal
        This method returns -1 if the BigDecimal is less than val,
        1 if the BigDecimal is greater than val and 0 if the BigDecimal is equal to val*/
        Validate.isTrue(newFromAccountBalance.compareTo(BigDecimal.ZERO) >= 0, ErrorMessageConstants.INSUFFICIENT_FUNDS_ON_ACCOUNT);
    }

    public static void checkThatAccountCanTransact(TWallet wallet){
        Validate.isTrue(wallet.getAccountStatus().equals(AccountStatusEnum.ACTIVE), ErrorMessageConstants.ACCOUNT_IS_CLOSED);
        Validate.isTrue(wallet.getAssigned(),ErrorMessageConstants.ACCOUNT_NOT_ASSIGNED_TO_ANY_ENTITY, wallet.getId());
    }
}
