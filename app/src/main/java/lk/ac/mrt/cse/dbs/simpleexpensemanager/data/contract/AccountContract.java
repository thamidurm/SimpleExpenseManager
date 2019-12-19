package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.contract;

import android.provider.BaseColumns;

public final class AccountContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private AccountContract() {}

    /* Inner class that defines the table contents */
    public static class AccountEntry implements BaseColumns {
        public static final String TABLE_NAME = "Account";
        public static final String COLUMN_NAME_ACCOUNT_NO = "account_no";
        public static final String COLUMN_NAME_BANK_NAME = "bank_name";
		public static final String COLUMN_NAME_ACCOUNT_HOLDER_NAME = "account_holder_name";
		public static final String COLUMN_NAME_BALANCE = "balance";
		
		public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " +
		AccountEntry.TABLE_NAME + " ( " +
		AccountEntry.COLUMN_NAME_ACCOUNT_NO + " STRING PRIMARY KEY" +
		AccountEntry.COLUMN_NAME_BANK_NAME + " TEXT, " +
		AccountEntry.COLUMN_NAME_ACCOUNT_HOLDER_NAME +" TEXT, " +
		AccountEntry.COLUMN_NAME_BALANCE + " DECIMAL(10,2));";
		
		public static final String SQL_DELETE_ENTRIES =
		"DROP TABLE IF EXISTS " + AccountEntry.TABLE_NAME;
    }
}