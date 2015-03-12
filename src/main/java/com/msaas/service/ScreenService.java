package com.msaas.service;

import static com.msaas.model.CameraState.SCHEDULED;
import static com.msaas.model.CameraState.WAITING;
import static com.msaas.security.OauthConfiguration.ROLE_OBS;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.systemDefault;
import static java.util.Date.from;
import static org.springframework.data.domain.Sort.Direction.ASC;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.msaas.infrastructure.CameraRepository;
import com.msaas.infrastructure.ScreenRepository;
import com.msaas.model.Camera;
import com.msaas.model.Observer;
import com.msaas.model.Screen;

@Service
@Transactional
public class ScreenService {

    @Resource
    private CameraRepository cameraRepository;
    @Resource
    private ScreenRepository screenRepository;

    @PreAuthorize("hasRole('" + ROLE_OBS + "')")
    public Screen scrollNextScreen(Observer observer) {
        // mark last screen as viewed
        Screen lastScreen = markLastScreenViewed(observer.getLastScreen());

        // compute next one
        Screen newScreen = computeNextScreen(observer);

        // reset cameras of last screen to WAITING
        resetAllCameras(lastScreen);

        return newScreen;
    }

    @PreAuthorize("hasRole('" + ROLE_OBS + "')")
    public Screen computeNextScreen(Observer observer) {

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

    @PreAuthorize("hasRole('" + ROLE_OBS + "')")
    public Screen markLastScreenViewed(Screen lastScreen) {
        // mark last screen as viewed
        lastScreen.viewedAt = new Date();
        return screenRepository.save(lastScreen);
    }

    @PreAuthorize("hasRole('" + ROLE_OBS + "')")
    public void resetAllCameras(Screen lastScreen) {
        // reset all the cameras
        lastScreen.cameras.forEach(camera -> {
            camera.state = WAITING;
            cameraRepository.save(camera);
        });
    }
}
