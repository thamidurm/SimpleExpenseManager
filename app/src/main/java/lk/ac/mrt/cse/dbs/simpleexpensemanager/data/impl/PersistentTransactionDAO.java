/*
 * Copyright 2015 Department of Computer Science and Engineering, University of Moratuwa.
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.SimpleFormatter;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.contract.TransactionContract;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * This is an In-Memory implementation of TransactionDAO interface. This is not a persistent storage. All the
 * transaction logs are stored in a LinkedList in memory.
 */
public class PersistentTransactionDAO implements TransactionDAO {
    private SQLiteOpenHelper dbHelper;

    public PersistentTransactionDAO(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        String dateString = (new SimpleDateFormat("yyyy-mm-dd")).format(date);

        ContentValues values = new ContentValues();
        values.put(TransactionContract.TransactionEntry.COLUMN_NAME_ACCOUNT_NO, accountNo);
        values.put(TransactionContract.TransactionEntry.COLUMN_NAME_TRANSACTION_DATE, dateString);
        values.put(TransactionContract.TransactionEntry.COLUMN_NAME_EXPENSE_TYPE, expenseType.toString());
        values.put(TransactionContract.TransactionEntry.COLUMN_NAME_AMOUNT, amount);

        db.insert(TransactionContract.TransactionEntry.TABLE_NAME, null, values);
    }

    private List<Transaction> getTransactionLogs(int count){
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        ArrayList<Transaction> transactions = new ArrayList<>();

        String[] projection = {
                TransactionContract.TransactionEntry.COLUMN_NAME_ACCOUNT_NO,
                TransactionContract.TransactionEntry.COLUMN_NAME_TRANSACTION_DATE,
                TransactionContract.TransactionEntry.COLUMN_NAME_EXPENSE_TYPE,
                TransactionContract.TransactionEntry.COLUMN_NAME_AMOUNT
        };

        String orderBy = TransactionContract.TransactionEntry.COLUMN_NAME_TRANSACTION_DATE + " DESC";
        String limit = (count > 0) ? String.valueOf(count) : null;

        Cursor cursor = db.query(
                TransactionContract.TransactionEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                orderBy,
                limit
        );

        while(cursor.moveToNext()){
            String expenseTypeString = cursor.getString(cursor.getColumnIndexOrThrow(
                    TransactionContract.TransactionEntry.COLUMN_NAME_EXPENSE_TYPE
            ));

            ExpenseType expenseType = (expenseTypeString.equals("EXPENSE")) ?
                    ExpenseType.EXPENSE : ExpenseType.INCOME;

            Date date = new Date();

            try{
                date = (new SimpleDateFormat("yyyy-mm-dd")).parse(
                        cursor.getString(cursor.getColumnIndexOrThrow(
                                TransactionContract.TransactionEntry.COLUMN_NAME_TRANSACTION_DATE
                        )));
            }
            catch(ParseException parseException){

            }

            String accountNo = cursor.getString(cursor.getColumnIndexOrThrow(
                    TransactionContract.TransactionEntry.COLUMN_NAME_ACCOUNT_NO
            ));

            double amount = cursor.getDouble(cursor.getColumnIndexOrThrow(
                    TransactionContract.TransactionEntry.COLUMN_NAME_AMOUNT
            ));

            transactions.add(new Transaction(date, accountNo,expenseType,amount));
        }
        cursor.close();
        return  transactions;
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        return getTransactionLogs(0);
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
       return getTransactionLogs(limit);
    }

}
