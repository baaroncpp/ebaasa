package com.bkbwongo.common.constants;

/**
 * @author bkaaron
 * @created on 16/06/2021
 * @project ebaasa-sms
 */
public class ErrorMessageConstants {
    public static final String INSUFFICIENT_FUNDS_ON_ACCOUNT = "Insufficient balance on wallet account for transaction";
    public static final String ACCOUNT_IS_CLOSED = "Account is already closed and cannot be operated";
    public static final String ACCOUNT_ALREADY_ASSIGNED = "Wallet account with ID %s is already assigned to another bank";//not wallets are assigned to banks
    public static final String ACCOUNT_BALANCE_MUST_BE_ZERO = "Account balance must be zero before its assigned or unassigned";
    public static final String ACCOUNT_NOT_ASSIGNED_TO_ANY_ENTITY = "Account with id %s not assigned to any entity";
}
