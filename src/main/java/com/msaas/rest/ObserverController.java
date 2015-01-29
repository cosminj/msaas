package com.msaas.rest;

import com.msaas.infrastructure.CameraRepository;
import com.msaas.infrastructure.ObserverRepository;
import com.msaas.infrastructure.ScreenRepository;
import com.msaas.model.Camera;
import com.msaas.model.Observer;
import com.msaas.model.Screen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import static com.msaas.model.CameraState.SCHEDULED;
import static com.msaas.model.CameraState.WAITING;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.systemDefault;
import static java.util.Date.from;
import static javax.transaction.Transactional.TxType.REQUIRED;
import static org.springframework.data.domain.Sort.Direction.ASC;

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

    @RequestMapping("/server/nextScreen")
    @Transactional(value = REQUIRED)
    public Screen nextScreen(@AuthenticationPrincipal User user) {
        Observer observer = observerRepository.findByName(user.getUsername());

        // mark last screen as viewed
        Screen lastScreen = markLastScreenViewed(observer.getLastScreen());

        // compute next one
        Screen newScreen = computeNextScreen(observer);

        // reset cameras of last screen to WAITING
        resetAllCameras(lastScreen);

        return newScreen;
    }

    protected Screen markLastScreenViewed(Screen lastScreen) {
        // mark last screen as viewed
        lastScreen.viewedAt = new Date();
        return screenRepository.save(lastScreen);
    }

    protected void resetAllCameras(Screen lastScreen) {
        // reset all the cameras
        lastScreen.cameras.forEach(camera -> {
            camera.state = WAITING;
            cameraRepository.save(camera);
        });
    }

    protected Screen computeNextScreen(Observer observer) {
        // find next top 4 newCameras in state WAITING (sorted by nextViewingAt)
        List<Camera> newCameras = cameraRepository.findTop4ByState(WAITING, new Sort(ASC, "nextViewingAt"));

        // insert new screen
        Screen newScreen = new Screen(observer);
        newScreen.scheduledAt = from(now().plusSeconds(60).atZone(systemDefault()).toInstant());
        newScreen.cameras.addAll(newCameras);
        screenRepository.save(newScreen);

        // reset the state of these newCameras to scheduled
        newCameras.forEach(camera -> {
            camera.state = SCHEDULED;
            camera.nextViewingAt = newScreen.scheduledAt;
            cameraRepository.save(camera);
        });

        return newScreen;
    }

}
