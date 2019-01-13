package com.github.mealsquad.generator;

import com.github.mealsquad.model.DinnerBoard;
import com.github.mealsquad.model.RelevantInfo;
import com.github.mealsquad.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DinnerBoardGenerator implements Function<List<String>, DinnerBoard> {

    @Override
    public DinnerBoard apply(List<String> strings) {
        RelevantInfoGenerator rig = new RelevantInfoGenerator();

        List<String> header = strings.subList(0, 6);

        // User data list into 6 object long arrays to be converted into RelevantInfoObjects.
        List<String> userData = strings.subList(6, strings.size());
        Map<User, RelevantInfo> dinnerInfo = new HashMap<>();

        // Check edge cases
        for (int i = 0; i + 6 <= userData.size(); i = i + 6) {
            List<String> iso = userData.subList(i, i + 6);
            dinnerInfo.put(new User(userData.get(i)), rig.apply(iso));
        }
        return new DinnerBoard(dinnerInfo, header);
    }


}
