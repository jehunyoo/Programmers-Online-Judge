class Solution {

    private static int N;
    private static int M;

    public int solution(int[][] board, int[][] skills) {
        N = board.length;
        M = board[0].length;

        int[][] delta = new int[N][M];

        for (int[] skill : skills) {
            int type = skill[0];
            int row1 = skill[1];
            int col1 = skill[2];
            int row2 = skill[3];
            int col2 = skill[4];
            int degree = skill[5];

            if (type == 1) {
                markDelta(delta, row1, col1, row2, col2, -degree);
            } else if (type == 2) {
                markDelta(delta, row1, col1, row2, col2, degree);
            }
        }

        int count = applyDelta(board, delta);

        return count;
    }

    private void markDelta(int[][] delta, int row1, int col1, int row2, int col2, int degree) {
        delta[row1][col1] += degree;

        if (row2 + 1 < N) {
            delta[row2 + 1][col1] += (-degree);
        }

        if (col2 + 1 < M) {
            delta[row1][col2 + 1] += (-degree);
        }

        if (row2 + 1 < N && col2 + 1 < M) {
            delta[row2 + 1][col2 + 1] += degree;
        }
    }

    private int applyDelta(int[][] board, int[][] delta) {
        for (int i = 0; i < N; i++) {
            for (int j = 1; j < M; j++) {
                delta[i][j] += delta[i][j - 1];
            }
        }

        for (int j = 0; j < M; j++) {
            for (int i = 1; i < N; i++) {
                delta[i][j] += delta[i - 1][j];
            }
        }

        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                board[i][j] += delta[i][j];

                if (board[i][j] > 0) {
                    count++;
                }
            }
        }

        return count;
    }
}
