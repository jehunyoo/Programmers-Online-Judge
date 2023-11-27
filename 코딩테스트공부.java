import java.util.*;

public class Solution {

    public int solution(int alp, int cop, int[][] problems) {
        Set<Problem> problemSet = new HashSet<>();
        problemSet.add(Problem.getAlgoUnitProblem());
        problemSet.add(Problem.getCodeUnitProblem());

        int algoTargetPower = 0;
        int codeTargetPower = 0;

        for (int[] problem : problems) {
            algoTargetPower = Math.max(algoTargetPower, problem[0]);
            codeTargetPower = Math.max(codeTargetPower, problem[1]);

            if (!Problem.isExpensive(problem[2], problem[3], problem[4])) {
                problemSet.add(new Problem(problem[0], problem[1], problem[2], problem[3], problem[4]));
            }
        }

        int time = Integer.MAX_VALUE;
        State initState = new State(alp, cop, 0);

        PriorityQueue<State> pq = new PriorityQueue<>();
        pq.add(initState);

        Map<State, Integer> dp = new HashMap<>();
        dp.put(initState, 0);

        while (!pq.isEmpty()) {
            State state = pq.remove();

            if (state.isCodingExpert(algoTargetPower, codeTargetPower)) {
                time = state.time;
                break;
            }

            for (Problem problem : problemSet) {
                State nextState = state.getNextState(problem.algoReward, problem.codeReward, problem.cost);
                boolean isNextStateValid = (dp.containsKey(nextState) && nextState.time < dp.get(nextState)) || !dp.containsKey(nextState);

                if (problem.isSolvable(state.algoPower, state.codePower) && isNextStateValid) {
                    pq.add(nextState);
                    dp.put(nextState, nextState.time);
                }
            }
        }

        return time;
    }

    private static class State implements Comparable<State> {

        int algoPower;
        int codePower;
        int time;

        public State(int algoPower, int codePower, int time) {
            this.algoPower = algoPower;
            this.codePower = codePower;
            this.time = time;
        }

        public State getNextState(int algoPower, int codePower, int cost) {
            return new State(this.algoPower + algoPower, this.codePower + codePower, this.time + cost);
        }

        public boolean isCodingExpert(int algoTargetPower, int codeTargetPower) {
            return algoPower >= algoTargetPower && codePower >= codeTargetPower;
        }

        @Override
        public int compareTo(State other) {
            return this.time - other.time;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            State state = (State) o;
            return algoPower == state.algoPower && codePower == state.codePower;
        }

        @Override
        public int hashCode() {
            return Objects.hash(algoPower, codePower);
        }
    }

    private static class Problem {

        int algoPower;
        int codePower;
        int algoReward;
        int codeReward;
        int cost;

        public Problem(int algoPower, int codePower, int algoReward, int codeReward, int cost) {
            this.algoPower = algoPower;
            this.codePower = codePower;
            this.algoReward = algoReward;
            this.codeReward = codeReward;
            this.cost = cost;
        }

        public static Problem getAlgoUnitProblem() {
            return new Problem(0, 0, 1, 0, 1);
        }

        public static Problem getCodeUnitProblem() {
            return new Problem(0, 0, 0, 1, 1);
        }

        public static boolean isExpensive(int algoReward, int codeReward, int cost) {
            return algoReward + codeReward <= cost;
        }

        public boolean isSolvable(int algoPower, int codePower) {
            return this.algoPower <= algoPower && this.codePower <= codePower;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Problem problem = (Problem) o;
            return algoPower == problem.algoPower && codePower == problem.codePower && algoReward == problem.algoReward
                    && codeReward == problem.codeReward && cost == problem.cost;
        }

        @Override
        public int hashCode() {
            return Objects.hash(algoPower, codePower, algoReward, codeReward, cost);
        }
    }
}
