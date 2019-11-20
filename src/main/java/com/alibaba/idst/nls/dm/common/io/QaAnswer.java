package com.alibaba.idst.nls.dm.common.io;

/**
 * @author jianghaitao
 * @date 2019/10/15
 */
public class QaAnswer {
    private String question;
    private String answer;
    private Double score;
    private String domain;
    private Object optional;

    public QaAnswer() {
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Double getScore() {
        return this.score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Object getOptional() {
        return this.optional;
    }

    public void setOptional(Object optional) {
        this.optional = optional;
    }
}
