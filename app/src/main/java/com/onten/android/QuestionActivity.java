package com.onten.android;

/**
 * Created by zhenting on 15/1/2016.
 */
public class QuestionActivity {

    private int ID;
    private String QUESTION;
    private String OPTION_A;
    private String OPTION_B;
    private String OPTION_C;
    private String OPTION_D;
    private String OPTION_E;
    private String ANSWER;

    public QuestionActivity() {
        ID = 0;
        QUESTION = "";
        OPTION_A = "";
        OPTION_B = "";
        OPTION_C = "";
        OPTION_D = "";
        OPTION_E = "";
        ANSWER = "";
    }

    public QuestionActivity(String Question, String Option_A, String Option_B, String Option_C,
                            String Option_D, String Option_E, String Answer) {

        QUESTION = Question;
        OPTION_A = Option_A;
        OPTION_B = Option_B;
        OPTION_C = Option_C;
        OPTION_D = Option_D;
        OPTION_E = Option_E;
        ANSWER = Answer;

    }

    public int getID() {
        return ID;
    }

    public String getQUESTION() {
        return QUESTION;
    }

    public String getOPTION_A() {
        return OPTION_A;
    }

    public String getOPTION_B() {
        return OPTION_B;
    }

    public String getOPTION_C() {
        return OPTION_C;
    }

    public String getOPTION_D() {
        return OPTION_D;
    }

    public String getOPTION_E() {
        return OPTION_E;
    }

    public String getANSWER() {
        return ANSWER;
    }

    public void setID(int id) {
        ID = id;
    }

    public void setQUESTION(String Question) {
        QUESTION = Question;
    }

    public void setOPTION_A(String Option_A) {
        OPTION_A = Option_A;
    }

    public void setOPTION_B(String Option_B) {
        OPTION_B = Option_B;
    }

    public void setOPTION_C(String Option_C) {
        OPTION_C = Option_C;
    }

    public void setOPTION_D(String Option_D) {
        OPTION_D = Option_D;
    }

    public void setOPTION_E(String Option_E) {
        OPTION_E = Option_E;
    }

    public void setANSWER(String Answer) {
        ANSWER = Answer;
    }

}
