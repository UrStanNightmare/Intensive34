package ru.aston.oshchepkov_aa.task9;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import ru.aston.oshchepkov_aa.task9.alien.AlienPersonalSecretData;
import ru.aston.oshchepkov_aa.task9.entity.*;
import ru.aston.oshchepkov_aa.task9.enums.UserType;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("Add alien to Db");

        doTransaction(s -> s.save(AlienUser.builder()
                .name("Blue")
                .surname("Dabadee")
                .lastName("Dabadi")
                .userType(UserType.ADULT)
                .bodyColor("Blue")
                .build()
        ));

        log.info("Add human to Db");
        doTransaction(s -> s.save(HumanUser.builder()
                .name("Vasya")
                .surname("Pupkin")
                .lastName("Sergeevich")
                .userType(UserType.ADULT)
                .footSize(41)
                .build()
        ));

        log.info("Get fresh added Users");
        final User[] u = new User[2];
        doTransaction(s -> {
            var users = s.createQuery("FROM User", User.class).list();
            for (int i = 0; i < users.size(); i++) {
                u[i] = users.get(i);
            }
        });

        log.info("Users in db: ");
        for (User user : u) {
            log.info("{}", user);
        }

        log.info("Ordering sadness for alien");
        var o = new Order(null, "Sadness", u[0]);

        log.info("Saving order to db");
        // User updates 'automatically' because of bidirectional binding.
        doTransaction(s -> s.save(o));

        log.info("Retrieving alien");
        /*
            Making query to fix LazyInitializationException because session closes after doTransaction method
            and log needs data to print orders. They are lazyLoading by default.
        */
        doTransaction(s -> u[0] = s.createQuery("from User u left join fetch u.orders where u.id = 1", User.class)
                .list().get(0));

        log.info("{}", u[0]);
        log.info("{}", u[0].getOrders());

        log.info("Creating alien secret data");
        var secret = AlienPersonalSecretData.builder()
                .header(Map.of("Object", "A little guy"))
                .payload(Map.of("Lives", "Blue world", "House", "Blue little window"))
                .secret(Map.of("Problem", "Ain't got nobody to listen to"))
                .build();

        var sEntity = new SecretDataRecord(null, secret, ((AlienUser) u[0]));
        doTransaction(s -> s.save(sEntity));

        doTransaction(s -> u[0] = s.createQuery("from AlienUser u left join fetch u.secretDataRecord where u.id = 1",
                        User.class)
                .list().get(0));

        log.info("Alien data: ");
        log.info("{}", ((AlienUser) u[0]).getSecretDataRecord().getData());

        log.info("Creating places to visit");
        doTransaction(s -> s.save(new VisitedPlace(null, "City", "Blue", null)));

        log.info("Attempt to add link to place to user");
        var p = new VisitedPlace[1];
        doTransaction(s -> p[0] = s.createQuery("FROM VisitedPlace p where p.id = 1", VisitedPlace.class)
                .list().get(0));

        log.info("{}", p[0]);
        u[0].setVisitedPlaces(Set.of(p[0]));
        doTransaction(s -> s.update(u[0]));

        log.info("Updating user and getting visited places");
        doTransaction(s -> u[0] = s.createQuery("from AlienUser u left join fetch u.visitedPlaces where u.id = 1",
                        User.class)
                .list().get(0));

        log.info("{}", u[0].getVisitedPlaces());

        HibernateUtils.shutdown();
    }

    private static void doTransaction(Consumer<Session> action) {
        try (var session = HibernateUtils.getSessionFactory().openSession()) {
            var trans = session.beginTransaction();
            action.accept(session);
            trans.commit();
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
        }

    }
}
