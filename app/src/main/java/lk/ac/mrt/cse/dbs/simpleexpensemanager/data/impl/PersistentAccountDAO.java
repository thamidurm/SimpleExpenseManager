/*
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *                  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.contract.AccountContract.AccountEntry;

/**
 * This is an In-Memory implementation of the AccountDAO interface. This is not a persistent storage. A HashMap is
 * used to store the account details temporarily in the memory.
 */
public class PersistentAccountDAO implements AccountDAO {
    private SQLiteOpenHelper dbHelper;

	public PersistentAccountDAO(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
    

    @Override
    public List<String> getAccountNumbersList() {

        SQLiteDatabase db = this.dbHelper.getReadableDatabase();

        String[] projection = {AccountEntry.COLUMN_NAME_ACCOUNT_NO};

        Cursor cursor = db.query(
                AccountEntry.TABLE_NAME,
                projection,
                null,
                null,
                AccountEntry.COLUMN_NAME_ACCOUNT_NO,
                null,
                null
        );

        ArrayList<String> accountNumbers = new ArrayList<>();

        while (cursor.moveToNext()){
            accountNumbers.add( cursor.getString(cursor.getColumnIndexOrThrow(AccountEntry.COLUMN_NAME_ACCOUNT_NO)));
        }
        cursor.close();
        return accountNumbers;
    }

    @Override
    public List<Account> getAccountsList() {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();

        String[] projection = {AccountEntry.COLUMN_NAME_ACCOUNT_NO};

        Cursor cursor = db.query(
                AccountEntry.TABLE_NAME,
                projection,
                null,
                null,
                AccountEntry.COLUMN_NAME_ACCOUNT_NO,
                null,
                null
        );

        ArrayList<Account> accounts = new ArrayList<>();

        while (cursor.moveToNext()){
            accounts.add(new Account(
                    cursor.getString(cursor.getColumnIndexOrThrow(AccountEntry.COLUMN_NAME_ACCOUNT_NO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(AccountEntry.COLUMN_NAME_BANK_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(AccountEntry.COLUMN_NAME_ACCOUNT_HOLDER_NAME)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(AccountEntry.COLUMN_NAME_BALANCE))
            ));
        }
        cursor.close();
        return accounts;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();

        String[] projection = {
                AccountEntry.COLUMN_NAME_ACCOUNT_NO,
                AccountEntry.COLUMN_NAME_ACCOUNT_HOLDER_NAME,
                AccountEntry.COLUMN_NAME_BANK_NAME,
                AccountEntry.COLUMN_NAME_BALANCE
        };

        String selection = AccountEntry.COLUMN_NAME_ACCOUNT_NO + " = ?";
        String[] selectionArgs = {accountNo};
        Cursor cursor = db.query(
                AccountEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if(cursor.moveToNext()){
               Account account = new Account(
                  cursor.getString(cursor.getColumnIndexOrThrow(AccountEntry.COLUMN_NAME_ACCOUNT_NO)),
                  cursor.getString(cursor.getColumnIndexOrThrow(AccountEntry.COLUMN_NAME_BANK_NAME)),
                  cursor.getString(cursor.getColumnIndexOrThrow(AccountEntry.COLUMN_NAME_ACCOUNT_HOLDER_NAME)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(AccountEntry.COLUMN_NAME_BALANCE))
                );
            cursor.close();
            return account;
        }
        cursor.close();
        String msg = "Account " + accountNo + " is invalid.";
        throw new InvalidAccountException(msg);
    }

    @Override
    public void addAccount(Account account) {
		SQLiteDatabase db = this.dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(AccountEntry.COLUMN_NAME_ACCOUNT_NO, account.getAccountNo());
		values.put(AccountEntry.COLUMN_NAME_BANK_NAME, account.getBankName());
		values.put(AccountEntry.COLUMN_NAME_ACCOUNT_HOLDER_NAME, account.getAccountHolderName());
		values.put(AccountEntry.COLUMN_NAME_BALANCE, account.getBalance());
        db.insert(AccountEntry.TABLE_NAME, null, values);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        String selection = AccountEntry.COLUMN_NAME_ACCOUNT_NO + "= ?";
        String[] selectionArgs = { accountNo };
        int deletedRows = db.delete(AccountEntry.TABLE_NAME, selection, selectionArgs);
        if (deletedRows == 0){
              String msg = "Account " + accountNo + " is invalid.";
              throw new InvalidAccountException(msg);
        }
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

//	    Account account = this.getAccount(accountNo);
//        switch (expenseType) {
//            case EXPENSE:
//                account.setBalance(account.getBalance() - amount);
//                break;
//            case INCOME:
//                account.setBalance(account.getBalance() + amount);
//                break;
//        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        char operator = (expenseType == ExpenseType.EXPENSE) ? '-' : '+';
        values.put(AccountEntry.COLUMN_NAME_BALANCE, AccountEntry.COLUMN_NAME_BALANCE +
                " " + operator + " " + amount);

        String selection = AccountEntry.COLUMN_NAME_ACCOUNT_NO + " = ?";
        String[] selectionArgs = {accountNo};

        int count = db.update(
                AccountEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );

        if(count == 0){
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }
    }
}
