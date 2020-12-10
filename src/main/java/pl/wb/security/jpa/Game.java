package pl.wb.security.jpa;

import pl.wb.chess.model.PieceColor;
import pl.wb.chess.model.Result;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import java.time.LocalDate;

@Entity
@Table(name = "game")
@NamedQuery(name = Game.FIND_GAME_BY_RESULT, query = "select g from Game g where g.result = :result")
@NamedQuery(name = Game.FIND_USER_GAMES_BY_RESULT, query = "select g from Game g where g.result = :result and g.gameOwner.email= :email")
@NamedQuery(name = Game.FIND_USER_GAMES_BY_PIECE_COLOR, query = "select g from Game g where g.piecesColor = :piececolor and g.gameOwner.email= :email")
@NamedQuery(name = Game.FIND_ALL_GAMES_BY_USER, query = "select g from Game g where g.gameOwner.email = :email")
@NamedQuery(name = Game.FIND_GAME_BY_ID, query = "select g from Game g where g.id = :id and g.gameOwner.email= :email")
public class Game extends AbstractEntity {

    public static final String FIND_GAME_BY_RESULT = "Game.findByResult";
    public static final String FIND_USER_GAMES_BY_RESULT = "Game.findUserGameByResult";
    public static final String FIND_USER_GAMES_BY_PIECE_COLOR = "Game.findUserGameByPieceColor";
    public static final String FIND_ALL_GAMES_BY_USER = "Game.findUserGames";
    public static final String FIND_GAME_BY_ID = "Game.findById";

    @ManyToOne
    private User gameOwner;

    //    @NotEmpty(message = "Opponent must be set")
//    @NotNull(message = "Opponent must be set")
    @Column(name = "opponent")
    private String opponent;

    @NotEmpty(message = "Opponent must be set")
    @NotNull(message = "Opponent must be set")
    @Column(name = "pieces_color")
    private Enum<PieceColor> piecesColor;

    //    @NotNull(message = "Result must be set")
    @Column(name = "result")
    private Enum<Result> result;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "date_completed")
    @JsonbDateFormat(value = "yyyy-MM-dd")
    private LocalDate dateCompleted;

    @FutureOrPresent(message = "Game must be played in the present or future!")
    @JsonbDateFormat(value = "yyyy-MM-dd")
    @Column(name = "date_future_game")
    private LocalDate setGameInTheFuture;
    @NotNull(message = "Description must be set")
    @DefaultValue("")
    @Column(name = "description")
    private String description;

    @Column(name = "is_completed", columnDefinition = "boolean default false")
    private boolean isCompleted;

    @PrePersist
    private void init() {
        setDateCreated(LocalDate.now());
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public Enum<PieceColor> getPiecesColor() {
        return piecesColor;
    }

    public void setPiecesColor(Enum<PieceColor> piecesColor) {
        this.piecesColor = piecesColor;
    }

    public Enum<Result> getResult() {
        return result;
    }

    public void setResult(Enum<Result> result) {
        this.result = result;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(LocalDate dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public User getGameOwner() {
        return gameOwner;
    }

    public void setGameOwner(User gameOwner) {
        this.gameOwner = gameOwner;
    }

    public LocalDate getSetGameInTheFuture() {
        return setGameInTheFuture;
    }

    public void setSetGameInTheFuture(LocalDate setGameInTheFuture) {
        this.setGameInTheFuture = setGameInTheFuture;
    }
}
