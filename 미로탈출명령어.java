class Solution {
    
    private final String[] dirs = {"d", "l", "r", "u"};
    private final int[] dx = {1, 0, 0, -1};
    private final int[] dy = {0, -1, 1, 0};
    
    public String solution(int n, int m, int x, int y, int r, int c, int k) {
        if (x == r && y == c && k == 0) {
            return "";
        }
        
        int distance = Math.abs(x - r) + Math.abs(y - c);
        if (distance > k || (k - distance) % 2 == 1) {
            return "impossible";
        }
        
        for (int d = 0; d < dirs.length; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];
            
            if (0 < nx && nx <= n && 0 < ny && ny <= m) {
                String s = solution(n, m, nx, ny, r, c, k - 1);
                if (!s.equals("impossible")) {
                    return dirs[d] + s;
                }
            }
        }
        
        return "impossible";
    }
}