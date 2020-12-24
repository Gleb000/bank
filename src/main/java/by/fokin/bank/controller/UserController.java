package by.fokin.bank.controller;

import by.fokin.bank.domain.Role;
import by.fokin.bank.domain.User;
import by.fokin.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());

        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());

        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ) {
        userService.saveUser(user, username, form);

        return "redirect:/user";
    }

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email
    ) {
        userService.updateProfile(user, password, email);

        return "redirect:/user/profile";
    }

    @GetMapping("billing")
    public String getBilling(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("userMoney", user.getMoney());

        return "billing";
    }

    @GetMapping("cashReplenishment")
    public String getCashRepl(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());

        return "cashReplenishment";
    }

    @PostMapping("cashReplenishment")
    public String updateMoney(
            @AuthenticationPrincipal User user,
            @RequestParam long cash
    ) {
        userService.moneyUpdate(user, cash);

        return "redirect:/login";
    }

    @GetMapping("cashTransfer")
    public String getCashTrans(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());

        return "cashTransfer";
    }

    @PostMapping("cashTransfer")
    public String moneyTransfer(
            @AuthenticationPrincipal User user,
            @RequestParam long cash,
            @RequestParam String user1
    ) {
        userService.transferMoney(user, cash, user1);

        return "redirect:/login";
    }

    @GetMapping("mobilePay")
    public String getMobilePay(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());

        return "mobilePay";
    }

    @PostMapping("mobilePay")
    public String payPhoneNumber(
            @AuthenticationPrincipal User user,
            @RequestParam long cash
    ) {
        userService.phoneMoneyTransfer(user, cash);

        return "redirect:/login";
    }

    @GetMapping("servicesPay")
    public String getServiceInfo(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("water", userService.getWater());
        model.addAttribute("light", userService.getLight());
        model.addAttribute("electricity", userService.getElectricity());
        model.addAttribute("servicesSum", userService.getServicesSum());

        return "servicesPay";
    }

    @PostMapping("servicesPay")
    public String payService(
            @AuthenticationPrincipal User user
    ) {
        userService.serviceTx(user, userService.getServicesSum());

        return "redirect:/login";
    }
}