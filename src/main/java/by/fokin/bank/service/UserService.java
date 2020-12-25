package by.fokin.bank.service;

import by.fokin.bank.domain.Role;
import by.fokin.bank.domain.User;
import by.fokin.bank.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    long water;
    long gas;
    long electricity;
    long servicesSum;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public boolean addUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());  

        if (userFromDb != null) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        sendMessage(user);

        return true;
    }

    private void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to <<DD-Bank>>. Please, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);

        userRepo.save(user);

        return true;
    }

    public List<User> findAll()  {
        return userRepo.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user);
    }

    public void updateProfile(User user, String password, String email) {
        String userEmail = user.getEmail();

        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

        if (isEmailChanged) {
            user.setEmail(email);

            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if (!StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }

        userRepo.save(user);

        if (isEmailChanged) {
            sendMessage(user);
        }
    }

    public void moneyUpdate(User user, long cash) {
        long money2 = user.getMoney();
        long money3 = money2 + cash;

        user.setMoney(money3);

        userRepo.save(user);
    }

    public void transferMoney(User user, long cash, String user1) {
        long difference = user.getMoney() - cash;
        User recipient = userRepo.findByUsername(user1);
        long sum = recipient.getMoney() + cash;


        if (difference > 0 && !recipient.getUsername().equals(user.getUsername())) {
            user.setMoney(difference);
            recipient.setMoney(sum);

            userRepo.save(recipient);
            userRepo.save(user);
        }
    }

    public void phoneMoneyTransfer(User user, long cash) {
        if(cash < user.getMoney()) {
            user.setMoney(user.getMoney() - cash);

            userRepo.save(user);
        }
    }

    public long getWater() {
        water = (long)(30 + Math.random()*70);

        return water;
    }

    public long getGas() {
        gas = (long)(60 + Math.random()*90);

        return gas;
    }

    public long getElectricity() {
        electricity = (long)(40 + Math.random()*80);

        return electricity;
    }

    public long getServicesSum() {
        servicesSum = water + gas + electricity;

        return servicesSum;
    }

    public void serviceTx(User user, long servicesSum) {
        if(user.getMoney() > servicesSum) {
            user.setMoney(user.getMoney() - servicesSum);

            userRepo.save(user);
        }
    }
}