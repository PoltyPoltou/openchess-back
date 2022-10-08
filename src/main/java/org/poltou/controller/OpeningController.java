package org.poltou.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.poltou.business.dto.opening.theory.TheoryNodeDTO;
import org.poltou.business.dto.opening.theory.TheoryOpeningDTO;
import org.poltou.controller.datainterface.NodeDataInterface;
import org.poltou.controller.datainterface.OpeningDataInterface;
import org.poltou.exceptions.BadIdException;
import org.poltou.service.OpeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpeningController {
    @Autowired
    private OpeningService openingService;

    @GetMapping("/opening")
    public List<TheoryOpeningDTO> getAllOpening() {
        return openingService.getAllOpenings().stream().map(TheoryOpeningDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/opening/{id}")
    public TheoryOpeningDTO getOpeningById(@PathVariable Long id) {
        return new TheoryOpeningDTO(
                openingService.findOpeningById(id).orElseThrow(() -> new BadIdException(id.toString())));
    }

    @GetMapping("/chessnode/{id}")
    public TheoryNodeDTO getNodeById(@PathVariable Long id) {
        return new TheoryNodeDTO(
                openingService.findNodeById(id).orElseThrow(() -> new BadIdException(id.toString())));
    }

    @PostMapping("/opening")
    public Long addOpening(@RequestBody OpeningDataInterface opening) {
        return openingService.addOpening(opening.getName(), opening.getFen());
    }

    @PostMapping("/chessnode/{id}")
    public Long addNode(@PathVariable Long id, @RequestBody NodeDataInterface node) {
        return openingService.addNodeToOpening(id, node.getUci());
    }

    @DeleteMapping("/opening/{id}")
    public void deleteOpening(@PathVariable Long id) {
        openingService.deleteOpening(id);
    }

    @DeleteMapping("/chessnode/{id}")
    public void deleteChessNode(@PathVariable Long id) {
        openingService.deleteChessNode(id);
    }
}
