import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;
import java.text.*;



public class CustomerManager5 {

	//고객 정보를 저장할 자료구조 선언
	static ArrayList<Customer> custList = new ArrayList<>();
	static ArrayList<UpdateLog> logList = new ArrayList<>();

	//리스트 정보를 조회하기 위해 인덱스를 필요로 함
	static int index = -1;

	static int count = 0;//custList.size()

	//기본 입력장치로부터 데이터를 입력받기 위해 Scanner객체 생성
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		loadCustomerData();

		
		while(true) {
			count = custList.size();
			System.out.printf("\n[INFO] 고객 수 : %d, 인덱스 : %d\n", count, index);
			System.out.println("메뉴를 입력하세요.");
			System.out.println("(I)nsert, (P)revious, (N)ext, " +
					"(C)urrent, (U)pdate, (D)elete, (L)ogs, Print (A)ll, (S)ave, (Q)uit ");
			System.out.print("메뉴 입력: ");
			String menu = scan.next();
			menu = menu.toLowerCase();	//입력한 문자열을 모두소문자로 변환
			switch(menu.charAt(0)) {
			case 'i':
				System.out.println("고객정보 입력을 시작합니다.");
				insertCustomerData();
				System.out.println("고객정보를 입력했습니다.");
				break;
			case 'p' :
				System.out.println("이전 데이터를 출력합니다.");
				if(index <= 0) {
					System.out.println("이전 데이터가 존재하지 않습니다.");
				}else {
					index--;
					printCustomerData(index);
				}
				break;
			case 'n' :
				System.out.println("다음 데이터를 출력합니다.");
				if(index >= count-1) {
					System.out.println("다음 데이터가 존재하지 않습니다.");
				}else {
					index++;
					printCustomerData(index);
				}
				break;
			case 'c' :
				System.out.println("현재 데이터를 출력합니다.");
				if( (index >= 0) && (index < count)) {
					printCustomerData(index);
				}else {
					System.out.println("출력할 데이터가 선택되지 않았습니다.");
				}
				break;			
			case 'u' :
				System.out.println("데이터를 수정합니다.");
				if( (index >= 0) && (index < count)) {
					System.out.println(index + "번째 데이터를 수정합니다.");
					updateCustomerData(index);
				}else {
					System.out.println("수정할 데이터가 선택되지 않았습니다.");
				}
				break;
			case 'd' :
				System.out.println("데이터를 삭제합니다.");
				if( (index >= 0) && (index < count)) {
					System.out.println(index + "번째 데이터를 삭제합니다.");
					deleteCustomerData(index);
				}else {
					System.out.println("삭제할 데이터가 선택되지 않았습니다.");
				}
				break;
			case 's' :
				saveCustomerData();
				break;
			case 'q' :
				System.out.println("프로그램을 종료합니다.");
				scan.close();	//Scanner 객체를 닫아준다.
				System.exit(0);	//프로그램을 종료시킨다.
				break;	
			case 'l' :
				printLogs();
				break;
			case 'a':
				printCustomerALL();
				break;
			default : 
				System.out.println("메뉴를 잘 못 입력했습니다.");	
			}//end switch
		}//end while
	}//end main

	public static void insertCustomerData() {
		System.out.print("이름 : ");	
		String name = scan.next();
		System.out.print("성별(M/F) : ");	
		char gender = scan.next().charAt(0);
		System.out.print("이메일 : ");	
		String email = scan.next();
		System.out.print("출생년도 : ");	
		int birthYear = scan.nextInt();

		//입력받은 데이터로 고객 객체를 생성
		Customer cust = new Customer(name, gender, email, birthYear);
		//고객 객체를 ArrayList에 저장
		custList.add(cust);

		insertLogData(cust.getCustomerNum(), 'i');
	}


	public static void insertLogData(int customer, char updatelog) {

		//입력받은 데이터로 고객 객체를 생성

		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy.MM.dd HH:mm:ss", Locale.KOREA );
		Date currentTime = new Date ( );
		String dTime = formatter.format ( currentTime );
		UpdateLog nlog = new UpdateLog(customer, updatelog, dTime);

		//고객 객체를 ArrayList에 저장
		logList.add(nlog);
	}

	
	//고객데이터 출력
	public static void printCustomerData(int index) {
		Customer cust = custList.get(index);
		System.out.println("==========CUSTOMER INFO================");
		System.out.println("이름 : " + cust.getName());
		System.out.println("성별 : " + cust.getGender());
		System.out.println("이메일 : " + cust.getEmail());
		System.out.println("출생년도 : " + cust.getBirthYear());
		System.out.println("=======================================");
	}

	//index 위치의 고객정보를 삭제합니다.
	public static void deleteCustomerData(int index) {
		insertLogData(custList.get(index).getCustomerNum(), 'd');
		custList.remove(index);
	}

	//index 위치의 고객 정보를 수정합니다.
	public static void updateCustomerData(int index) {
		Customer cust = custList.get(index);
		System.out.println("---------UPDATE CUSTOMER INFO----------");
		System.out.print("이름(" + cust.getName() + ") :");
		cust.setName(scan.next());

		System.out.print("성별(" + cust.getGender() + ") :");
		cust.setGender(scan.next().charAt(0));

		System.out.print("이메일(" + cust.getEmail() + ") :");
		cust.setEmail(scan.next());

		System.out.print("출생년도(" + cust.getBirthYear() + ") :");
		cust.setBirthYear(scan.nextInt());

		insertLogData(cust.getCustomerNum(), 'u');
		
	}

	
	public static void printCustomerALL()
	{

		for(int i =0; i < custList.size(); i++)
		{
			System.out.println("==========CUSTOMER"+(i+1)+ " INFO================");
			System.out.println("이름 : " + custList.get(i).getName());
			System.out.println("성별 : " + custList.get(i).getGender());
			System.out.println("이메일 : " + custList.get(i).getEmail());
			System.out.println("출생년도 : " + custList.get(i).getBirthYear());
			
		}
		System.out.println("==============================================");
		

		
	}

	
	public static void printLogs()
	{
		System.out.println("고객번호\t업데이트 내역\t시간");
		for(int i =0; i < logList.size(); i++)
		{
			System.out.print(logList.get(i).getCustomer());
			switch(logList.get(i).getUpdate())
			{
			case 'i':
				System.out.print("\tINSERT\t");
				break;
			case 'u':
				System.out.print("\tUPDATE\t");
				break;
			case 'd':
				System.out.print("\tDELETE\t");
				break;
			}
			System.out.println(logList.get(i).getTime());
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	public static void loadCustomerData() {
		String fileName = "customer.data";
		
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(fileName);
			ois = new ObjectInputStream(fis);
			
			custList = (ArrayList<Customer>)ois.readObject();
		} catch (FileNotFoundException e) {
			System.out.println("파일이 존재하지 않습니다.");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			if(ois != null)
				try{ois.close();} catch (IOException e) { }
		}
	}

	public static void saveCustomerData() {
		String fileName = "customer.data";
		
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		
		try {
			fos = new FileOutputStream(fileName);
			oos = new ObjectOutputStream(fos);
			
			oos.writeObject(custList);
			System.out.println("고객 데이터가 저장됐습니다.");
		} catch (FileNotFoundException e) {
			System.out.println("파일이 존재하지 않습니다.");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			if(oos != null)
				try { oos.close(); } catch(Exception e){} 
		}
	}
	

}//end class