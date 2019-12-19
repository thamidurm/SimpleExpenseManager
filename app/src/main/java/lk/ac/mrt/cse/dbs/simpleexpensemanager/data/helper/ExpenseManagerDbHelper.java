package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.contract.AccountContract.AccountEntry;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.contract.TransactionContract.TransactionEntry;

public class ExpenseManagerDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "170393U.db";

    public ExpenseManagerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AccountEntry.SQL_CREATE_TABLE);
        db.execSQL(TransactionEntry.SQL_CREATE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(AccountEntry.SQL_DELETE_TABLE);
        db.execSQL(TransactionEntry.SQL_DELETE_TABLE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}