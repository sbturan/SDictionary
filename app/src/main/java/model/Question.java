package model;

/**
 * Created by seckin on 7/15/2016.
 */
public class Question {

    private String questionText;
    private String choise1;
    private String choise2;
    private String choise3;
    private String choise4;
    private String answer;

    public Question(String questionText,String choise1,String choise2,String choise3,String choise4,String answer){
        this.questionText=questionText;
        this.choise1=choise1;
        this.choise2=choise2;
        this.choise3=choise3;
        this.choise4=choise4;
        this.answer=answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getChoise1() {
        return choise1;
    }

    public void setChoise1(String choise1) {
        this.choise1 = choise1;
    }

    public String getChoise2() {
        return choise2;
    }

    public void setChoise2(String choise2) {
        this.choise2 = choise2;
    }

    public String getChoise3() {
        return choise3;
    }

    public void setChoise3(String choise3) {
        this.choise3 = choise3;
    }

    public String getChoise4() {
        return choise4;
    }

    public void setChoise4(String choise4) {
        this.choise4 = choise4;
    }
}
