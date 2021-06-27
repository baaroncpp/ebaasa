package com.bkbwongo.common.constants;

/**
 * @author bkaaron
 * @created on 16/06/2021
 * @project ebaasa-sms
 */
public class ErrorMessageConstants {

    private ErrorMessageConstants() {}

    public static String APP_CLIENT_NOT_FOUND = "App client with identifier %s could not found";
    public static String APP_CLIENT_NOT_ENABLED = "App client with identifier %s has been disabled";
    public static final String INSUFFICIENT_FUNDS_ON_ACCOUNT = "Insufficient balance on wallet account for transaction";
    public static final String ACCOUNT_IS_CLOSED = "Account is already closed and cannot be operated";
    public static final String ACCOUNT_ALREADY_ASSIGNED = "Wallet account with ID %s is already assigned to another bank";//not wallets are assigned to banks
    public static final String ACCOUNT_BALANCE_MUST_BE_ZERO = "Account balance must be zero before its assigned or unassigned";
    public static final String ACCOUNT_NOT_ASSIGNED_TO_ANY_ENTITY = "Account with id %s not assigned to any entity";
    public static final String NULL_OBJECT_VALUE = "Null %s object value";
    public static final String NULL_VALUE = "Null %s value";
    public static final String WALLET_NOT_FOUND = "Wallet with ID: %s not found";
    public static final String WALLET_TRANSACTION_NOT_FOUND = "Wallet transaction with ID: %s not found";
    public static final String CASH_FLOW_NOT_FOUND = "Cash flow with ID: %s not found";
    public static final String SECOND_CASH_FLOW_APPROVED = "Cash flow approval already completed";
    public static final String FIRST_CASH_FLOW_APPROVED = "First Cash flow approval already completed";
    public static final String FIRST_CASH_FLOW_NOT_APPROVED = "First Cash flow approval not completed";
    public static final String CASH_FLOW_REJECTED = "Cash flow approval is rejected";
    public static final String CASH_FLOW_APPROVED_SUCCESSFULLY = "Cash flow approval successfully";
    public static final String SAME_WALLET_ACCOUNT = "Same Source and Destination wallet account";
    public static final String NULL_BANK_ACCOUNT = "Null bank account payload";
    public static final String NULL_COUNTRY_VALUE_OR_OBJECT = "Null country payload";
}
