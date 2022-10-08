package org.poltou.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.poltou.business.dto.opening.user.UserNodeDTO;
import org.poltou.business.dto.opening.user.UserOpeningDTO;
import org.poltou.controller.datainterface.UserDataInterface;
import org.poltou.exceptions.BadIdException;
import org.poltou.service.UserOpeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UserOpeningController {
    @Autowired
    private UserOpeningService userOpeningService;

    @PostMapping("/importchesscom")
    public void importchesscom(@RequestBody UserDataInterface user) {
        userOpeningService.importChessCom(user.getUsername());
    }

    @GetMapping("/opening")
    public List<UserOpeningDTO> getAllOpening() {
        return userOpeningService.getAllOpenings().stream().map(UserOpeningDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/opening/{id}")
    public UserOpeningDTO getOpeningById(@PathVariable Long id) {
        return new UserOpeningDTO(
                userOpeningService.findOpeningById(id).orElseThrow(() -> new BadIdException(id.toString())));
    }

    @GetMapping("/chessnode/{id}")
    public UserNodeDTO getNodeById(@PathVariable Long id) {
        return new UserNodeDTO(
                userOpeningService.findNodeById(id).orElseThrow(() -> new BadIdException(id.toString())));
    }

    @DeleteMapping("/opening/{id}")
    public void deleteOpening(@PathVariable Long id) {
        userOpeningService.deleteOpening(id);
    }
}
