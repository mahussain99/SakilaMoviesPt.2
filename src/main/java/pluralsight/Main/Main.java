package pluralsight.Main;

import org.apache.commons.dbcp2.BasicDataSource;
import pluralsight.db.DataManager;
import pluralsight.model.Actor;
import pluralsight.model.Film;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

        if (args.length != 2) {
            System.out.println("Application need to correct username and password");
        }
        String username = args[0];
        String password = args[1];

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        DataManager dataManager = new DataManager(dataSource);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter actor lastName");
        String lastName = scanner.nextLine();


        List<Actor> actorList = dataManager.getAllActorName(lastName);
        if (!actorList.isEmpty()) {
            System.out.println("ALl actors name is matching:\n");
            for (Actor actor : actorList) {
                System.out.println("Actor Id: " + actor.getActorId());
                System.out.println("Actor First Name: " + actor.getFirstName());
                System.out.println("Actor First Name: " + actor.getLastName());
            }

        } else {
            System.out.println("Actor name not matching: ");
        }
        System.out.println("Enter the actor ID to watch their films");
        int actorID = scanner.nextInt();
        scanner.nextLine();

        List<Film> filmList = dataManager.filmByActorId(actorID);

        if (!filmList.isEmpty()) {
            System.out.println(" films matching for actor id:\n");

            for (Film film : filmList) {
                System.out.println("Film ID" + film.getFilmId());
                System.out.println("Title: " + film.getTitle());
                System.out.println("Description: " + film.getDescription());
                System.out.println("Release Year: " + film.getReleaseYear());
                System.out.println("Length: " + film.getLength());
                System.out.println();

            }
        } else {
            System.out.println(" No film matching with actor id");
        }

    }
}
