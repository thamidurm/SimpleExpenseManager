package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.contract;

import android.provider.BaseColumns;

public final class TransactionContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private TransactionContract() {}

    /* Inner class that defines the table contents */
    public static class TransactionEntry implements BaseColumns {
        static final String TABLE_NAME = "transaction";
        static final String COLUMN_NAME_ACCOUNT_NO = "account_no";
        static final String COLUMN_NAME_EXPENSE_TYPE = "expense_type";
        static final String COLUMN_NAME_AMOUNT = "amount";
        static final String COLUMN_NAME_TRANSACTION_DATE = "transaction_date";

        public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " +
                TransactionEntry.TABLE_NAME + " ( " +
                TransactionEntry._ID + "INTEGER PRIMARY KEY, " +
                TransactionEntry.COLUMN_NAME_ACCOUNT_NO + " INTEGER, " +
                TransactionEntry.COLUMN_NAME_EXPENSE_TYPE + " INTEGER, " +
                TransactionEntry.COLUMN_NAME_AMOUNT +" DECIMAL(10,2), " +
                TransactionEntry.COLUMN_NAME_TRANSACTION_DATE + " DATE);";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TransactionEntry.TABLE_NAME;
    }
}