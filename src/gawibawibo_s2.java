
import javax.swing.JOptionPane;

class GameRecord{
	int win, lose, draw;
	
	public GameRecord() {
		this.win=0;
		this.lose=0;
		this.draw=0;
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
        int t = this.total();
        if (t == 0) return 0.0;
        return (double) win / t;
    }

    public int getWin() { return win; }
    public int getDraw() { return draw; }
    public int getLose() { return lose; }
}

class PlayGame {
    private boolean gameStart = true;
    private GameRecord record;

    public PlayGame() {
        this(new GameRecord());
    }

    public PlayGame(GameRecord record) {
        this.record = record;
    }

    public void run() {
        while (gameStart) {
            Integer userVal = readUserMove(); 
            if (userVal == null) continue;

            int comVal = randomMove(); 

            printHands(comVal, userVal);

            int result = judge(userVal, comVal); 
            applyResult(result);
            printResult(result);

            gameStart = askContinue();
        }

        printSummary();
    }

    private Integer readUserMove() {
        String input = JOptionPane.showInputDialog("가위(1), 바위(2), 보(3) 입력 (또는 가위/바위/보)");
        if (input == null) return null;

        input = input.trim();

        if (input.equals("1") || input.equals("가위")) return 1;
        if (input.equals("2") || input.equals("바위")) return 2;
        if (input.equals("3") || input.equals("보")) return 3;

        System.out.println("올바른 값을 넣으세요.");
        return null;
    }

    private int randomMove() {
        return (int)(Math.random() * 3) + 1; 
    }

    private String moveToString(int v) {
        if (v == 1) return "가위";
        if (v == 2) return "바위";
        if (v == 3) return "보";
        return " ";
    }

    private void printHands(int comVal, int userVal) {
        System.out.println("컴 : " + moveToString(comVal) + ", 당신 : " + moveToString(userVal));
    }

    
    private int judge(int user, int com) {
        if (user == com) return 0;
        if ((user == 1 && com == 3) || 
            (user == 2 && com == 1) || 
            (user == 3 && com == 2)) { 
            return 1;
        }
        return -1;
    }

    private void applyResult(int result) {
        if (result == 1) record.addWin();
        else if (result == 0) record.addDraw();
        else record.addLose();
    }

    private void printResult(int result) {
        if (result == 1) System.out.println("당신 승리");
        else if (result == 0) System.out.println("무승부");
        else System.out.println("컴 승리");
    }

    private boolean askContinue() {
        String input2 = JOptionPane.showInputDialog("게임을 계속할래요? (y/n)");
        if (input2 == null) return false;

        input2 = input2.trim().toLowerCase();

        if (input2.equals("y")) {
            System.out.println("게임을 다시 시작합니다");
            return true;
        }
        if (input2.equals("n")) {
            System.out.println("게임을 종료합니다.");
            return false;
        }

        System.out.println("y 또는 n만 입력하세요.");
        return false;
    }

    private void printSummary() {
        int total = record.total();
        System.out.printf(
            "총 판수 : %d\n승 : %d\n무 : %d\n패 : %d\n승률 : %.2f\n",
            total, record.getWin(), record.getDraw(), record.getLose(), record.winRate()
        );
    }
}


public class gawibawibo_s2 {
public static void main(String[] args) {
		/*
		 * 사용자는 가위,바위,보 대신 1,2,3을 값으로 낼 수 있습니다.
		 * 그럼 프로그램은 이값을 분석해서 랜덤한 컴퓨터의 값(Math.random())
		 * 으로 게임을 진행 시키고 결과를 다음처럼 출력시키세요
		 *  
		 *  컴 : 가위, 당신: 보
		 *  컴 승리!
		 *  
		 *  위결과를 출력 후, 게임을 다시 할건지 물어본다.
		 *  ex>게임을 계속할래요? 이때 사용자는 y or n를 입력하게 되고
		 *  이에따라서 게임이 계속되거나 끝나거나 합니다.
		 *  
		 *  단 게임이 끝날때는 총전적,승리,무,패 ,승률 (소수점2자리까지)을 출력시키고 게임을 종료시킨다
		 * 
		 */
	 new PlayGame().run();
		

	}
}


