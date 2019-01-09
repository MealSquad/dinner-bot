package com.github.mealsquad.board;

import com.github.mealsquad.model.RelevantInfo;
import com.github.mealsquad.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    // Think about different cases related to different size dinner-boards
    public DinnerBoard add(DinnerBoard other) {
        if (dinnerBoard.size() != other.dinnerBoard.size()) {
            throw new UnsupportedOperationException("Unequal sized dinner boards cannot be added");
        }
        Map<User, RelevantInfo> newBoard = new HashMap<>();
        this.dinnerBoard.keySet().stream()
                .forEach(user -> newBoard.put(user, this.dinnerBoard.get(user).add(other.dinnerBoard.get(user))));
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
}
