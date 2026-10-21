/**
 * Time Complexity:  O((V+E)⋅log(V+E)) V is the number of houses, E number of pipes
 * Space Complexity: O(V + E)
 *
 * •
 * We connect each house to a virtual node 0 using the well cost as an edge.
 * Then we collect all those edges along with the real pipes into one list and sort by cost.
 * Using Union-Find, we keep adding the cheapest edges that connect new components until all are connected.
 */
class Solution {
    public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
        int[] uf = new int[n + 1];
        int result = 0;

        List<int[]> edges = new ArrayList<>();
        for (int[] pipe : pipes) {
            edges.add(pipe);
        }

        for (int i = 0; i < wells.length; i++) {
            edges.add(new int[] { 0, i + 1, wells[i] });
        }

        Collections.sort(edges, (a, b) -> a[2] - b[2]);
        for (int i = 0; i < n + 1; i++) {
            uf[i] = i;
        }

        for (int[] edge : edges) {
            int x = edge[0];
            int y = edge[1];
            int cost = edge[2];

            int px = findParent(x, uf);
            int py = findParent(y, uf);

            if (px != py) {
                result += cost;
                uf[py] = px;
            }
        }

        return result;
    }

    int findParent(int x, int[] uf) {

        if (x != uf[x]) {
            uf[x] = findParent(uf[x], uf);
        }

        return uf[x];
    }
}