package com.msaas.service;

import static com.msaas.model.CameraState.WAITING;
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
import com.msaas.model.Screen;
import com.msaas.model.User;

@Service
@Transactional
public class ScreenService {

    @Resource
    private CameraRepository cameraRepository;
    @Resource
    private ScreenRepository screenRepository;

    @PreAuthorize("hasRole('OBSERVER')")
    public Screen scrollNextScreen(User user) {
        // mark last screen as viewed
        Screen lastScreen = user.getLastScreen().markViewed();
        screenRepository.save(lastScreen);

        // compute next one
        Screen newScreen = computeNextScreen(user);

        // reset cameras of last screen to WAITING
        lastScreen.getCameras().forEach(c -> cameraRepository.save(c.makeWaiting()));

        return newScreen;
    }

    @PreAuthorize("hasRole('OBSERVER')")
    public Screen computeNextScreen(User user) {

        // find next top 4 newCameras in state WAITING (sorted by nextViewingAt)
        List<Camera> newCameras = cameraRepository.findTop4ByState(WAITING, new Sort(ASC, "nextViewingAt"));

        // insert new screen 60 seconds from now
        Date scheduledAt = from(now().plusSeconds(60).atZone(systemDefault()).toInstant());
        Screen newScreen = new Screen(scheduledAt, null, user, newCameras);
        screenRepository.save(newScreen);

        // reset the state of these newCameras to scheduled
        newCameras.forEach(c -> cameraRepository.save(c.scheduleMe(newScreen.getScheduledAt())));

        return newScreen;
    }
}
