import java.util.*;

class Solution {
    
    private List<int[]>[] graph;
    private boolean[] visited;
    private PLACE[] place;
    private int minIntensity;
    private int minSummit;
    private int[] dp; // save min intensity of each node
    
    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        graph = new List[n + 1];
        
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        
        int minWeight = Integer.MAX_VALUE;
        int maxWeight = 0;
        
        for (int[] path : paths) {
            int i = path[0];
            int j = path[1];
            int w = path[2];
            
            graph[i].add(new int[]{j, w});
            graph[j].add(new int[]{i, w});
            
            minWeight = Math.min(minWeight, w);
            maxWeight = Math.max(maxWeight, w);
        }
        
        if (minWeight == maxWeight) {
            Arrays.sort(summits);
            return new int[]{summits[0], minWeight};
        }
        
        place = new PLACE[n + 1];
        Arrays.fill(place, PLACE.SHELTER);
        
        for (int gate : gates) {
            place[gate] = PLACE.GATE;
        }
        for (int summit : summits) {
            place[summit] = PLACE.SUMMIT;
        }
        
        minIntensity = Integer.MAX_VALUE;
        minSummit = n + 1;
        visited = new boolean[n + 1];
        dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        
        for (int gate : gates) {
            visited[gate] = true;
            dfs(gate, 0);
        }
        
        return new int[]{minSummit, minIntensity};
    }
    
    private void dfs(int node, int intensity) {
        if (intensity < dp[node]) {
            dp[node] = intensity;
        } else {
            return;
        }
        
        if (intensity > minIntensity) {
            return;
        }
        
        if (place[node] == PLACE.SUMMIT) {
            if (intensity < minIntensity) {
                minIntensity = intensity;
                minSummit = node;
            } else if (intensity == minIntensity) {
                minSummit = Math.min(minSummit, node);
            }
            return;
        }
        
        for (int[] path : graph[node]) {
            int next = path[0];
            int weight = path[1];
            
            if (!visited[next] && place[next] != PLACE.GATE) {
                visited[next] = true;
                dfs(next, Math.max(intensity, weight));
                visited[next] = false;
            }
        }
    }
    
    enum PLACE {
        GATE, SUMMIT, SHELTER
    }
}