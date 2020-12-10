package pl.wb.security.service;

import pl.wb.security.jpa.Game;
import pl.wb.security.jpa.User;
import pl.wb.security.utils.SecurityUtil;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
@Stateless
public class GameService {

    @Inject
    private EntityManager entityManager;

    @Inject
    private QueryService queryService;

    @Inject
    private SecurityUtil securityUtil;

    public User saveUser(User user) {

        if (user.getId() == null)  {
            Map<String, String> credentialMap = securityUtil.hashPassword(user.getPassword());

            user.setPassword(credentialMap.get(SecurityUtil.HASHED_PASSWORD_KEY));
            user.setSalt(credentialMap.get(SecurityUtil.SALT_KEY));

            entityManager.persist(user);
            credentialMap.clear();
        }
        return user;
    }

    public Game createGame(Game game, String email) {
        User userByEmail = queryService.findUserByEmail(email);

        if (userByEmail != null) {
            game.setGameOwner(userByEmail);
            entityManager.persist(game);
        }
        return game;
    }

    public Game updateGame(Game game) {
        entityManager.merge(game);
        return game;
    }

    public Game findGameById(Long id) {
        return queryService.findGameById(id);
    }


    public List<Game> getGames(String email) {
        return queryService.getAllGames(email);
    }

}
