package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        gameOver = checkGameOver(board);
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver(board);
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;
        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.
        board.setViewingPerspective(side);

        for (int colNum=0;colNum<board.size();colNum++){
            int[] col_list = {0,0,0,0};
            for (int rowNum=0;rowNum<board.size();rowNum++){
                Tile t = board.tile(colNum,rowNum);
                if (t!=null){
                    col_list[rowNum] = t.value();
                }
            }
            int[] tmp_list = reverse(col_list);
            int[] pos_list =  getPosition(tmp_list);
            for (int rowNum=board.size()-1;rowNum>-1;rowNum--){
                Tile t = board.tile(colNum,rowNum);
                if (t!=null){
                    int target_row = rowNum+pos_list[3-rowNum];
//                    System.out.println(colNum+","+rowNum+"->"+colNum+","+target_row);
                    boolean a = board.move(colNum,rowNum+pos_list[3-rowNum],t);
                    if (a){
                        score+=t.value()*2;
                    }
                    if (rowNum!=target_row){
                        changed = true;
                    }
                }
            }
        }
        board.setViewingPerspective(Side.NORTH);
        System.out.println("-------------------------");

        boolean end =  checkGameOver(board);
        System.out.println("结束？"+end);
        if (changed) {
            setChanged();
        }
        return changed;
    }

    private int[] reverse(int[] a) {
        int[] return_list = {0, 0, 0, 0};
        for(int i=0;i<a.length;i++){
            return_list[a.length-i-1] = a[i];
        }
        return return_list;
    }
    private int[] getPosition(int[] a) {
//        输入[1,1,2,2] 返回 [0,1,1,2] 每个变量左移的格子数
        int[] return_list_colasp = colasp(a);
        a = moveList(a,return_list_colasp);

        int[] return_list_shrink = {0, 0, 0, 0};
        for(int i=0;i<a.length-1;i++){
            if (a[i]==a[i+1]){
                for(int j=i+1;j<a.length;j++){
                    return_list_shrink[j] += 1;
                }
                i+=1;
            }
        }
        int[] return_list = {0, 0, 0, 0};
        for(int i=0;i<return_list_colasp.length;i++){
            return_list[i] = return_list_colasp[i]+return_list_shrink[i-return_list_colasp[i]];
        }
        return return_list;
    }

    private int[] moveList(int[] list,int[] pos_list) {
//        移动格子
        int[] return_list = {0, 0, 0, 0};
        for(int i=0;i<list.length;i++){
            return_list[i-pos_list[i]] = list[i];
        }
        return return_list;
    }

    private int[] colasp(int[] list) {
//        去0
        int[] return_list = {0, 0, 0, 0};
        for(int i=0;i<list.length-1;i++){
            if (list[i]==0){
                for(int j=i+1;j<list.length;j++){
                    return_list[j]+=1;
                }
            }
        }
        return return_list;
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        for (int rowNum=0;rowNum<b.size();rowNum++){
            for (int colNum=0;colNum<b.size();colNum++){
//                System.out.println(b.tile(colNum,rowNum));
                if (b.tile(colNum,rowNum)==null){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
        for (int rowNum=0;rowNum<b.size();rowNum++){
            for (int colNum=0;colNum<b.size();colNum++){
//                System.out.println(b.tile(colNum,rowNum));
                if (b.tile(colNum,rowNum)!=null && b.tile(colNum,rowNum).value()==MAX_PIECE ){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        if (emptySpaceExists(b)){
            return true;
        }else {
            for (int rowNum=0;rowNum<b.size()-1;rowNum++){ //竖扫
                for (int colNum=0;colNum<b.size();colNum++){
                    if (b.tile(colNum,rowNum).value()==b.tile(colNum,rowNum+1).value() ){
                        return true;
                    }
                }
            }

            for (int colNum=0;colNum<b.size()-1;colNum++){
                for (int rowNum=0;rowNum<b.size();rowNum++){ //横扫
                    if (b.tile(colNum,rowNum).value()==b.tile(colNum+1,rowNum).value() ){
                        return true;
                    }
                }
            }

            return false;
        }
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
