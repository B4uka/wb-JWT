package pl.wb.security.resource;

import pl.wb.security.jpa.Game;
import pl.wb.security.service.GameService;
import pl.wb.security.service.QueryService;
import pl.wb.security.utils.UserInfo;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("game")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authz
public class GameResource {
    
    @Inject
    private GameService gameService;
    @Inject
    private QueryService queryService;
    @Inject
    private UserInfo userInfo;

    @Path("newGame")
    @POST
    public Response createGame(Game game){
        gameService.createGame(game, userInfo.getEmail());
       
       return Response.ok(game).build();
    }

    @Path("update")
    @PUT
    public Response updateGame(Game game){
        gameService.updateGame(game);

         return Response.ok(game).build();
    }
    
    @Path("{id}")
    @GET
    public Game getGame(@PathParam("id") Long id){
        return queryService.findGameById(id);
    }
    
    
    @Path("list/{email}")
    @GET
    public List<Game> getGames(@PathParam("email") String email){
        return queryService.getAllGames(email);
    }

    @Path("gameStatus/{id}")
    @POST
    public Response markAsComplete(@QueryParam("id") Long id){
        Game game = gameService.findGameById(id);
        game.setCompleted(true);
        gameService.updateGame(game);

        return Response.ok(game).build();

    }

}
