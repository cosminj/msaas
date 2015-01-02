package com.msaas.observer;

import com.msaas.camera.Screen;
import com.msaas.camera.ScreenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import static java.time.LocalDateTime.now;
import static java.time.ZoneId.systemDefault;
import static javax.transaction.Transactional.TxType.REQUIRED;

/**
 * @author cj
 * @since 02/01/15.
 */
@RestController
public class ObserverController {

    private final ObserverRepository observerRepository;
    private final ScreenRepository screenRepository;

    @Autowired
    public ObserverController(ObserverRepository observerRepository, ScreenRepository screenRepository) {
        this.observerRepository = observerRepository;
        this.screenRepository = screenRepository;
    }

    @RequestMapping("/nextScreen")
    @Transactional(value = REQUIRED)
    public Screen nextScreen(@AuthenticationPrincipal User user) {

        Observer observer = observerRepository.findByName(user.getUsername());
        List<Screen> screens = observer.screens;

        Screen lastScreen = new Screen(observer);
        if (!screens.isEmpty()) {
            lastScreen = screens.get(screens.size() - 1);
        }

        // mark last screen as viewed
        lastScreen.viewedAt = new Date();
        lastScreen.scheduledAt = new Date();
        screenRepository.save(lastScreen);

        // compute next one
        return computeNextScreen(observer);
    }


    public Screen computeNextScreen(Observer observer) {
        Screen newScreen = new Screen(observer);
        newScreen.scheduledAt = Date.from(now().plusSeconds(10).atZone(systemDefault()).toInstant());
        screenRepository.save(newScreen);
        return newScreen;
    }

}
