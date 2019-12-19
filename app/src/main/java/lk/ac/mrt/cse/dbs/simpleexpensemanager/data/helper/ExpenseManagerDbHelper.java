package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.contract.AccountContract;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.contract.TransactionContract;

public class ExpenseManagerDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "170393U.db";

    public ExpenseManagerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AccountContract.AccountEntry.SQL_CREATE_ENTRIES);
        db.execSQL(TransactionContract.TransactionEntry.SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(AccountContract.AccountEntry.SQL_DELETE_ENTRIES);
        db.execSQL(TransactionContract.TransactionEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}