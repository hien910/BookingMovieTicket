package com.example.bookingmovieticket;

import com.example.bookingmovieticket.entity.*;
import com.example.bookingmovieticket.model.enums.SeatType;
import com.example.bookingmovieticket.model.enums.StatusSeat;
import com.example.bookingmovieticket.model.enums.UserRole;
import com.example.bookingmovieticket.repository.*;
import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
class BookingMovieTicketApplicationTests {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private SystemCinemaRepository systemCinemaRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private SeatRepository seatRepository;


    @Test
    void contextLoads() {
    }

    @Test
    void save_seats() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();
        Random random = new Random();
        List<Room> roomList = roomRepository.findAll();

        for (Room room : roomList) {
            if (room.getId()==5){
                break;
            }
            for (int i = 0; i < room.getNumCol() * room.getNumRow(); i++) {
                String codeSeat = codeSeat(colIndex(i), rowIndex(i));
                Seat seat = Seat.builder()
                        .rowIndex(rowIndex(i))
                        .colIndex(colIndex(i))
                        .code(codeSeat)
                        .room(room)
                        .seatType(SeatType.valueOf(determineSeatType(codeSeat)))
                        .price(50000.00)
                        .status(StatusSeat.ACTIVE)
                        .build();
                seatRepository.save(seat);
            }
        }
    }
    @Test
    void update_seat() {
        seatRepository.deleteAll();
    }


    public String determineSeatType(String code) {
        char row = code.charAt(0);
        int col = Integer.parseInt(code.substring(1));

        if (row >= 'D' && row <= 'I') {
            if (row >= 'D' && row <= 'H') {
                return "VIP";
            } else if ((row == 'I' && col <= 6) || row == 'J') {
                return "COUPLE";
            }
        }

        return "STANDARD";
    }

    int rowIndex(int a) {
        int s = (a / 10 + 1);
        if (s == 11) {
            return 10;
        } else {
            return s;
        }
    }

    int colIndex(int a) {
        int s = (a % 10);
        if (s == 0) {
            return 10;
        } else {
            return s;
        }
    }

    String codeSeat(int a, int row) {
        switch (row) {
            case 1:
                return "A" + a;
            case 2:
                return "B" + a;
            case 3:
                return "C" + a;
            case 4:
                return "D" + a;
            case 5:
                return "E" + a;
            case 6:
                return "F" + a;
            case 7:
                return "G" + a;
            case 8:
                return "H" + a;
            case 9:
                return "I" + a;
            case 10:
                return "J" + a;
        }
        return null;
    }

    @Test
    void save_rooms() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();
        Random random = new Random();
        List<Cinema> cinemaList = cinemaRepository.findAll();
        for (Cinema cinema : cinemaList) {
            for (int i = 0; i < 5; i++) {
                Room room = Room.builder()
                        .name(faker.color().name())
                        .numRow(10)
                        .numCol(10)
                        .status(true)
                        .cinema(cinema)
                        .createdAt(new Date())
                        .updatedAt(new Date())
                        .build();
                roomRepository.save(room);
            }
        }
    }

    @Test
    void save_system() {

        systemCinemaRepository.save(new SystemCinema("Beta Cinemas", "https://homepage.momocdn.net/cinema/momo-upload-api-210813104719-637644484394328824.png", "Rạp chiếu phim Beta Cinemas"));
        systemCinemaRepository.save(new SystemCinema("CGV", "https://homepage.momocdn.net/placebrand/s/momo-upload-api-190709165424-636982880641515855.jpg", "Hệ thống rạp chiếu phim lớn nhất Việt Nam"));
        systemCinemaRepository.save(new SystemCinema("Lotte Cinema", "https://homepage.momocdn.net/blogscontents/momo-upload-api-210604170617-637584231772974269.png", "Hệ thống rạp chiếu phim từ Hàn Quốc"));
        systemCinemaRepository.save(new SystemCinema("BHD Star", "https://homepage.momocdn.net/blogscontents/momo-upload-api-210604170453-637584230934981809.png", "Hệ thống rạp chiếu phim hiện đại"));
        systemCinemaRepository.save(new SystemCinema("Galaxy Cinema", "https://homepage.momocdn.net/cinema/momo-upload-api-211123095138-637732578984425272.png", "Mang Hollywood đến gần bạn"));
        systemCinemaRepository.save(new SystemCinema("Cinestar", "https://homepage.momocdn.net/blogscontents/momo-upload-api-210604170530-637584231309495829.png", "Hệ thống rạp chiếu phim giá rẻ, hiện đại bậc nhất"));
        systemCinemaRepository.save(new SystemCinema("Cinemax", "https://homepage.momocdn.net/cinema/momo-upload-api-221108100132-638034984925107129.png", "Rạp phim đạt chuẩn quốc gia"));
        systemCinemaRepository.save(new SystemCinema("Mega GS", "https://homepage.momocdn.net/blogscontents/momo-upload-api-210604170511-637584231119272266.png", "Rạp chiếu phim tiêu chuẩn quốc tế tại Việt Nam"));
    }

    @Test
    void save_cinemas() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();
        Random random = new Random();
        List<SystemCinema> systemCinemas = systemCinemaRepository.findAll();
        for (SystemCinema s : systemCinemas) {
            Cinema cinema = Cinema.builder()
                    .name(s.getName() + "1")
                    .location("Hà nội")
                    .description(s.getDescription())
                    .systemCinema(s)
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .build();
            cinemaRepository.save(cinema);
        }
    }

    @Test
    void save_reviews() {
        Faker faker = new Faker();
        Random random = new Random();

        List<User> userList = userRepository.findAll();
        List<Movie> movieList = movieRepository.findAllByStatus(true);

        for (Movie movie : movieList) {
            // Mỗi phim sẽ có 1 số lượng review ngẫu nhiên từ 10 -> 20 review
            int numberOfReview = random.nextInt(5) + 10;
            for (int i = 0; i < numberOfReview; i++) {
                Review review = Review.builder()
                        .comment(faker.lorem().paragraph())
                        .rating(faker.number().numberBetween(1, 10))
                        .user(userList.get(random.nextInt(userList.size())))
                        .movie(movie)
                        .createdAt(new Date())
                        .updatedAt(new Date())
                        .build();
                reviewRepository.save(review);
            }
        }
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
                    .createdAt(new Date())
                    .updatedAt(new Date())
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

        Date showDateStart = new Calendar.Builder().setDate(2024, 4, 12).build().getTime();
        Date showDateEnd = new Calendar.Builder().setDate(2024, 5, 12).build().getTime();

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
    @Test
    void save_users() {
        Faker faker = new Faker();
        Random rd = new Random();
        for (int i = 0; i < 10; i++) {
            String name = faker.name().fullName();
            int randomNumber = rd.nextInt(9000000) + 1000000;
            String phone = String.valueOf(randomNumber);
            User user = User.builder()
                    .name(name)
                    .phone("098" + phone)
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

    @Test
    void update_password() {
        List<User> userList = userRepository.findAll();
        userList.forEach(user -> {
            user.setPassword(passwordEncoder.encode("123"));
            userRepository.save(user);
        });
    }

}
