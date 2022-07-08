package toyproject.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import toyproject.board.domain.User;
import toyproject.board.service.UserService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/new")
    public String createForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "users/createUserForm";
    }

    @PostMapping("/users/new")
    public String create(@Valid UserForm userForm, BindingResult result) {
        User user = new User();

        user.setUserName(userForm.getName());
        user.setUserPassword(userForm.getPassword());
        user.setUserEmail(userForm.getEmail());

        userService.join(user);
        return "redirect:/";
    }
}
