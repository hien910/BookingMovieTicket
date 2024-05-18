package com.example.bookingmovieticket.controller;

import com.example.bookingmovieticket.entity.Blog;
import com.example.bookingmovieticket.model.response.VerifyAccountResponse;
import com.example.bookingmovieticket.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class WebController {
    private final MovieService movieService;
    private final BlogService blogService;
    private final GenreService genreService;
    private final ActorService actorService;
    private final DirectorService directorService;
    private final CountryService countryService;
    private final ReviewService reviewService;
    private final AuthService authService;

    @GetMapping("/login")
    public String getLogin() {
        return "web/auth/login";
    }

    @GetMapping("/register")
    public String getRegister() {
        return "web/auth/register";
    }
    @GetMapping("/xac-thuc-tai-khoan")
    public String getVerifyAccount(@RequestParam String token, Model model) {
        VerifyAccountResponse data = authService.verifyAccount(token);
        model.addAttribute("data", data);
        return "web/auth/verify-account";
    }
    @GetMapping("/quen-mat-khau")
    public String getForgotPassword(Model model, @RequestParam(required = false) String token) {
        String data = authService.verifyPasswordRetrieval(token);
        model.addAttribute("token" ,data) ;
        return "web/auth/forgot-password";
    }
    @GetMapping("/profile")
    public String getProfile(){
        return "/web/profile";
    }
    @GetMapping("/seat")
    public String getSeat(){
        return "/seat/seat";
    }
    @GetMapping( "/")
    public String getHome(Model model){

        model.addAttribute("movieUpcoming",movieService.getMoviesUpcomingHome(true));
        model.addAttribute("movieShowing",movieService.getMoviesShowingHome(true));
        model.addAttribute("blogs",blogService.findBlogByStatusOrderByPublishedAtDesc(true,1,4 ));
        return "web/home";
    }
    @GetMapping("/phim-chieu")
    public String getMovie(Model model){
        model.addAttribute("genreList",genreService.getAll());
        model.addAttribute("countryList",countryService.getAll());

        model.addAttribute("movieUpcoming",movieService.getMoviesUpcomingHome(true));
        model.addAttribute("movieShowing",movieService.getMoviesShowingHome(true));
        model.addAttribute("blogs",blogService.findBlogByStatusOrderByPublishedAtDesc(true,1,4 ));
        System.out.println(movieService.getMoviesShowingHome(true));
        return "web/movie";
    }
    //     Chi tiết phim
    @GetMapping("/phim/{id}/{slug}")
    public String getChiTietPhim(@PathVariable Integer id, @PathVariable String slug, Model model) {

        model.addAttribute("movie", movieService.getMovie(id, slug, true));
        model.addAttribute("blogs",blogService.findBlogByStatusOrderByPublishedAtDesc(true,1,4 ));
        model.addAttribute("reviews", reviewService.getReviewsByMovie(id));
        model.addAttribute("authentication", SecurityContextHolder.getContext().getAuthentication());
        return "web/detail-movie";
    }
    @GetMapping("/home1")
    public String getHome1(){
        return "web/home1";
    }
    @GetMapping("/rap-chieu")
    public String getCinema(){
        return "/web/cinema";
    }
    @GetMapping("/lich-chieu")
    public String getShowtime(){
        return "/web/showtime";
    }

    @GetMapping("/list-blog")
    public String getListBlog(Model model ,
                              @RequestParam(required = false, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = "8") Integer size) {
        Page<Blog> pageData = blogService.getBlogByStatus(true, page, size);

        model.addAttribute("pageData", pageData);
        model.addAttribute("currentPage", page);
        return "web/list-blog";
    }
    @GetMapping("/blog/{id}/{slug}")
    public String getDetailBlog(@PathVariable Integer id, @PathVariable String slug, Model model) {
        Blog blog = blogService.findBlogByIdAndSlug(id, slug, true);
        model.addAttribute("blog", blog);
        model.addAttribute("blogs", blogService.findBlogByStatusOrderByPublishedAtDesc(true,1,4 ));
        return "web/detail-blog";
    }


}
