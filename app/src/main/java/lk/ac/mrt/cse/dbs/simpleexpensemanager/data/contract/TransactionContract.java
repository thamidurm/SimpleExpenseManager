package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.contract;

import android.provider.BaseColumns;

public final class TransactionContract {

    private TransactionContract() {}

    public static class TransactionEntry implements BaseColumns {

        public static final String TABLE_NAME = "transaction_log";
        public static final String COLUMN_NAME_ACCOUNT_NO = "account_no";
        public static final String COLUMN_NAME_EXPENSE_TYPE = "expense_type";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_TRANSACTION_DATE = "transaction_date";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " +
                TransactionEntry.TABLE_NAME + " ( " +
                TransactionEntry._ID + "INTEGER PRIMARY KEY, " +
                TransactionEntry.COLUMN_NAME_ACCOUNT_NO + " TEXT, " +
                TransactionEntry.COLUMN_NAME_AMOUNT +" DECIMAL(10,2), " +
                TransactionEntry.COLUMN_NAME_EXPENSE_TYPE + " VARCHAR(255), " +
                TransactionEntry.COLUMN_NAME_TRANSACTION_DATE + " DATE);";

        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TransactionEntry.TABLE_NAME;
    }
}