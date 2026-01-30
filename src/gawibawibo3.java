package javaFund;
import java.awt.print.PrinterException;

import javax.swing.JOptionPane;


public class gawibawibo3 {
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
		boolean gameStart=true;

		String input="";
		while(gameStart) {
			int win=0;
			int draw=0;
			int lose=0;
			int userVal=0;
			
			input = JOptionPane.showInputDialog("게임을 시작합니다.");
			System.out.println(input);
			//위 입력값이 무엇인지 찾아보도록
			try {
				userVal= Integer.parseInt(input);

			}catch (Exception e) {
				System.out.println(e.getMessage());
				//e.printStackTrace();
			
						
				if(input.equals("1") || input.equals("가위")) {
					System.out.println("가위를 입력하셨군요.");
					userVal=1;
				}else if(input.equals("2") || input.equals("바위")){
					System.out.println("바위를 입력하셨군요.");
					userVal=2;
				}else if(input.equals("3") || input.equals("보")){
					System.out.println("보를 입력하셨군요.");
					userVal=3;
				}else {
					System.out.println("올바른 값을 넣으세요.");
					continue;
				}	
				
//				switch(input) {
//				case "가위":
//					userVal=1;
//					break;
//				case "바위":
//					userVal=2;
//					break;
//				case "보":
//					userVal=3;
//					break;
//				default:
//					userVal=4;
//					break;
//				}
//				if(userVal>3) {
//				System.out.println("올바른 값을 넣으세요.");
//				continue;
//			}
			}

				int com = (int)(Math.random()*3+1);
				System.out.println("컴퓨터의 값 : "+com);
				
				
				
				//게임의 결과 체크
				if(userVal==com) {
					++draw;
				}
				else if(userVal==1 && com==2) {
					++lose;
				}
				else if(userVal==1 && com==3) {
					++win;
				}
				else if(userVal==2 && com==3) {
					++lose;
				}
				else if(userVal==2 && com==1) {
					++win;
				}
				else if(userVal==3 && com==1) {
					++lose;
				}
				else if(userVal==3 && com==2) {
					++win;
				}
				
				
				
				
				String input2 = JOptionPane.showInputDialog("게임을 계속할래요?");
				if("y".equals(input2)) {
					gameStart=true;
					System.out.println("게임을 다시 시작합니다");
				}
				else if("n".equals(input2)) {
					System.out.println("게임을 종료합니다.");
					int gameNum=win+draw+lose;
					System.out.printf("총 판수 : %d\n 승 : %d\n 무 : %d\n 패 : %d\n 승률 : %.2f",gameNum,win,draw,lose,(double)win/gameNum);
					gameStart=false;
				}
		}

	}
}


