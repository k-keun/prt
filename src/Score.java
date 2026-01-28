
import javax.swing.JOptionPane;

public class Score {
    public static void main(String[] args) {

        String[] subjects = {"국어", "영어", "수학"};
        int[] scores = new int[3];

        for (int i = 0; i < subjects.length; i++) {
            while (true) {
                String input = JOptionPane.showInputDialog(subjects[i] + " 점수 입력 (0~100)");

                try{
                    int val = Integer.parseInt(input);

                    if (val < 0 || val > 100) {
                        JOptionPane.showMessageDialog(null, "0~100 사이로 입력하세요.");
                        continue;
                    }

                    scores[i] = val;
                    break;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "숫자만 입력하세요.");
                }
            }
        }

        int[] totalArr = new int[1];
        for (int s : scores) {
        	totalArr[0] += s;
        }
        double avg = totalArr[0] / 3.0;

        String grade;
        switch ((int)(avg / 10)) {
            case 10:
            case 9:
                grade = "A";
                break;
            case 8:
                grade = "B";
                break;
            default:
                grade = "F";
        }

        String result =
                "점수: 국어=" + scores[0] + ", 영어 = " + scores[1] + ", 수학 = " + scores[2] + "\n" +
                "총점: " + totalArr[0] + "\n" +
                "평균: " + String.format("%.2f", avg) + "\n" +
                "학점: " + grade;

        JOptionPane.showMessageDialog(null, result);
        System.out.println(result);
    }
}