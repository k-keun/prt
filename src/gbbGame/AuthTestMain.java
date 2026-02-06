package gbbGame;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AuthTestMain {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        UserRepository repo = new UserRepository("data/users.dat");
        AuthService auth = new AuthService(repo);

        User u = auth.loginOrSignupFlow();
        if (u == null) {
            System.out.println("취소로 종료");
            return;
        }

        System.out.println("로그인 성공: " + u.getId());
        
    }
}
