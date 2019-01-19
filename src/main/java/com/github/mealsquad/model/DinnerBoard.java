package com.github.mealsquad.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DinnerBoard {

    private @Getter @Setter Map<User, RelevantInfo> dinnerBoard;
    private @Setter List<String> header;

    public String[] getHeader() {
        String[] arr = new String[header.size()];
        header.toArray(arr);
        return arr;
    }

    // ORDER MATTERS:  current.add(update)
    // TODO Should fix this code to work in either direction
    public DinnerBoard add(DinnerBoard other) {
        // Perhaps have checks for user in an update who is not already in the board?  When a player is added to board, new dinner board should be
        Set<User> intersection = new HashSet<>(this.dinnerBoard.keySet());
        intersection.retainAll(other.dinnerBoard.keySet());
        Map<User, RelevantInfo> newBoard = new HashMap<>();
        this.dinnerBoard.keySet().stream()
                .forEach(user -> newBoard.put(user, intersection.contains(user) ? this.dinnerBoard.get(user).add(other.dinnerBoard.get(user)) : this.dinnerBoard.get(user)));
        return new DinnerBoard(newBoard, this.header);
    }

    public void addPlayer(String username) {
        dinnerBoard.put(new User(username), RelevantInfo.emptyRelevantInfo(username));
    }

    // TODO toString should return properly formatted for table like display

    @Override
    public String toString() {
        List<String> display = dinnerBoard.keySet().stream()
                .map(dinnerBoard::get)
                .map(RelevantInfo::toString)
                .collect(Collectors.toList());
        return "DinnerBoard{" +
                "dinnerBoard=" + display +
                ", header=" + header +
                '}';
    }

    public String[][] to2dArray() {
        String[][] arr = new String[dinnerBoard.size()][];
        int i = 0;
        for (User user : dinnerBoard.keySet()) {
            arr[i++] = dinnerBoard.get(user).toArray();
        }
        return arr;
    }
}
