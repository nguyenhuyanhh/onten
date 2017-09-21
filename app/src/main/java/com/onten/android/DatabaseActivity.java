package com.onten.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhenting on 15/1/2016.
 */
public class DatabaseActivity extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Quiz";

    // tasks table name
    private static final String TABLE_QUEST = "Question ";

    // tasks Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_QUESTION = "question";
    private static final String KEY_ANSWER = "answer"; //correct option
    private static final String KEY_OPTION_A = "option_a"; //option a
    private static final String KEY_OPTION_B = "option_b"; //option b
    private static final String KEY_OPTION_C = "option_c"; //option c
    private static final String KEY_OPTION_D = "option_d"; //option d
    private static final String KEY_OPTION_E = "option_e"; //option e

    private SQLiteDatabase dbase;

    public DatabaseActivity(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dbase = db;
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUEST + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUESTION
                + " TEXT, " + KEY_ANSWER + " TEXT, " + KEY_OPTION_A + " TEXT, "
                + KEY_OPTION_B + " TEXT, " + KEY_OPTION_C + " TEXT, "
                + KEY_OPTION_D + " TEXT, " + KEY_OPTION_E + " TEXT)";
        db.execSQL(sql);
        addQuestions();
        //db.close();
    }

    private void addQuestions() {

        QuestionActivity qn1 = new QuestionActivity("1. The ARM7TDMI processor has how many flags, modes and states.",
                "a. 4 flags,1 mode and 7 states", "b. 5 flags, 2 modes and 6 states", "c. 3 flags, 7 modes and 2 states",
                "d. 2 flags, 6 modes and 2 states", "e. None of the above", "e. None of the above");
        this.addQuestion(qn1);

        QuestionActivity qn2 = new QuestionActivity("2. Which of the following number(s) is/are NaN(s) in IEEE 754 format?",
                "a. 0xFF800001", "b. 0xFFFFFFFF", "c. 0x7FD55555", "d. None of the above", "e. All of the above",
                "e. All of the above");
        this.addQuestion(qn2);

        QuestionActivity qn3 = new QuestionActivity("3. Represent the decimal number, −11, using 32-bit precision 2’s complement form.",
                "a. 0x8000000B", "b. 0xFFFFFFF5", "c. 0xEFFFFFFF", "d. 0x80000005", "e. None of the above",
                "b. 0xFFFFFFF5");
        this.addQuestion(qn3);

        QuestionActivity qn4 = new QuestionActivity("4. What are the stages in ARM7TDMI pipeline architecture?",
                "a. ARM7TDMI doesn’t use pipeline architecture.", "b. FETCH, EXECUTE, DECODE",
                "c. FETCH, DECODE,  EXECUTE, MEMORY, WRITE", "d. DECODE, EXECUTE, FETCH", "e. FETCH, DECODE, EXECUTE",
                "e. FETCH, DECODE, EXECUTE");
        this.addQuestion(qn4);

        QuestionActivity qn5 = new QuestionActivity("5. Consider the following instructions and determine the values of the NZCV flags." +
                "     MOV r0, #8" +
                "     SUBS r1, r0, r0",
                "a. N = 0, Z = 1, C = 1, V = 0", "b. N = 0, Z = 0, C = 0, V = 0", "c. N = 1, Z = 0, C = 1, V = 1",
                "d. N = 1, Z = 1, C = 0, V = 0", "e. None of the above", "a. N = 0, Z = 1, C = 1, V = 0");
        this.addQuestion(qn5);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);
        // Create tables again
        onCreate(db);
    }

    // Adding new question
    public void addQuestion(QuestionActivity quest) {

        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUESTION, quest.getQUESTION());
        values.put(KEY_ANSWER, quest.getANSWER());
        values.put(KEY_OPTION_A, quest.getOPTION_A());
        values.put(KEY_OPTION_B, quest.getOPTION_B());
        values.put(KEY_OPTION_C, quest.getOPTION_C());
        values.put(KEY_OPTION_D, quest.getOPTION_D());
        values.put(KEY_OPTION_E, quest.getOPTION_E());

        // Inserting Row
        dbase.insert(TABLE_QUEST, null, values);
    }

    public List<QuestionActivity> getAllQuestions() {

        List<QuestionActivity> questionList = new ArrayList<QuestionActivity>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
        dbase = this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                QuestionActivity quest = new QuestionActivity();
                quest.setID(cursor.getInt(0));
                quest.setQUESTION(cursor.getString(1));
                quest.setANSWER(cursor.getString(2));
                quest.setOPTION_A(cursor.getString(3));
                quest.setOPTION_B(cursor.getString(4));
                quest.setOPTION_C(cursor.getString(5));
                quest.setOPTION_D(cursor.getString(6));
                quest.setOPTION_E(cursor.getString(7));
                questionList.add(quest);
            } while (cursor.moveToNext());
        }

        // return quest list
        return questionList;
    }

    public int rowcount() {
        int row = 0;
        String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        row = cursor.getCount();
        return row;
    }
}
