package com.lgd.es;

import java.sql.Timestamp;

/**
 * Describe:
 *
 * @author: guodong.li
 * @datetime: 2017/5/23 9:58
 */
public class CorrentAnswerInfo {

    private Integer id;
    private String ask;
    private String answer;
    private String pureTextAnswer;
    private String classifyName;
    private String state;
    private String askType;
    private String matchMode;
    private String validTerm;
    //private Timestamp updateTime;
    private String updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getPureTextAnswer() {
        return pureTextAnswer;
    }

    public void setPureTextAnswer(String pureTextAnswer) {
        this.pureTextAnswer = pureTextAnswer;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAskType() {
        return askType;
    }

    public void setAskType(String askType) {
        this.askType = askType;
    }

    public String getMatchMode() {
        return matchMode;
    }

    public void setMatchMode(String matchMode) {
        this.matchMode = matchMode;
    }

    public String getValidTerm() {
        return validTerm;
    }

    public void setValidTerm(String validTerm) {
        this.validTerm = validTerm;
    }

    /*public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }*/

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "CorrentAnswerInfo{" +
                "id=" + id +
                ", ask='" + ask + '\'' +
                ", answer='" + answer + '\'' +
                ", pureTextAnswer='" + pureTextAnswer + '\'' +
                ", classifyName='" + classifyName + '\'' +
                ", state='" + state + '\'' +
                ", askType='" + askType + '\'' +
                ", matchMode='" + matchMode + '\'' +
                ", validTerm='" + validTerm + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
