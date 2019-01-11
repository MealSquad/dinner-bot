package com.github.mealsquad.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class DinnerBoard {

    private Map<User, RelevantInfo> dinnerBoard;
    private List<String> header;

    public DinnerBoard() {
        this(new HashMap<>(), new ArrayList<>());
    }

    public DinnerBoard(Map<User, RelevantInfo> dinnerBoard, List<String> header) {
        this.dinnerBoard = dinnerBoard;
        this.header = header;
    }

    public void setDinnerBoard(Map<User, RelevantInfo> dinnerBoard) {
        this.dinnerBoard = dinnerBoard;
    }

    public void setHeader(List<String> header) {
        this.header = header;
    }

    public Map<User, RelevantInfo> getDinnerBoard() {
        return dinnerBoard;
    }

    public String[] getHeader() {
        String[] arr = new String[6];
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DinnerBoard that = (DinnerBoard) o;
        return Objects.equals(header, that.header) &&
                Objects.equals(dinnerBoard, that.dinnerBoard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, dinnerBoard);
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
