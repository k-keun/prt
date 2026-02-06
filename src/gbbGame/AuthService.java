package gbbGame;

import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Pattern;

public class AuthService {

    private final UserRepository repo;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final Pattern PW_PATTERN =
            Pattern.compile("^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=.*\\d)(?=.*[\\W_]).{8,12}$");

    private static final DateTimeFormatter LAST_LOGIN_FMT =
            DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 a h시 m분", Locale.KOREAN);

    public AuthService(UserRepository repo) {
        this.repo = repo;
    }

    
    public User loginOrSignupFlow() {
        while (true) {
            String id = JOptionPane.showInputDialog("로그인\nID(이메일)을 입력하세요");
            if (id == null) return null;
            id = id.trim();

            if (!isValidEmail(id)) {
                JOptionPane.showMessageDialog(null, "ID는 이메일 형식이어야 합니다.");
                continue;
            }

            User user = repo.findById(id);

            if (user == null) {
                int joinOption = JOptionPane.showConfirmDialog(
                        null,
                        "처음 사용하는 ID입니다. 회원가입 하시겠습니까?",
                        "회원가입",
                        JOptionPane.YES_NO_OPTION
                );
                if (joinOption != JOptionPane.YES_OPTION) {
                    continue;
                }

                User newUser = signup(id);
                if (newUser == null) continue;
                return newUser;
            }

            
            String pw = JOptionPane.showInputDialog("비밀번호를 입력하세요");
            if (pw == null) return null;

            if (!user.getPassword().equals(pw)) {
                JOptionPane.showMessageDialog(null, "비밀번호가 틀렸습니다.");
                continue;
            }

            showLastLogin(user);

            // 로그인 시간 갱신
            user.setLastLogin(LocalDateTime.now());
            repo.saveUser(user);

            return user;
        }
    }


    private User signup(String id) {
        while (true) {
            String pw = JOptionPane.showInputDialog(
                    "회원가입\n비밀번호를 입력하세요\n(8~12자, 대문자/숫자/특수문자 각 1개 이상)"
            );
            if (pw == null) return null;

            if (!isValidPassword(pw)) {
                JOptionPane.showMessageDialog(null,
                        "아래 형식을 지켜주세요.\n8~12자, 대문자/숫자/특수문자 각 1개 이상 포함해야 합니다.");
                continue;
            }

            String pw2 = JOptionPane.showInputDialog("비밀번호 확인을 다시 입력하세요");
            if (pw2 == null) return null;

            if (!pw.equals(pw2)) {
                JOptionPane.showMessageDialog(null, "비밀번호 확인이 일치하지 않습니다.");
                continue;
            }

            User user = new User(id, pw);
            user.setLastLogin(LocalDateTime.now()); 
            repo.saveUser(user);

            JOptionPane.showMessageDialog(null, "회원가입 완료! 환영합니다, " + id+"님");
            return user;
        }
    }

   
    public boolean changePassword(User user) {
        if (user == null) return false;

        String cur = JOptionPane.showInputDialog("현재 비밀번호를 입력하세요");
        if (cur == null) return false;

        if (!user.getPassword().equals(cur)) {
            JOptionPane.showMessageDialog(null, "현재 비밀번호가 틀렸습니다.");
            return false;
        }

        while (true) {
            String next = JOptionPane.showInputDialog(
                    "새 비밀번호 입력\n(8~12자, 대문자/숫자/특수문자 각 1개 이상)"
            );
            if (next == null) return false;

            if (!isValidPassword(next)) {
                JOptionPane.showMessageDialog(null, "비밀번호 규칙 위반입니다.");
                continue;
            }

            String next2 = JOptionPane.showInputDialog("새 비밀번호 확인을 다시 입력하세요");
            if (next2 == null) return false;

            if (!next.equals(next2)) {
                JOptionPane.showMessageDialog(null, "비밀번호 확인이 일치하지 않습니다.");
                continue;
            }

            user.setPassword(next);
            repo.saveUser(user); 
            JOptionPane.showMessageDialog(null, "비밀번호 변경 완료");
            return true;
        }
    }

    public boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public boolean isValidPassword(String pw) {
        return pw != null && PW_PATTERN.matcher(pw).matches();
    }

    private void showLastLogin(User user) {
        if (user.getLastLogin() == null) {
            JOptionPane.showMessageDialog(null, "첫 로그인입니다.");
            return;
        }
        String msg = "마지막 로그인 시간은\n" + user.getLastLogin().format(LAST_LOGIN_FMT) + " 이었습니다.";
        JOptionPane.showMessageDialog(null, msg);
    }
}

