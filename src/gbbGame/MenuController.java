package gbbGame;

import javax.swing.JOptionPane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class MenuController {

    private final UserRepository repo;
    private final AuthService auth;
    private final RankingService ranking;

    private User currentUser;

    public MenuController(UserRepository repo) {
        this.repo = repo;
        this.auth = new AuthService(repo);
        this.ranking = new RankingService(repo);
    }

    public void start() throws FileNotFoundException, IOException {
        
        currentUser = auth.loginOrSignupFlow();
        if (currentUser == null) {
            
            JOptionPane.showMessageDialog(null, "종료합니다.");
            return;
        }

        
        boolean running = true;
        while (running) {
            int menu = readMenu();
            switch (menu) {
                case 1: //로그아웃
                    doLogout();
                    running = false;
                    break;

                case 2: //게임시작
                    doPlayGame();
                    break;

                case 3: //내전적보기
                    showMyRecord();
                    break;

                case 4: //전체랭킹보기
                    showRanking();
                    break;

                case 5: //비번 변경
                    doChangePassword();
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "1~5만 입력하세요.");
            }
        }
    }

    private int readMenu() {
        while (true) {
            String msg =
                    "메뉴 선택\n" +
                    "1. 로그아웃\n" +
                    "2. 게임시작\n" +
                    "3. 내전적보기\n" +
                    "4. 전체랭킹보기\n" +
                    "5. 비번 변경하기\n";

            String input = JOptionPane.showInputDialog(msg);
            if (input == null) return 1;

            input = input.trim();
            try {
                int n = Integer.parseInt(input);
                if (n >= 1 && n <= 5) return n;
            } catch (NumberFormatException ignore) {}

            JOptionPane.showMessageDialog(null, "숫자 1~5 중에서 선택하세요.");
        }
    }

    private void doLogout() throws FileNotFoundException, IOException {
        // 로그아웃 시 저장
        repo.save();
        JOptionPane.showMessageDialog(null, "로그아웃 완료. 데이터 저장됨.");
        currentUser = null;
    }

    private void doPlayGame() {
        
        PlayGame game = new PlayGame(currentUser.getRecord());
        game.run();

     
        repo.saveUser(currentUser);
    }

    private void showMyRecord() {
        GameRecord r = currentUser.getRecord();
        int total = r.total();
        String text =
                "내 전적\n" +
                "ID: " + currentUser.getId() + "\n" +
                "총 판수: " + total + "\n" +
                "승: " + r.getWin() + "\n" +
                "무: " + r.getDraw() + "\n" +
                "패: " + r.getLose() + "\n" +
                String.format("승률: %.2f", r.winRate());

        JOptionPane.showMessageDialog(null, text);
    }

    private void showRanking() {
        
        Object[] options = {"승률 내림차순", "승률 오름차순"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "랭킹 정렬 방식을 선택하세요",
                "랭킹",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        boolean desc = (choice != 1); // 기본 내림차순
        List<User> ranked = ranking.getRankingByWinRate(desc);

        
        StringBuilder sb = new StringBuilder();
        sb.append(desc ? "전체 랭킹(승률 내림차순)\n" : "전체 랭킹(승률 오름차순)\n");
        sb.append("순위 | ID | 승/무/패 | 승률\n");
        sb.append("---------------------------\n");

        int rank = 1;
        for (User u : ranked) {
            GameRecord r = u.getRecord();
            sb.append(rank++).append(" | ")
              .append(u.getId()).append(" | ")
              .append(r.getWin()).append("/")
              .append(r.getDraw()).append("/")
              .append(r.getLose()).append(" | ")
              .append(String.format("%.2f", r.winRate()))
              .append("\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private void doChangePassword() {
        boolean ok = auth.changePassword(currentUser);
        if (ok) {
            
            repo.saveUser(currentUser);
        }
    }
}
