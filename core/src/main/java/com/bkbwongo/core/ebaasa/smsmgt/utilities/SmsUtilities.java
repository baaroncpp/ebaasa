package com.bkbwongo.core.ebaasa.smsmgt.utilities;

import com.bkbwongo.common.constants.ErrorMessageConstants;
import com.bkbwongo.common.utils.Validate;
import com.bkbwongo.core.ebaasa.base.enums.AccountStatusEnum;
import com.bkbwongo.core.ebaasa.base.enums.AccountTypeEnum;
import com.bkbwongo.core.ebaasa.smsmgt.jpa.models.TSmsAccount;
import com.bkbwongo.core.ebaasa.walletmgt.jpa.models.TWallet;

/**
 * @author bkaaron
 * @created on 07/07/2021
 * @project ebaasa-sms
 */
public class SmsUtilities {

    private SmsUtilities() {
    }

    public static  void checkThatAccountCanBeAssignedAsMain(TSmsAccount smsAccount){
        SmsUtilities.checkThatAccountCanBeAssigned(smsAccount);
        Validate.isTrue(smsAccount.getSmsAccountType().equals(AccountTypeEnum.MAIN_SMS),"Only a main account can be assigned to a bank");
    }

    public static void checkThatAccountCanBeAssigned(TSmsAccount smsAccount){
        Validate.isTrue(!smsAccount.getAssigned(), ErrorMessageConstants.ACCOUNT_ALREADY_ASSIGNED, smsAccount.getId());
        Validate.isTrue(!smsAccount.getAccountStatus().equals(AccountStatusEnum.CLOSED), ErrorMessageConstants.ACCOUNT_IS_CLOSED);
        Validate.isTrue(smsAccount.getAccountStatus().equals(AccountStatusEnum.NOT_ACTIVE), ErrorMessageConstants.ACCOUNT_ALREADY_ASSIGNED, smsAccount.getId());
        Validate.isTrue(smsAccount.getAccountSmsCount() == 0, ErrorMessageConstants.ACCOUNT_BALANCE_MUST_BE_ZERO);
    }

    public static void checkThatAccountCanBeUnAssigned(TSmsAccount smsAccount){
        Validate.isTrue(smsAccount.getAccountStatus().equals(AccountStatusEnum.ACTIVE),"Account is already not assigned");
        Validate.isTrue(smsAccount.getAccountSmsCount() == 0, ErrorMessageConstants.ACCOUNT_BALANCE_MUST_BE_ZERO );
    }

    public static void checkThatTransactionWontResultInNegativeBalance(TSmsAccount smsAccount, Long numberOfSms){
        final Long fromAccountBalance = smsAccount.getAccountSmsCount();
        final Long newFromAccountBalance = fromAccountBalance - numberOfSms;

        Validate.isTrue(newFromAccountBalance >= 0, ErrorMessageConstants.INSUFFICIENT_FUNDS_ON_ACCOUNT);
    }

    public static void checkThatAccountCanTransact(TWallet wallet){
        Validate.isTrue(wallet.getAccountStatus().equals(AccountStatusEnum.ACTIVE), ErrorMessageConstants.ACCOUNT_IS_CLOSED);
        Validate.isTrue(wallet.getAssigned(),ErrorMessageConstants.ACCOUNT_NOT_ASSIGNED_TO_ANY_ENTITY, wallet.getId());
    }
}
