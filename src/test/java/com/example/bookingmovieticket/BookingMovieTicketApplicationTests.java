package com.example.bookingmovieticket;

import com.example.bookingmovieticket.entity.*;
import com.example.bookingmovieticket.model.enums.UserRole;
import com.example.bookingmovieticket.repository.*;
import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
class BookingMovieTicketApplicationTests {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private DirectorRepository directorRepository;


    @Test
    void contextLoads() {
    }
    @Test
    void save_genres() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();
        for (int i = 0; i < 10; i++) {
            String name = faker.funnyName().name();
            Genre genre = Genre.builder()
                    .name(name)
                    .slug(slugify.slugify(name))
                    .build();
            genreRepository.save(genre);
        }
    }

    @Test
    void save_actors() {
        Faker faker = new Faker();
        for (int i = 0; i < 50; i++) {
            String name = faker.name().fullName();
            Actor actor = Actor.builder()
                    .name(name)
                    .description(faker.lorem().paragraph())
                    .avatar(generateLinkImage(name))
                    .birthday(faker.date().birthday())
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .build();
            actorRepository.save(actor);
        }
    }

    @Test
    void save_directors() {
        Faker faker = new Faker();
        for (int i = 0; i < 20; i++) {
            String name = faker.name().fullName();
            Director director = Director.builder()
                    .name(name)
                    .description(faker.lorem().paragraph())
                    .avatar(generateLinkImage(name))
                    .birthday(faker.date().birthday())
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .build();
            directorRepository.save(director);
        }
    }


    @Test
    void save_countries() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Country country = Country.builder()
                    .name(faker.country().name())
                    .build();
            countryRepository.save(country);
        }
    }
    @Test
    void save_movies() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();
        Random random = new Random();
        List<Country> countryList = countryRepository.findAll();

        Date start = new Calendar.Builder().setDate(2024, 3, 3).build().getTime();
        Date end = new Date();

        Date showDateStart = new Calendar.Builder().setDate(2024, 3, 12).build().getTime();
        Date showDateEnd = new Calendar.Builder().setDate(2024, 4, 12).build().getTime();

        List<Genre> genreList = genreRepository.findAll();
        List<Actor> actorList = actorRepository.findAll();
        List<Director> directorList = directorRepository.findAll();

        for (int i = 0; i < 20; i++) {

            // Lấy ngẫu nhiên danh sách 1 -> 3 thể loại
            List<Genre> rdGenreList = new ArrayList<>();
            for (int j = 0; j < random.nextInt(3) + 1; j++) {
                Genre genre = genreList.get(random.nextInt(genreList.size()));
                if (!rdGenreList.contains(genre)) {
                    rdGenreList.add(genre);
                }
            }

            // Lấy ngẫu nhiên danh sách 5 -> 7 diễn viên
            List<Actor> rdActorList = new ArrayList<>();
            for (int j = 0; j < random.nextInt(3) + 5; j++) {
                Actor actor = actorList.get(random.nextInt(actorList.size()));
                if (!rdActorList.contains(actor)) {
                    rdActorList.add(actor);
                }
            }

            // Lấy ngẫu nhiên 1 -> 3 đạo diễn
            List<Director> rdDirectorList = new ArrayList<>();

            for (int j = 0; j < random.nextInt(3) + 1; j++) {
                Director director = directorList.get(random.nextInt(directorList.size()));
                if (!rdDirectorList.contains(director)) {
                    rdDirectorList.add(director);
                }
            }


            String title = faker.book().title();
            boolean status = faker.bool().bool();
            Date createdAt = randomDateBetweenTwoDates(start, end);
            Date publishedAt = null;
            if (status) {
                publishedAt = createdAt;
            }

            Movie movie = Movie.builder()
                    .title(title)
                    .slug(slugify.slugify(title))
                    .description(faker.lorem().paragraph())
                    .poster(generateLinkImage(title))
                    .duration(random.nextInt(60) + 99)
                    .releaseYear(faker.number().numberBetween(2022, 2024))
                    .showDateStart(showDateStart)
                    .rating(faker.number().randomDouble(1, 6, 10))
                    .status(status)
                    .country(countryList.get(random.nextInt(countryList.size())))
                    .showDateEnd(showDateEnd)
                    .createdAt(createdAt)
                    .updatedAt(createdAt)
                    .publishedAt(publishedAt)
                    .genres(rdGenreList)
                    .actors(rdActorList)
                    .directors(rdDirectorList)
                    .build();

            movieRepository.save(movie);
        }
    }

    @Test
    void save_users() {
        Faker faker = new Faker();
        Random rd = new Random();
        for (int i = 0; i < 10; i++) {
            String name = faker.name().fullName();
            int randomNumber = rd.nextInt(9000000) + 1000000;
            String phone  = String.valueOf(randomNumber);
            User user = User.builder()
                    .name(name)
                    .phone("098"+ phone)
                    .password("123")
                    .email(faker.internet().emailAddress())
                    .avatar(generateLinkImage(name))
                    .role(i == 0 || i == 1 ? UserRole.ADMIN : UserRole.USER)
                    .enabled(true)
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .build();
            userRepository.save(user);
        }
    }

    @Test
    void save_blogs() {
        Slugify slugify = Slugify.builder().build();
        Faker faker = new Faker();
        Random rd = new Random();

        List<User> userList = userRepository.findByRole(UserRole.ADMIN);

        Date start = new Calendar.Builder().setDate(2023, 8, 1).build().getTime();
        Date end = new Date();

        for (int i = 0; i < 50; i++) {
            String title = faker.book().title();
            boolean status = faker.bool().bool();
            Date createdAt = randomDateBetweenTwoDates(start, end);
            Date publishedAt = null;
            if (status) {
                publishedAt = createdAt;
            }

            Blog blog = Blog.builder()
                    .title(title)
                    .slug(slugify.slugify(title))
                    .description(faker.lorem().paragraph())
                    .content(faker.lorem().paragraph(100))
                    .status(rd.nextInt(2) == 0)
                    .user(userList.get(rd.nextInt(userList.size())))
                    .thumbnail(generateLinkImage(title))
                    .createdAt(createdAt)
                    .updatedAt(createdAt)
                    .publishedAt(publishedAt)
                    .build();
            blogRepository.save(blog);
        }
    }
    // write method to random date between 2 date
    private Date randomDateBetweenTwoDates(Date startInclusive, Date endExclusive) {
        long startMillis = startInclusive.getTime();
        long endMillis = endExclusive.getTime();
        long randomMillisSinceEpoch = ThreadLocalRandom
                .current()
                .nextLong(startMillis, endMillis);
        return new Date(randomMillisSinceEpoch);
    }

    // generate link author avatar follow struct : https://placehold.co/200x200?text=[...]
    public static String generateLinkImage(String str) {
        return "https://placehold.co/200x200?text=" + str.substring(0, 1).toUpperCase();
    }
    @Test
    void getMovie() {
        Date date = new Date();

        System.out.println(movieRepository.findByStatusAndShowDateStartAfter(true, date));
    }

}
