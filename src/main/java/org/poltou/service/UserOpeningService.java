package org.poltou.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.poltou.business.UserResult;
import org.poltou.business.opening.user.UserNode;
import org.poltou.business.opening.user.UserOpening;
import org.poltou.business.repository.UserNodeRepo;
import org.poltou.business.repository.UserOpeningRepo;
import org.poltou.service.data.UserNodeDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chess.Board;
import chess.Color;
import chess.Situation;
import chess.format.pgn.InitialPosition;
import chess.format.pgn.ParsedPgn;
import chess.format.pgn.Reader;
import chess.format.pgn.Sans;
import chess.format.pgn.Tags;
import chess.variant.Variant;
import scala.Option;
import scala.Tuple3;
import scala.util.Either;

@Service
public class UserOpeningService {
    Logger logger = LoggerFactory.getLogger(UserOpening.class);
    @Autowired
    UserOpeningRepo userOpeningRepo;
    @Autowired
    UserNodeRepo userNodeRepo;
    @Autowired
    ChesscomImportService chesscomService;
    @Autowired
    UserNodeDataService userNodeDataService;

    public void importChessCom(String username) {
        UserOpening whiteOpen = getOrCreateOpening(username, "white");
        UserOpening blackOpen = getOrCreateOpening(username, "black");
        List<ParsedPgn> pgnFromPlayer = chesscomService.getPgnFromPlayer(2021, 1, username);
        for (ParsedPgn pgn : pgnFromPlayer) {
            addUserGameToOpening(whiteOpen, blackOpen, pgn);
        }
        userOpeningRepo.save(whiteOpen);
        userOpeningRepo.save(blackOpen);
    }

    private UserOpening getOrCreateOpening(String username, String color) {
        UserOpening opening = userOpeningRepo.findByUsernameAndColor(username, color);
        if (opening == null) {
            UserNode node = userNodeDataService
                    .createNode(new Situation(Board.init(Variant.orDefault("standard")), Color.White$.MODULE$));
            opening = UserOpening.of(username, color, node);
        }
        return opening;
    }

    /**
     * Will add the game to the corresponding opening
     * depending of which color the user played in the pgn <br>
     * Openings should share the same username
     */
    private void addUserGameToOpening(UserOpening whiteOpen, UserOpening blackOpen, ParsedPgn pgn) {
        String username = whiteOpen.getUsername();
        Tuple3<InitialPosition, Tags, Sans> tuple;
        tuple = ParsedPgn.unapply(pgn).get();
        Tags tags = tuple._2();
        // check if the game has an end
        Option<Option<Color>> resultColor = tags.resultColor();
        if (resultColor.nonEmpty()) {
            UserOpening opening = null;
            Color played = null;

            // fetching which side is playing the user and assign variables accordingly
            if (tags.apply("white").exists(player -> username.equalsIgnoreCase(username))) {
                // user is playing as white
                played = Color.White$.MODULE$;
                opening = whiteOpen;
            } else if (tags.apply("black").exists(player -> username.equalsIgnoreCase(username))) {
                // user is playing as black
                played = Color.Black$.MODULE$;
                opening = blackOpen;
            } else {
                logger.warn("Pgn loaded does not contain username " + username);
                return;
            }
            UserResult result = UserResult.parseTag(played, resultColor.get());

            // Adding the UserNode of the game to the correct opening
            UserNode nodeIter = opening.getStartingNode();
            Reader.fullWithSans(pgn, id -> id).valid().toOption().get().chronoMoves()
                    .foldLeft(nodeIter,
                            (UserNode iter, Either<chess.Move, chess.Drop> dropOrMove) -> {
                                String uci = dropOrMove.fold(mv -> mv.toUci().uci(), drop -> drop.toUci().uci());
                                return userNodeDataService.getOrCreateChildNode(iter, uci, result);
                            });
        }
    }

    public List<UserOpening> getAllOpenings() {
        return StreamSupport.stream(userOpeningRepo.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Optional<UserOpening> findOpeningById(Long id) {
        return userOpeningRepo.findById(id);
    }

    public Optional<UserNode> findNodeById(Long id) {
        return userNodeRepo.findById(id);
    }

    public void deleteOpening(Long id) {
    }

    public UserOpening findByUsernameAndColor(String username, String color) {
        return userOpeningRepo.findByUsernameAndColor(username, color);
    }
}
