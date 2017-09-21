package com.onten.android.DataContainer;

/**
 * Created by Peiying.Lim on 6/4/2017.
 */

public class ExamResultContainer {
    public String getQuizeType() {
        return quizeType;
    }

    public void setQuizeType(String quizeType) {
        this.quizeType = quizeType;
    }

    public String getQquestion() {
        return qquestion;
    }

    public void setQquestion(String qquestion) {
        this.qquestion = qquestion;
    }

    public String getQanswer() {
        return qanswer;
    }

    public void setQanswer(String qanswer) {
        this.qanswer = qanswer;
    }

    public String getQtime() {
        return qtime;
    }

    public void setQtime(String qtime) {
        this.qtime = qtime;
    }

    String quizeType;
    String qquestion;
    String qanswer;
    String qtime;

    public String getQusername() {
        return qusername;
    }

    public void setQusername(String qusername) {
        this.qusername = qusername;
    }

    String qusername = "";

    public String getQdate() {
        return qdate;
    }

    public void setQdate(String qdate) {
        this.qdate = qdate;
    }

    String qdate;
}
