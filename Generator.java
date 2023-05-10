import java.util.*;


public class Generator {
    private static char[][] maze = new char[99][99];
    private static boolean[][] visited = new boolean[99][99];;

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

private static char[][] end = {{'#', '#', '#', '#', ' ', '#', '#', '#', '#'}, 
                               {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, 
                               {'#', ' ', '#', ' ', ' ', ' ', '#', ' ', '#'}, 
                               {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, 
                               {' ', ' ', ' ', ' ', 'e', ' ', ' ', ' ', '#'}, 
                               {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, 
                               {'#', ' ', '#', ' ', ' ', ' ', '#', ' ', '#'}, 
                               {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, 
                               {'#', '#', '#', '#', '#', '#', '#', '#', '#'}};
                               
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
        bfs.add(new Pair(48, 48));
        
        while(bfs.size() > 0) {
            Pair curPoint = bfs.poll();
            maze[curPoint.getY()][curPoint.getX()] = ' ';
            
            if (!visited[min(97, curPoint.getY() + 1)][curPoint.getX()]) {
                if (Math.random() < 0.65) bfs.add(new Pair(curPoint.getX(), curPoint.getY() + 1));
                visited[curPoint.getY() + 1][curPoint.getX()] = true;
            }
            if (!visited[max(1, curPoint.getY() - 1)][curPoint.getX()]) {
                if (Math.random() < 0.65) bfs.add(new Pair(curPoint.getX(), curPoint.getY() - 1));
                visited[curPoint.getY() - 1][curPoint.getX()] = true;
            }
            if (!visited[curPoint.getY()][min(97, curPoint.getX() + 1)]) {
                if (Math.random() < 0.65) bfs.add(new Pair(curPoint.getX() + 1, curPoint.getY()));
                visited[curPoint.getY()][curPoint.getX() + 1] = true;
            }
            if (!visited[curPoint.getY()][max(1, curPoint.getX() - 1)]) {
                if (Math.random() < 0.65) bfs.add(new Pair(curPoint.getX() - 1, curPoint.getY()));
                visited[curPoint.getY()][curPoint.getX() - 1] = true;
            }
        }
    }
    
    public static int checkContinuity(Pair curPoint, int count) {
        if (visited[curPoint.getY()][curPoint.getX()]) return count;
        visited[curPoint.getY()][curPoint.getX()] = true;
        
        if (maze[curPoint.getY() + 1][curPoint.getX()] == 'e' || maze[curPoint.getY() + 1][curPoint.getX()] == '$') count++;
        
        if (maze[curPoint.getY() + 1][curPoint.getX()] != '#') generateChests(new Pair(curPoint.getX(), curPoint.getY() + 1));
        if (maze[curPoint.getY() - 1][curPoint.getX()] != '#') generateChests(new Pair(curPoint.getX(), curPoint.getY() - 1));
        if (maze[curPoint.getY()][curPoint.getX() + 1] != '#') generateChests(new Pair(curPoint.getX() + 1, curPoint.getY()));
        if (maze[curPoint.getY()][curPoint.getX() - 1] != '#') generateChests(new Pair(curPoint.getX() - 1, curPoint.getY()));
        
        return count;
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
        for (int i = 0; i < 99; i++) {
            for (int o = 0; o < 99; o++) {
                maze[o][i] = '#';
            }
        }
        
        fill(0, 0, boss);
        fill(0, 90, boss);
        fill(90, 0, boss);
        fill(90, 90, end);
        fill(46, 46, start); // fill the start section + boss rooms
        for (int i = 0; i < 99; i++) {
          maze[i][0] = '#';
          maze[0][i] = '#';
          maze[i][98] = '#';
          maze[98][i] = '#';
        }
        
        while (checkContinuity(new Pair(48, 48), 0) < 4) {
            generateTunnels(); // somehow fill other portions(maybe drill tunnels using bfs)
            visited = new boolean[99][99];
        }
        
        generateChests(new Pair(47, 47)); // do dfs and populate chests/open new passages???
        
        return maze;
    }
    
    public static void main(String[] args) {
        generateMaze();
        
        for (int i = 0; i < 99; i++) {
            for (int o = 0; o < 99; o++) {
                System.out.print(maze[o][i] + "");
            }
            System.out.println();
        }
    }

}
