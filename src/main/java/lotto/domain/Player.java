package lotto.domain;


import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Player {
    public final List<Lotto> lottos;

    public Player(int purchaseCount) {
        this.lottos = receiveLotto(purchaseCount);
    }

    private List<Lotto> receiveLotto(int purchaseCount) {
        List<Lotto> lottos = new ArrayList<>();
        for (int i = 0; i < purchaseCount; i++) {
            lottos.add(new Lotto(makeNumbers()));
        }
        return lottos;
    }

    private List<Integer> makeNumbers() {
        return Randoms.pickUniqueNumbersInRange(1, 45, 6)
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public void checkMatchWinningNumber(List<Integer> winningNumbers) {
        List<Integer> matchList;
        for (var lotto : lottos) {
            matchList = lotto.getNumbers().stream()
                    .filter(number -> winningNumbers.stream()
                            .anyMatch(Predicate.isEqual(number)))
                    .collect(Collectors.toList());

            lotto.setRank(matchList.size());
            matchList.clear();
        }
    }

    public void checkMatchBonusNumber(int bonusNumber) {
        for(var lotto : lottos) {
            lotto.matchBonusNumber(bonusNumber);
        }
    }

    public String getEarningRate(double amount) {
        double rate = (Rank.getTotalEarning() / amount ) * 100;
        return String.format("%.1f",rate);
    }
}
