package com.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;

import java.util.Map;

@Introspected
public class Joke {

    private String type;
    private Integer factId;
    private String text;

    public Joke() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFactId() {
        return factId;
    }

    public String getText() {
        return text;
    }

    @JsonProperty("value")
    public void setText(Map<String, Object> fact) {
        this.factId = (Integer) fact.get("id");
        this.text = (String) fact.get("joke");
    }

    @Override
    public String toString() {
        return "Fact{" +
                "type='" + type + '\'' +
                ", factId=" + factId +
                ", text='" + text + '\'' +
                '}';
    }

}
