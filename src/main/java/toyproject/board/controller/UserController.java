package toyproject.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import toyproject.board.domain.User;
import toyproject.board.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/users/new")
    public String createForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "users/createUserForm";
    }

    @PostMapping("/users/new")
    public String create(@Valid UserForm userForm, BindingResult result) {

        if (userForm.getName().length() == 0 || userForm.getPassword().length() == 0) {
            return "users/createUserForm";
        }

        User user = new User();

        user.setUserName(userForm.getName());
        user.setUserPassword(userForm.getPassword());
        user.setUserEmail(userForm.getEmail());

        userService.join(user);
        return "redirect:/";
    }

    @GetMapping("/users/login")
    public String loginForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "users/login";
    }

    @PostMapping("/users/login")
    public String login(@Valid UserForm userForm, BindingResult result, HttpSession session, HttpServletRequest request, Model model) {

        if (userForm.getName().length() == 0 || userForm.getPassword().length() == 0) {
            return "users/login";
        }

        Map<String, Object> rs = userService.checkLoginAvailable(userForm);

        String resultCode = (String) rs.get("resultCode");
        String msg = (String) rs.get("msg");

        if (resultCode.startsWith("S")) {
            session.setAttribute("loginedUserName", userForm.getName());
            return "redirect:/";
        } else {
            model.addAttribute("msg", msg);
            return "users/login";
        }

    }


    @GetMapping("/users/logout")
    public String logout(HttpSession session) {

        session.invalidate();
        return "redirect:/";
    }
}
