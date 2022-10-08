package org.poltou.service.data;

import org.poltou.business.UserResult;
import org.poltou.business.opening.theory.TheoryNode;
import org.poltou.business.opening.user.UserNode;
import org.springframework.stereotype.Service;

import chess.Situation;

@Service
public class UserNodeDataService extends TheoryNodeDataService {

    @Override
    protected TheoryNode provideNode() {
        return new UserNode();
    }

    private void setupNode(UserNode node) {
        node.setEncounters(0);
        node.setWins(0);
        node.setLosses(0);
    }

    public UserNode createNode(Situation situation) {
        UserNode node = (UserNode) super.createNode(situation);
        setupNode(node);
        return node;
    }

    public UserNode createNode(Situation situation, UserNode parent, String san, String uci) {
        UserNode node = (UserNode) super.createNode(situation, parent, san, uci);
        setupNode(node);
        return node;
    }

    public UserNode getOrCreateChildNode(UserNode parent, String uci, UserResult result) {
        UserNode foundNode = (UserNode) super.getOrCreateChildNode(parent, uci);
        foundNode.setEncounters(foundNode.getEncounters() + 1);
        switch (result) {
            case WIN:
                foundNode.setWins(foundNode.getWins() + 1);
                break;
            case LOSS:
                foundNode.setLosses(foundNode.getLosses() + 1);
                break;
            default:
        }
        return foundNode;
    }
}
