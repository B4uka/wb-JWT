package pl.wb.chess.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Result {
    @JsonProperty
    WHITE_WON("1:0"),
    @JsonProperty
    BLACK_WON("0:1"),
    @JsonProperty
    DRAW("0.5:0.5");

    private String result;

    Result(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

}
