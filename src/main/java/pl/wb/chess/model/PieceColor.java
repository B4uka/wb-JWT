package pl.wb.chess.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PieceColor {
    @JsonProperty
    WHITE("White"),
    @JsonProperty
    Black("Black");

    private String piecesColor;

    PieceColor(String piecesColor) {
        this.piecesColor = piecesColor;
    }

    public String getPiecesColor() {
        return piecesColor;
    }
}
