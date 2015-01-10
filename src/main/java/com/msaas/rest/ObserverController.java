package com.msaas.rest;

import com.msaas.infrastructure.ObserverRepository;
import com.msaas.model.Camera;
import com.msaas.infrastructure.CameraRepository;
import com.msaas.model.CameraState;
import com.msaas.model.Observer;
import com.msaas.model.Screen;
import com.msaas.infrastructure.ScreenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import static com.msaas.model.CameraState.WAITING;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.systemDefault;
import static javax.transaction.Transactional.TxType.REQUIRED;
import static org.springframework.data.domain.Sort.Direction.ASC;

/**
 * @author cj
 * @since 02/01/15.
 */
@RestController
public class ObserverController {

    private final ObserverRepository observerRepository;
    private final ScreenRepository screenRepository;
    private final CameraRepository cameraRepository;
    
    @Autowired
    public ObserverController(ObserverRepository observerRepository, ScreenRepository screenRepository, CameraRepository cameraRepository) {
        this.observerRepository = observerRepository;
        this.screenRepository = screenRepository;
        this.cameraRepository = cameraRepository;
    }

    @RequestMapping("/nextScreen/{viewingTime}")
    @Transactional(value = REQUIRED)
    public Screen nextScreen(@AuthenticationPrincipal User user, @PathVariable("viewingTime") Integer viewingTime) {

        Observer observer = observerRepository.findByName(user.getUsername());

        resetLastScreen(observer);

        // compute next one
        return computeNextScreen(observer);
    }

    public void resetLastScreen(Observer observer) {
        List<Screen> screens = observer.screens;
        Screen lastScreen = screens.isEmpty() ? new Screen(observer) : screens.get(screens.size() - 1);
        // mark last screen as viewed
        lastScreen.viewedAt = new Date();
        if(lastScreen.scheduledAt == null) {
            lastScreen.scheduledAt = new Date();
        }
        screenRepository.save(lastScreen);
    }


    public Screen computeNextScreen(Observer observer) {
        Screen newScreen = new Screen(observer);
        newScreen.scheduledAt = Date.from(now().plusSeconds(3).atZone(systemDefault()).toInstant());
        screenRepository.save(newScreen);
        
        // take cameras
        List<Camera> cameras = cameraRepository.findTop4ByState(WAITING, new Sort(ASC, "nextViewingAt"));

        // TODO assign cameras to new screen

        return newScreen;
    }

}
