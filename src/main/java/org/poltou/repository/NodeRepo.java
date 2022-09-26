package org.poltou.repository;

import org.poltou.opening.ChessNode;
import org.springframework.data.repository.CrudRepository;

import cats.data.Validated;
import chess.Move;
import chess.format.Uci;
import chess.format.pgn.Dumper;

public interface NodeRepo extends CrudRepository<ChessNode, Long> {

    public default ChessNode buildChildNode(ChessNode parent, String uci) {
        Validated<String, Move> elmt = parent.getSituation().move(Uci.Move$.MODULE$.apply(uci).get());
        if (elmt.isValid()) {
            ChessNode node = new ChessNode();
            node.setSituation(elmt.toOption().get().situationAfter());
            node.setSan(Dumper.apply(elmt.toOption().get()));
            node.setUci(elmt.toOption().get().toUci().uci());
            return node;
        } else {
            throw new IllegalArgumentException(elmt.toString());
        }
    }
}
