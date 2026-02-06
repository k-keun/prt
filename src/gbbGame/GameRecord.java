package gbbGame;

import java.io.Serializable;

public class GameRecord implements Serializable {
    
	private static final long serialVersionUID = -2444148261404747671L;
	private int win, lose, draw;

    public GameRecord() {
        this.win = 0;
        this.lose = 0;
        this.draw = 0;
    }

    public GameRecord(int win, int draw, int lose) {
        this.win = win;
        this.draw = draw;
        this.lose = lose;
    }

    public void addWin() { win++; }
    public void addDraw() { draw++; }
    public void addLose() { lose++; }

    public int total() { return win + draw + lose; }

    public double winRate() {
        int t = total();
        if (t == 0) return 0.0;
        return (double) win / t;
    }

    public int getWin() { return win; }
    public int getDraw() { return draw; }
    public int getLose() { return lose; }
}
