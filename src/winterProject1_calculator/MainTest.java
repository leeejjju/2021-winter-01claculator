package winterProject1_calculator;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainTest extends JFrame{
	/*
	 뭔진모르겠지만 경고뜨길래 없애려구 추가했슴 한김에 정보적기 
	 2021-12-21-11:00~ 2021-12-23-11:00
	 2021-winter 과제1, 48시간 안에 계산기 만들기 
	 HGU 전산전자공학부_ 22100579 이진주
	 */
	private static final long serialVersionUID = 1L;

	//파일 작업을 위해 글로벌로 선언해줄 친구들 
	static boolean saving = false; //저장중인지 아닌지 구분하는 친구. 
	static FileWriter writer; //파일라이터의...그머라하지 포인터같은느낌 
	static int count = 0; //몇번째 파일인지 세어주는 친구.  
	
	
	//계산기 구현하여 호출하는 메소드. 입력받은 스트링이 계산기이름.
	public static void calc  (String title){ 
		
			//필요한거 뚝딱뚝딱 만들어놓기 
			final int width = 200, height = 260;
			GridLayout cell = new GridLayout(4,4, 2, 2); //버튼패널에서 쓸 그리드 레이아웃
			FlowLayout layout = new FlowLayout(); //프레임에서 쓸 플로우 레이아웃(근데 이게 디폴트아닌가..?)

			
			//프레임 기본설정 
			JFrame frame = new JFrame(title); //새로운 프레임 생성 
			frame.setSize(width, height); //프레임의 사이즈 설정
			frame.setResizable(false);//사용자가 임의로 프레임의 크기를 변경시킬 수 있는가>> 앙대
			frame.setLocationRelativeTo(null);//화면의 어느 위치에서 첫 등장할지>> null이면 자동 센터지정 
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//프레임 닫으면 프로그램도 같이 종료.
			frame.setLayout(layout); //레이아웃설정 플로우로 
			JLabel Title = new JLabel(title); //프레임 작아서 제목 잘리길래.. 라벨넣어줌ㅎㅎ 
			frame.add(Title);
			
			
			//스크린 패널 작업 
			JPanel screen = new JPanel(); //스크린 패널 생성	
			screen.setBackground(Color.LIGHT_GRAY);//회색칠해주기(깔맞춤ㅎㅎ)
			JTextField display = new JTextField(18);//초기값 0이고 18자까지 입력되는 텍스트필드 생성
			display.setHorizontalAlignment(JTextField.RIGHT); //우측줄맞춤
			display.setEnabled(false); //직접 입력하지 못하게 하기(버튼을 통하도록)
			screen.add(display); //패널에 텍스트필드 집어넣기 
			frame.add(screen);//프레임에 패널 집어넣기
			
				
			//버튼즈 패널 작업 
			JPanel buttons = new JPanel();//버튼즈 패널 생성
			buttons.setLayout(cell); //레이아웃 그리드로 설정(표처럼 배치해줌)
			StringBuffer formula = new StringBuffer(15); //입력완료한 수식 저장할 버퍼문자열(수정가능하도록)
			
			
			//버튼 생성하여 추가 
			//숫자버튼친구들
			JButton nums[] = new JButton[10]; 	
			for( int i = 0; i < 10; i++) {
				nums[i] = new JButton(i+""); //버튼생성
				nums[i].setBackground(Color.WHITE); //버.꾸(버튼꾸미기)
				
				final int num = i; //이벤트 내부에서 쓰려면 파이널이여야된대서... 
				
				//반복하는김에 버튼에 이벤트 맞물려주기
				nums[i].addActionListener(event ->{
					String line = display.getText(); //line문자열에 현재 텍스트필드상의 문자 저장하고 
					display.setText(line+(int)num); //거따가 입력한 숫자 더해서 텍스트필드에 출력 
				});
				
			}
			
			//기호버튼친구들
			JButton opers[] = new JButton[6]; 	
			char OPER[] = {'=', '/', '*', '+', '-', '.'}; //인덱스에 매칭할 char배열 
			
			for( int i = 0; i < 6; i++) {
				opers[i] = new JButton(OPER[i]+""); //버튼생성
				opers[i].setBackground(Color.WHITE); //버.꾸
				
				final char oper = OPER[i];
				
				opers[i].addActionListener(event ->{
					String line = display.getText();
					//입력완료시 
					if(oper == '=') {
						formula.append(line);//최종문자열 formula에 현재상태의 line 저장
						if(line.length() > 18) { //범위 벗어나면 알림
							System.out.println("입력가능 범위를 초과했습니다.");
							line = "";
						}else if(line.length() == 0){ //암것도 입력 안했어도 알림
							System.out.println("수식을 입력해주세요.");
						}else { //두 경우가 아니라면 텍스트필드에 결과 출력
							display.setText("= "+getResult(formula)); //메서드한테 계산시킨 결과값 표시 
						}
						formula.delete(0, formula.length()); //초기화 
					
					//기타문자버튼
					}else { 
						char pre = line.charAt(line.length()-1);//기호 연속적으로 못 받도록 바로앞에 검사해서 숫자 다음만 입력받기 
						if(pre >= '0' && pre <= '9') {
							display.setText(line+oper);
						}
					}
					
				});
				
			}
			
				
			//버튼즈 패널에 무지성으로 버튼 추가하기... 이런거 좀 반복문으로 안되나ㅜ 
			buttons.add(nums[7]);
			buttons.add(nums[8]);
			buttons.add(nums[9]);
			buttons.add(opers[1]);
			
			buttons.add(nums[4]);
			buttons.add(nums[5]);
			buttons.add(nums[6]);
			buttons.add(opers[2]);
			
			buttons.add(nums[1]);
			buttons.add(nums[2]);
			buttons.add(nums[3]);
			buttons.add(opers[3]);
			
			buttons.add(opers[4]);
			buttons.add(nums[0]);
			buttons.add(opers[5]);
			buttons.add(opers[0]);
				
			frame.add(buttons);//프레임에 패널 집어넣기
			
			
			
			//***************파일에 저장하는 버튼(추가기능)
			JButton saveFile = new JButton("S");
			saveFile.setBackground(Color.LIGHT_GRAY);
			frame.add(saveFile);
			//boolean writing = false;
			
			saveFile.addActionListener(event ->{
				
				if(saving) { //기록종료
					Title.setText(title); //라벨 원상복귀
					saveFiles(-1, ""); //파일라이터 닫아 파일 저장해주는 메소드 실행 
					
					saving = false;
					
				}else { //기록시작 
					Title.setText(title+"(기록중)"); //라벨로 상태알림 
					saveFiles(1, ""); //파일 생성하고 파일라이터 연결해주는 메소드 실행 
					
					saving = true;
				}
				
			});
			//***********************************
			
			
			//전체지우기: 초기화 버튼
			JButton clear = new JButton("C");
			clear.setBackground(Color.LIGHT_GRAY);
			clear.addActionListener(event ->{
				display.setText(""); //다지워버리기~
			});
			frame.add(clear);
			
			
			//하나지우기: 뒤로가기 버튼
			JButton back = new JButton("BACK");
			back.setBackground(Color.LIGHT_GRAY);
			back.addActionListener(event ->{
				StringBuffer line = new StringBuffer(display.getText());//버퍼스트링으로 현내용 입력받아
				if(!(line.length() == 0)) { //아예 내용이 없었지 않다면 
					line.delete(line.length()-1, line.length()); //맨뒤 하나 지워버리고
					display.setText(line+""); //그걸 다시 입력해줌 
				}
			});
			frame.add(back);
			

			//마무으리
			JLabel info = new JLabel("2021-winter | java | project1 | Leeejjju"); //정보라벨
			info.setFont(new Font("Arial", Font.PLAIN, 10));
			info.setForeground(Color.LIGHT_GRAY);
			frame.add(info);
			
			frame.setVisible(true); //프레임을 보이게 하기
			
	}
	
	
	 //StringBuffer 형태의 수식을 받아 계산하고 결과를 String형태로 반환하는 메서드
	public static String getResult(StringBuffer F) {
		
		//입력받은 첫자가 "="이라면(이전 결과에서 연속입력) 지워줘야할듯 
		if(F.charAt(0) == '=') {
			F.delete(0,	 2); //처음의 =과 공백, 총 두 자 지워줌
		}
		
		//입력받은 식 정보 출력(저장중이면 파일에도)
		System.out.println("입력받은 식: "+F);
		if(saving) {
			saveFiles(0, "입력받은 식: "+F);
		}
		
		//필요한애들 뚝딱뚝딱 만들어놓기 
		 float R = 0; //임시계산, 누적계산되고 끝내 반환될 Result 
		 char oper  = '+'; //연산을 위해 직전 부호 저장할 문자변수 
		 int start = 0; //숫자범위의 시작을 표시할 인덱스 
		 int end = 0; //숫자범위의 끝 인덱스. 앞으로 처음만날 oper의 인덱스가 들어갈것임.
		 boolean flag =  false; //지금 검토중인 숫자범위가 정수인지 실수인지. 실수면 true 
		 char c; //문자열의특정 인덱스번째 문자를 담아서 검사하고 하여튼 계속 쓸놈.
		 
		 int i = 0; //반복문에서 쓸 친구 
		 int preStart = 0; //곱나눗셈에서 대체범위 지정용으로 이전 시작인덱스 임시저장하는 용도 
		 boolean waiting = false; //지금 곱하기한번 지나가서 다음값이랑 계산하려고 대기타고있다고 알려주는 용도 
		 
		 /*for로 돌리다가 인덱스 벗어나면 안대니까 일단 while(true)로 돌리고 첫시작을 종료조건 검토로 하자
		 1. 이일단 문자열을 함 훑으면서 *나 /가 발견되면 >>
		 	1-1 앞뒤로 문자열이나 null만날때까지의 범위를 찾아서 해당 계산의 시작인덱스, 끝인덱스로 저장하고
		 		1-1-1 이거..어케한담. start랑 end 여기서도ㅓ야겟다 *,/만나면 end저장, 아무튼 oper만나면 start저장 \
		 		1-1-2아놔 뒤에항이랑 계산해야하는구나... 그렇게하자. waiting기준으로 R에 덮어씌울지 누적계산할지 결정. 
		 	1-2 그거기준으로 도려내서 형변환 후 계산해서 그 값(R에저장) 으로 바꿔치기
		 	1-3 다했으면 다시 마저훑어ㅇㅇ*/

		 
		 //곱셈 나눗셈을 일차적으로 계산하기 
		 while(true) {
			
			 //종료조건
			if(F.length() <= i) { //길이에 인덱스가 도달하면

				if(waiting) { //근데 만약 아직 대기중인데 끝에 도달하면.. 대기완료 위한 절차 한번 밟아줄 것 
					end = i; //여기를 범위끝으로 지정 
					//System.out.println("대기상태로 끝에 도달함");
					
					if(flag) { //실수면
						if(oper == '*') R *= Float.parseFloat(F.substring(start,end));
						else R /= Float.parseFloat(F.substring(start,end));
					}else { //정수면 
						if(oper == '*') R *= Integer.parseInt(F.substring(start,end));
						else R  /= Integer.parseInt(F.substring(start,end));
					}
					
					F.replace(preStart, end, R+"");//*도려내서바꿔치기
					//System.out.println("대기끝");
					i = preStart; //인덱스 대체한 시작점으로 이동 
				}
				
				//System.out.println("종료합니다");
				R = 0; //다음과정에서 쓰기 위해 초기화 
				start = 0;
				oper = '+';
				flag = false;  
				
				break;
			}
			
			//진행중일때 일어나는 일들
			c = F.charAt(i); //한놈 담고
			
			if (!(c >= '0' && c <= '9')) { //c가 숫자가 아니면:기호라면
				//System.out.println("기호발견" + c);
				
				if(c == '.') { //dot이면 플래그만 세우고 스루
					flag = true;
					i++;
					continue;
					
				}
				
				end = i; //dot도 걸렀겠다 일단 끊어준다
		
				if(waiting) { //대기타던중이었다면: 앞 숫자가 들어있을 R에 누적계산하고 계산된 R로 지정된 인덱스 범위를 대체하기
					
					if(flag) { //실수면
						if(oper == '*') R *= Float.parseFloat(F.substring(start,end));
						else R /= Float.parseFloat(F.substring(start,end));
						flag = false; //초기화 
					}else { //정수면 
						if(oper == '*') R *= Integer.parseInt(F.substring(start,end));
						else R  /= Integer.parseInt(F.substring(start,end));
					}
					
					F.replace(preStart, end, R+"");//*도려내서바꿔치기
					waiting = false; //대기끝.
					//System.out.println("대기끝");
					i = preStart; //인덱스 대체한 시작점으로 이동 
					start = i++;
					continue;	
					
				}
				
				
				if((c == '*' || c == '/')) { //대기상태 아닌데 만난 곱셈이나 나눗셈이면(대기시작)
					//System.out.println(c+"발견");
					
					if(flag) { //실수인가 
						if(c == '*') R = Float.parseFloat(F.substring(start,end));
						else R = Float.parseFloat(F.substring(start,end));			
					}else { //정수
						if(c == '*') R = Integer.parseInt(F.substring(start,end));
						else R = Integer.parseInt(F.substring(start,end));	
					}
					
					oper = c; //지금의 기호를 다음계산에 쓸 오퍼레이터로 저장 
					preStart = start; //이전시작인덱스에 임시저장(대기끝날때 대체범위 계산용) 
					waiting = true; //대기시작.
					//System.out.println("대기시작");
					
				}
				
				start = end+1; //다음 숫자범위 시작위치 지정 
				flag = false;//실수영역 끝
			}
			
			i++; //한바퀴 루프했으니 인덱스증가 	
		}
		 
		  	
		 /*2. 다 훑어서 모든 곱셈나눗셈 제거했으면, 앞에서부터 연산 시작(for문쓰자~)
		 	2-1 방금만난놈(char c에 저장)이 숫자인지 기호인지 판단
		 		2-1-1 문자다? dot인지 아닌지 판단
		 			2-1-1-1 dot이다? flag를 true로 바꾸기(이거기준으로 정수실수판단)
		 			2-1-1-2 다른놈이다? 
		 					2-1-1-2-1 end에 본인인덱스 저장. 글고 
		 					2-1-1-2-2 flag에 따라 형변환한 start-end사이의 값을 oper에 따라 R에 누적계산
		 					2-1-1-2-4 oepr에다 현재 본인값 저장, flag 회수 
		 					2-1-1-2-5 start에 본인인덱스+1 저장 */
		 
		 
		 //앞서 손질된 수식을 앞에서부터 계산하고 R에 누적하기 
		 for(i = 0; i < F.length(); i++) {
			 
			 c = F.charAt(i); //한놈 잡아다가 
			 
			 if(!(c >= '0' && c <= '9')) { //검사. 기호인가? 
				 
				 if( c == '.') { //dot이면 해당구역 실수라고 알리는 플래그 세우고 나머지 스루 
					 flag = true;
					 continue; //나머지영역 스루 
				 }

				 end = i; //영역구분 찍어주고 
				 R = getResult( oper, start,  end,  F,  R,  flag); //계산하여 값 누적 

				 oper = c; //다음연산 위해 기호 저장 
				 flag = false; //플래그 회수 
				 start = end+1; //다음 숫자영역의 시작점 저장 
				 
			 }
		}
		 //ㅇ ㅏ oper를 못만나는바람에 마지막 연산이 안되는구나
		 // 그냥 탈출하면 기본적으로 마지막연산 함 더 해줘야겠다 
		 
		 end = i; //영역구분 찍어주고 
		 R = getResult( oper, start,  end,  F,  R,  flag); //ㄱㅖ산 
	

		/*
		 	Integer.parseInt(String s); >>문자열을 기본형으로...!! 
		 	F.charAt(int index) >>해당인덱스의 문자를 반환
		 	F.delete(int start, int end) >>start번부터 index직전까지의 문자열 삭제한 문자열 반환 
		 	F.substring(int start,int end)>>start번부터 index직전까지의 문자열을 복사하여 반환
		 	F.insert(int pos, type a) >>a를 문자열로 변환하여 지정된 위치(pos)에 추가함 
		 	F.replace(int start, int end, String s) >>아주나이스해요 위에두개합친거. 바꿔치기, 대체의 개념인듯 
		 	F.toString() >>스트링형태로 바꿔준다는디 머..굳이? 
		 */
		
		 //정수면 정수형으로 바꿔서 리턴. 저장중이면 파일에도 출력 
		 if(R%1 == 0) { 
			 int r = (int)R; 
			 System.out.println("계산 결과: "+r+"\n");
			if(saving) {
				saveFiles(0, "계산 결과: "+r+"\n");
				}
			 return r+"";
		 }
		
		 //정수판단에서 안걸렸으면 실수형태 그대로 리턴. 저장중이면 파일에도 출력 
		System.out.println("계산 결과: "+R+"\n");
		if(saving) {
			saveFiles(0,"계산 결과: "+R+"\n");
		}
		return R+"";
		
		
	}
	
	
	/*
	 * 추가기능 뭐할까.. 스쳐지나간 생각들 구체화해보기
	  파일에 입력결과 저장하기? 콘솔에 나오는거 그대로 파일에도 써주면 될듯
	 	기록시작 기록끝 버튼 만들어서 파일 여닫고... 라벨에 기록중 표시 나오게? 
	 	2-1 기록시작끝 버튼 만들고 기록중 표시될 공간 만들고
	 	2-2 버튼이벤트로- 기록중인지 아닌지 불리안타입에 따라 다르게 행동하는데
	 		2-2-1 기록중 아니면 파일열기, 이름은 자동지정되게 해야할듯. 글고 기록중변수 실행.
	 				기록중변수가 실행되면 콘솔출력과 동시에 파일출력도 이루어지도록 수정
	 		2-2-2 기록중이었으면 파일닫기. 콘솔에 ~이름으로 저장에 성공했습니다 출력 
	 	3. ㅇㅋ가보자고 
	
	 */
	
	
	//오퍼레이터, 자료형 등을 고려해 연산값을 반환하는 메소드 
	static float getResult(char oper, int start, int end, StringBuffer F, float R, boolean flag) { 
		
		 if(flag) { //실수여부에 따라 적절한 연산값 형변환, 연산 
			if(oper == '+') {
				return R += Float.parseFloat(F.substring(start,end));
			}else if(oper == '-'){
				return R -= Float.parseFloat(F.substring(start,end));
			 }else if(oper == '*'){
				 return R *= Float.parseFloat(F.substring(start,end));
			 }else if(oper == '/'){
				 return R /= Float.parseFloat(F.substring(start,end));
			 }
			
		 }else {
			 if(oper == '+') {
				 return R += Integer.parseInt(F.substring(start,end));
			 }else if(oper == '-'){
				 return R -= Integer.parseInt(F.substring(start,end));
			 }else if(oper == '*'){
				 return R *= Integer.parseInt(F.substring(start,end));
			 }else if(oper == '/'){
				 return R /= Integer.parseInt(F.substring(start,end));
			 }
			 
		 }
		 return -1; //아마 여기까지 오기전에 계산한 현상태까지의 R을 리턴할거임 이건 걍 경고뜨길래 넣어준거 
		 
	}
	
	
	//파일을 다루는 메소드
	static void saveFiles(int workState, String s) {  
		//workState가 1이면 파일생성, 0이면 파일에 입력, -1이면 파일 닫기..
		try {
			if(workState == 1) { //파일생성
				File newFile = new File("C:\\Java\\workspace\\winterProject1_calculator\\savedFormulaFIle", "Formula_"+count+".txt"); 
				//새 파일 인스턴트 생성(파일명은 count에 따라 달라지게)
				newFile.createNewFile(); //새로만든 파일인스턴트가 관여할 실제 파일 생성
				writer = new FileWriter(newFile); //그과 연결되는 파일라이터 생성
				System.out.println("파일이 생성되었습니다.");
				
			}else if(workState == 0) { //파일에 쓰기
				writer.write(s+"\n");//파일에 해당 문자열 쓰기
				
			}else { //파일 닫기, 카운트 증가 
				writer.close(); //파일라이터 닫기
				System.out.println("Formula_"+count+".txt"+" 이름으로 파일을 저장했습니다.");
				count ++; //다음파일은 증가된 카운트에 따른 이름을 갖도록 
			}
			
		} catch (IOException e) { //예외처리 
			System.out.println("그런파일없음메");
			e.printStackTrace();
			
		} 
	}
	
	
	//메인..메소드.. 역할은 계산기 호출하기
	public static void main(String[] args) {
		
		calc(":: Simple Calculator ::");
		
	}
	
	
	//끗!
	
}
	









