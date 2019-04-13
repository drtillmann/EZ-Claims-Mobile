package com.google.sample.cloudvision;

public class GoogleVisionItemInfo {

    private String score;
    private String description;

    public GoogleVisionItemInfo(String prob, String def){
        setProbability(prob);
        setDefinition(def);
    }

    public void setProbability(String score){
        this.score = score;
    }

    public void setDefinition(String description){
        this.description = description;
    }

    public String getProbability(){
        return this.score;
    }

    public String getDefinition(){
        return this.description;
    }
}
