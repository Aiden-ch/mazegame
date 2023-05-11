import java.util.*;


public class Generator {
    private static char[][] maze = new char[59][59];
    private static boolean[][] visited = new boolean[59][59];;

    private static char[][] start = {{' ', ' ', ' ', ' ', ' ', ' ', ' '}, 
                                     {' ', '#', ' ', ' ', ' ', '#', ' '}, 
                                     {' ', ' ', ' ', ' ', ' ', ' ', ' '}, 
                                     {' ', ' ', ' ', ' ', ' ', ' ', ' '}, 
                                     {' ', ' ', ' ', ' ', ' ', ' ', ' '}, 
                                     {' ', '#', ' ', ' ', ' ', '#', ' '}, 
                                     {' ', ' ', ' ', ' ', ' ', ' ', ' '}};

    private static char[][] boss = {{'#', '#', '#', '#', ' ', '#', '#', '#', '#'}, 
                                    {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, 
                                    {'#', ' ', '#', ' ', ' ', ' ', '#', ' ', '#'}, 
                                    {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, 
                                    {' ', ' ', ' ', ' ', '$', ' ', ' ', ' ', ' '}, 
                                    {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, 
                                    {'#', ' ', '#', ' ', ' ', ' ', '#', ' ', '#'}, 
                                    {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, 
                                    {'#', '#', '#', '#', ' ', '#', '#', '#', '#'}};
                               
    public static int min(int a, int b) {
        if (a < b) return a;
        return b;
    }
    public static int max(int a, int b) {
        if (a > b) return a;
        return b;
    }
    
    public static void fill(int x, int y, char[][] pattern) {
        for (int i = x; i < x + pattern.length; i++) {
            for (int o = y; o < y + pattern[0].length; o++) {
                maze[o][i] = pattern[o - y][i - x];
                if (pattern[o - y][i - x] != ' ') visited[o][i] = true;
            }
        }
    }
    
    public static void generateTunnels() {
        Queue<Pair> bfs = new LinkedList<>();
        bfs.add(new Pair(28, 28));
        
        while(bfs.size() > 0) {
            Pair curPoint = bfs.poll();
            maze[curPoint.getY()][curPoint.getX()] = ' ';
            
            if (!visited[min(57, curPoint.getY() + 1)][curPoint.getX()]) {
                if (Math.random() < 0.63 || maze[curPoint.getY() + 1][curPoint.getX()] == ' ') bfs.add(new Pair(curPoint.getX(), curPoint.getY() + 1));
                visited[curPoint.getY() + 1][curPoint.getX()] = true;
            }
            if (!visited[max(1, curPoint.getY() - 1)][curPoint.getX()]) {
                if (Math.random() < 0.63 || maze[curPoint.getY() - 1][curPoint.getX()] == ' ') bfs.add(new Pair(curPoint.getX(), curPoint.getY() - 1));
                visited[curPoint.getY() - 1][curPoint.getX()] = true;
            }
            if (!visited[curPoint.getY()][min(57, curPoint.getX() + 1)]) {
                if (Math.random() < 0.63 || maze[curPoint.getY()][curPoint.getX() + 1] == ' ') bfs.add(new Pair(curPoint.getX() + 1, curPoint.getY()));
                visited[curPoint.getY()][curPoint.getX() + 1] = true;
            }
            if (!visited[curPoint.getY()][max(1, curPoint.getX() - 1)]) {
                if (Math.random() < 0.6 || maze[curPoint.getY()][curPoint.getX() - 1] == ' ') bfs.add(new Pair(curPoint.getX() - 1, curPoint.getY()));
                visited[curPoint.getY()][curPoint.getX() - 1] = true;
            }
        }
        
        int i = 9;
        while (maze[i][4] == '#' && maze[4][i] == '#') {
            maze[i][4] = ' ';
            maze[4][i] = ' ';
            i++;
        }
        i = 9;
        while (maze[i][54] == '#' && maze[4][58 - i] == '#') {
            maze[i][54] = ' ';
            maze[4][58 - i] = ' ';
            i++;
        }
        i = 9;
        while (maze[58 - i][4] == '#' && maze[54][i] == '#') {
            maze[58 - i][4] = ' ';
            maze[54][i] = ' ';
            i++;
        }
        i = 9;
        while (maze[58 - i][54] == '#' && maze[54][58 - i] == '#') {
            maze[58 - i][54] = ' ';
            maze[54][58 - i] = ' ';
            i++;
        }
    }
                     
    public static void generateChests(Pair curPoint) {
        if (visited[curPoint.getY()][curPoint.getX()]) return;
        visited[curPoint.getY()][curPoint.getX()] = true;
        
        int count = 0;
        if (maze[curPoint.getY() + 1][curPoint.getX()] == '#') count++;
        if (maze[curPoint.getY() - 1][curPoint.getX()] == '#') count++;
        if (maze[curPoint.getY()][curPoint.getX() + 1] == '#') count++;
        if (maze[curPoint.getY()][curPoint.getX() - 1] == '#') count++;
        
        if (count == 3 && Math.random() < 0.15) maze[curPoint.getY()][curPoint.getX()] = '$';
        
        if (maze[curPoint.getY() + 1][curPoint.getX()] == ' ') generateChests(new Pair(curPoint.getX(), curPoint.getY() + 1));
        if (maze[curPoint.getY() - 1][curPoint.getX()] == ' ') generateChests(new Pair(curPoint.getX(), curPoint.getY() - 1));
        if (maze[curPoint.getY()][curPoint.getX() + 1] == ' ') generateChests(new Pair(curPoint.getX() + 1, curPoint.getY()));
        if (maze[curPoint.getY()][curPoint.getX() - 1] == ' ') generateChests(new Pair(curPoint.getX() - 1, curPoint.getY()));
    }
    
    public static char[][] generateMaze() {
        for (int i = 0; i < 59; i++) {
            for (int o = 0; o < 59; o++) {
                maze[o][i] = '#';
            }
        }

        fill(0, 0, boss);
        fill(0, 50, boss);
        fill(50, 0, boss);
        fill(50, 90, boss);
        maze[4 + (int)(Math.random() * 2) * 50][4 + (int)(Math.random() * 2) * 50] = 'e';
        fill(26, 26, start); // fill the start section + boss rooms
        for (int i = 0; i < 59; i++) {
          maze[i][0] = '#';
          maze[0][i] = '#';
          maze[i][58] = '#';
          maze[58][i] = '#';
        }
        
        generateTunnels(); // somehow fill other portions(maybe drill tunnels using bfs)
        
        visited = new boolean[59][59];
        generateChests(new Pair(28, 28)); // do dfs and populate chests/open new passages???
        
        return maze;
    }

}
