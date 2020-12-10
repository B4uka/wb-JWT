package pl.wb.security.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import pl.wb.security.jpa.Game;
import pl.wb.security.jpa.User;

import java.util.List;
import java.util.Optional;

@Stateless
public class QueryService {

    @Inject
    private EntityManager entityManager;

    public User findUserById(Long id) {
        Optional<User> user = Optional.ofNullable(entityManager
                .createNamedQuery(User.FIND_USER_BY_ID, User.class)
                .setParameter("id", id)
                .getResultList()
                .get(0));
        return user.get();
    }


    public List<Game> findGameById(Long id, String email) {
        List<Game> gamesList = entityManager
                .createNamedQuery(Game.FIND_GAME_BY_ID, Game.class)
                .setParameter("id", id)
                .setParameter("email", email)
                .getResultList();
        return gamesList;
    }

    public User findUserByEmail(String email) {
        List<User> userList = entityManager.createNamedQuery(User.FIND_USER_BY_EMAIL, User.class).setParameter("email", email)
                .getResultList();

        if (!userList.isEmpty()) {
            return userList.get(0);
        }
        return null;
    }

//    public List<Long> countUsersByEmail(String email) {
//        return entityManager.createNativeQuery("SELECT count (id) FROM Game where exists(select id from User where email =?)")
//                .setParameter(1, email).getResultList();
//    }

    public Game findGameById(Long id) {
        List<Game> resultList = entityManager.createNamedQuery(Game.FIND_GAME_BY_ID, Game.class)
                .setParameter("id", id)
                .getResultList();

        if (!resultList.isEmpty()) {
            return resultList.get(0);
        }

        return null;

    }

    public List<Game> getAllGames(String email) {
        return entityManager.createNamedQuery(Game.FIND_ALL_GAMES_BY_USER, Game.class)
                .setParameter("email", email).getResultList();
    }

    public List<Game> getAllTodosByTask(String result, String email) {
        return entityManager.createNamedQuery(Game.FIND_GAME_BY_RESULT, Game.class)
                .setParameter("email", email)
                .setParameter("result", "%" + result + "%").getResultList();
    }

}