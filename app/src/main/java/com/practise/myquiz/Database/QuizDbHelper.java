package com.practise.myquiz.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.practise.myquiz.Question;
import com.practise.myquiz.fixtures.QuizContract.*;

import java.util.ArrayList;
import java.util.List;


public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyAwesomeQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +  QuestionTable.TABLE_NAME +
                " ( " + QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLUMN_QUESTION + " TEXT, " +
                QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionTable.COLUMN_DIFFICULTY + " TEXT" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    private void fillQuestionsTable(){
        Question q1 = new Question("Easy: A is correct",
                "A", "B", "C", 1, Question.DIFFICULTY_EASY);
        addQuestion(q1);
        Question q2 = new Question("Medium: B is correct",
                "A", "B", "C", 2, Question.DIFFICULTY_MEDIUM);
        addQuestion(q2);
        Question q3 = new Question("Medium: C is correct",
                "A", "B", "C", 3, Question.DIFFICULTY_MEDIUM);
        addQuestion(q3);
        Question q4 = new Question("Hard: A is correct",
                "A", "B", "C", 1, Question.DIFFICULTY_HARD);
        addQuestion(q4);
        Question q5 = new Question("Hard: B is correct",
                "A", "B", "C", 2, Question.DIFFICULTY_HARD);
        addQuestion(q5);
        Question q6 = new Question("Hard: C is correct",
                "A", "B", "C", 3, Question.DIFFICULTY_HARD);
        addQuestion(q6);

    }
    private void addQuestion(Question question){
        ContentValues cv = new ContentValues();
        cv.put(QuestionTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionTable.COLUMN_OPTION1,question.getOption1());
        cv.put(QuestionTable.COLUMN_OPTION2,question.getOption2());
        cv.put(QuestionTable.COLUMN_OPTION3,question.getOption3());
        cv.put(QuestionTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionTable.COLUMN_DIFFICULTY, question.getDifficulty());
        db.insert(QuestionTable.TABLE_NAME, null, cv);
    }

    public ArrayList<Question>getAllQuestions(){
        ArrayList<Question>questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionTable.TABLE_NAME, null);
        if(c.moveToFirst()){
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionTable.COLUMN_DIFFICULTY)));
                questionList.add(question);
            }while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    public ArrayList<Question>getQuestions(String difficulty){
        ArrayList<Question>questionList = new ArrayList<>();
        db = getReadableDatabase();
        String[] selectionArgs = new String[]{difficulty};
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionTable.TABLE_NAME +
                " WHERE " + QuestionTable.COLUMN_DIFFICULTY + " = ?", selectionArgs);
        if(c.moveToFirst()){
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionTable.COLUMN_DIFFICULTY)));
                questionList.add(question);
            }while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME);
        onCreate(db);
    }
}
