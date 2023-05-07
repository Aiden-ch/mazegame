import java.util.*;

/*
# # # # # # # # #
#               #
#   #       #   #
#               #
#       $       #
#               #
#   #       #   #
#               #
# # # # # # # # #

op_r = maze_section('# # #', '#    ', '# # #', 'r') #right open
op_l = maze_section('# # #', '    #', '# # #', 'l') #left open
op_u = maze_section('#   #', '#   #', '# # #', 'u') #up open
op_d = maze_section('# # #', '#   #', '#   #', 'd') #down open
four_cr = maze_section('#   #', '     ', '#   #', 'udlr') #all open
thr_cr_d = maze_section('# # #', '     ', '#   #', 'dlr') #up closed
thr_cr_r = maze_section('#   #', '#    ', '#   #', 'udr') #left closed
thr_cr_u = maze_section('#   #', '     ', '# # #', 'ulr') #down closed
thr_cr_l = maze_section('#   #', '    #', '#   #', 'udl') #right closed
cor_d_r = maze_section('# # #', '#    ', '#   #', 'dr') #down, right open
cor_d_l = maze_section('# # #', '    #', '#   #', 'dl') #down, left open
cor_u_r = maze_section('#   #', '#    ', '# # #', 'dl') #up, right open
cor_u_l = maze_section('#   #', '    #', '# # #', 'dl') #up, left open
str_l_r = maze_section('# # #', '     ', '# # #', 'dl') #left, right open
str_u_d = maze_section('#   #', '#   #', '#   #', 'dl') #up, down open
ch_op_r = maze_section('# # #', '# $  ', '# # #', 'r') #right open, chest
ch_op_l = maze_section('# # #', '  $ #', '# # #', 'l') #left open, chest
ch_op_u = maze_section('#   #', '# $ #', '# # #', 'u') #up open, chest
ch_op_d = maze_section('# # #', '# $ #', '#   #', 'd') #down open, chest
four_op = maze_section('     ', '     ', '     ', 'udlr') #all, all open

#special
ch_four_op = maze_section('     ', '  $  ', '     ', 'udlr') #all, all open, chest
e_four_op = maze_section('     ', '  e  ', '     ', 'udlr') #all, all open, end

*/



public class Generator {
  char[][] maze = new char[99][99];
  
  public static void fill(int x, int y, Section pattern) {
    for (int i = x; i < x + pattern.getX(); i++) {
      for (int o = y; o < y + pattern.getY(); o++) {
        maze[i][o] = pattern.get(i, o);
      }
    }
  }
  
  public static char[][] generateMaze() {
    
    fill(45, 45, start); // fill the start section
    
    // fill boss rooms
    
    
    // somehow fill other portions
    
    
    // do dfs and populate chests/open new passages???
  }
  
}
