package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.contract;

import android.provider.BaseColumns;

public final class AccountContract {

    private AccountContract() {}

    public static class AccountEntry implements BaseColumns {

        public static final String TABLE_NAME = "account";
        public static final String COLUMN_NAME_ACCOUNT_NO = "account_no";
		public static final String COLUMN_NAME_ACCOUNT_HOLDER_NAME = "holder_name";
		public static final String COLUMN_NAME_BALANCE = "balance";
        public static final String COLUMN_NAME_BANK_NAME = "bank_name";
		
		public static final String SQL_CREATE_TABLE = "CREATE TABLE " +
		AccountEntry.TABLE_NAME + " ( " +
		AccountEntry.COLUMN_NAME_ACCOUNT_NO + " TEXT PRIMARY KEY, " +
				AccountEntry.COLUMN_NAME_ACCOUNT_HOLDER_NAME +" TEXT, " +
				AccountEntry.COLUMN_NAME_BALANCE + " DECIMAL(10,2), " +
		AccountEntry.COLUMN_NAME_BANK_NAME + " TEXT );" ;
		
		public static final String SQL_DELETE_TABLE =
		"DROP TABLE IF EXISTS " + AccountEntry.TABLE_NAME;
    }
}