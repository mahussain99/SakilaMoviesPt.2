package pluralsight.db;

import pluralsight.model.Actor;
import pluralsight.model.Film;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private DataSource dataSource;

    public DataManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Actor> getAllActorName(String lastName) {
        List<Actor> actors = new ArrayList<>();
        String actorQuery = """
                SELECT actor_id, first_name, last_name
                FROM actor
                Where last_name = ?
                """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(actorQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            preparedStatement.setString(1, lastName);

            while (resultSet.next()) {

                int actorId = resultSet.getInt(1);
                String actorFirstName = resultSet.getString(2);
                String actorLastName = resultSet.getString(3);

                actors.add(new Actor(actorId, actorFirstName, actorLastName));

            }

        } catch (SQLException ex) {
            System.out.println("Show me run time error");
            ex.printStackTrace();
        }
        return actors;
    }

    public List<Film> filmByActorId(int actorId) throws SQLException {
        List<Film> filmList = new ArrayList<>();

        String filmQuery =  """ 
                             SELECT film.film_id, film.title, film.description, film.release_year, film.length
                             FROM film
                             JOIN film_actor ON film.film_id = film_actor.film_id
                             WHERE film_actor.actor_id = ?
                       """;

       try ( Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement( filmQuery)){;
             statement.setInt(1, actorId);
            try (ResultSet resultSet = statement.executeQuery()){;
                while (resultSet.next()){

                    int filmId = resultSet.getInt(1);
                    String title = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    int releaseYear = resultSet.getInt(4);
                    int length = resultSet.getInt(5);

                    filmList.add(new Film(filmId, title, description, releaseYear, length));


                }
            }

        }
        return filmList;
    }
}
