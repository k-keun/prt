package gbbGame;

import java.util.*;

public class RankingService {

    private final UserRepository repo;

    public RankingService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> getRankingByWinRate(boolean desc) {
        List<User> list = repo.findAll();

        Comparator<User> cmp = Comparator
                .comparingDouble((User u) -> u.getRecord().winRate())
                .thenComparingInt(u -> u.getRecord().total())
                .thenComparing(User::getId);

        // desc면 뒤집기: 승률 높은 순
        if (desc) cmp = cmp.reversed();

        list.sort(cmp);
        return list;
    }
}
