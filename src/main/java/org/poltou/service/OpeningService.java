package org.poltou.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.poltou.exceptions.BadIdException;
import org.poltou.opening.ChessNode;
import org.poltou.opening.Opening;
import org.poltou.repository.NodeRepo;
import org.poltou.repository.OpeningRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chess.Situation;
import chess.format.Forsyth;

@Service
public class OpeningService {
    @Autowired
    private OpeningRepo openingRepo;
    @Autowired
    private NodeRepo chessNodeRepo;

    public List<Opening> getAllOpenings() {
        return StreamSupport.stream(openingRepo.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Optional<Opening> findOpeningById(Long id) {
        return openingRepo.findById(id);
    }

    public Long addOpening(String name, String startingFen) {
        Situation situation = Forsyth.$less$less(startingFen).getOrElse(null);
        if (situation != null) {
            ChessNode node = new ChessNode();
            node.setSituation(situation);
            chessNodeRepo.save(node);

            Opening opening = new Opening();
            opening.setName(name);
            opening.setStartingNode(node);
            return openingRepo.save(opening).getId();
        } else {
            throw new IllegalArgumentException("Fen provided is not parsable");
        }
    }

    public Long addNodeToOpening(Long nodeId, String uci) {
        ChessNode parent = chessNodeRepo.findById(nodeId)
                .orElseThrow(() -> new BadIdException("ChessNode " + nodeId + " not found."));
        ChessNode childNode = chessNodeRepo.buildChildNode(parent, uci);
        parent.getChildren().add(childNode);
        childNode.setParent(parent);
        return chessNodeRepo.save(childNode).getId();
    }

    public void deleteOpening(Long id) {
        if (openingRepo.findById(id).isPresent()) {
            openingRepo.deleteById(id);
        } else {
            throw new BadIdException();
        }
    }

    public void deleteChessNode(Long id) {
        Optional<ChessNode> optNode = chessNodeRepo.findById(id);
        if (optNode.isPresent()) {
            if (optNode.get().getParent() != null) {

                optNode.get().getParent().getChildren().remove(optNode.get());
                chessNodeRepo.deleteById(id);
            } else {
                throw new IllegalArgumentException("Can't delete root node of opening.");
            }
        } else {
            throw new BadIdException();
        }
    }

    public Optional<ChessNode> findNodeById(Long id) {
        return chessNodeRepo.findById(id);
    }

}