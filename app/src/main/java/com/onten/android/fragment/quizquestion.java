package com.onten.android.fragment;

/**
 * Created by Peiying.Lim on 12/5/2017.
 */

public class quizquestion {
    private String id;
    private String question;
    private String a;
    private String b;
    private String c;
    private String d;
    private String ans;
    private String explanation;
    private String quizset;

    public quizquestion() {
      /*Blank default constructor essential for Firebase*/
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getQuizset() {
        return quizset;
    }

    public void setQuizset(String quizset) {
        this.quizset = quizset;
    }
}
