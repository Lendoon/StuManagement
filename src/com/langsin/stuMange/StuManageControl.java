package com.langsin.stuMange;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class StuManageControl {
	private List<String> searchedList = new ArrayList<String>();
	private Map<String, List<String>> map = new HashMap<String, List<String>>();
	public boolean isLogined = false;//是否已登录   private修饰，测试改为public
	
	//打印欢迎语句 进入功能菜单
	private void welcome() {
		System.out.println("你好，欢迎登录学生管理系统！");
		this.menu();
	}
	
	//功能菜单
	private void menu() {
		System.out.println("请选择相应的功能：\n[1]=用户登录 [2]=用户注册 [3]=信息查询 [4]=退出系统");
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			int menu = scanner.nextInt();
			try {
				this.menu(menu);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//根据用户输入选择功能，进入相应的功能
	private void menu(int menu) throws Exception{
		switch (menu) {
		case 1:
			this.login();
			break;
		case 2:
			this.registration();
			break;
		case 3:
			this.search();
			break;
		case 4:
			this.exit();
			break;
		default:
			break;
		}
	}
	

	//登录   private修饰，测试改为public
	public void login() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(new File("./loginData.txt")));
		String string = null;
		System.out.println("请输入用户名和密码（使用空格分隔）：");
		Scanner sc = new Scanner(System.in);
		String[] loginInfo = sc.nextLine().split(" ");
		while ((string = br.readLine())!=null) {
			String[] dataInfo = string.split(" ");
			if (loginInfo[0].equals(dataInfo[0])){
				if (loginInfo[1].equals(dataInfo[1])) {
					System.out.println("登录成功！\n用户名："+dataInfo[0]+" 昵称："+dataInfo[2]);
					isLogined=true;
//					this.menu();
				}else{
					System.out.println("密码错误！返回主界面");
//					this.menu();
				}
			}
		}
		br.close();
		System.out.println("找不到用户，返回主界面");
		this.menu();
	}
	
	//注册
	private void registration() throws Exception {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		int i = 0;
		String data = null;
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("./loginData.txt"),true));
		if (i==0) {
			System.out.println("请输入用户名（长度6—8位，主要靠此字段来查询学生信息）：");
			
						String isdata = null;
			while ((isdata  = br.readLine())!=null) {
				if (Pattern.matches("^\\w{6,8}$",isdata)) {
					data = isdata;
					i++;
					break;
				} else {
					System.out.println("用户名不符合要求，请重新输入");
				}
			}
		}
		if (i==1) {
			System.out.println("请输入密码（长度6—8位）：");
			String isdata = null;
			while ((isdata  = br.readLine())!=null) {
				if (Pattern.matches("^\\w{6,8}$",isdata)) {
					data =  data+" "+isdata;
					i++;
					break;
				} else {
					System.out.println("密码不符合要求，请重新输入");
				}
			}
		}
		if (i==2) {
			System.out.println("请输入姓名：");
			data = data+" "+br.readLine();
			i++;
		}
		if (i==3) {
			System.out.println("请输入学校：");
			data = data+" "+br.readLine();
			i++;
		}
		if (i==4) {
			System.out.println("请输入专业：");
			data = data+" "+br.readLine();
			i++;
		}
		if (i==5) {
			System.out.println("请输入住址：");
			data = data+" "+br.readLine()+"\n";
			i++;
		}
		if(i==6) {
			System.out.println("信息输入完成，是否进行注册？Y/N（选择Y注册，选择N返回主界面）：");
			String isreg = br.readLine();
			while (isreg  != null) {
				if (isreg.equalsIgnoreCase("Y")) {
					bw.write(data);
//					bw.newLine();
					bw.flush();
					System.out.println("注册成功");
					this.menu();
				}else if (isreg.equalsIgnoreCase("N")) {
					this.menu();
				} else {
					System.out.println("格式不正确，请输入Y/N（选择Y注册，选择N返回主界面）");
					isreg = br.readLine();
				}
			}
		}
		bw.close();
		br.close();
		isr.close();
	}
	
	//查找
	public void search() throws Exception {
		if (!isLogined) {
			System.out.println("请登录后执行此操作");
			this.menu(1);
		}
		System.out.println("请输入要查询的昵称：");
		Scanner sc = new Scanner(System.in);
		String search = sc.next();
		BufferedReader br = new BufferedReader(new FileReader(new File("./loginData.txt")));
		String dataList = null;
		int i = 1;
		boolean isSearched = false;
		//遍历所有用户
		while ((dataList=br.readLine())!=null) {
			String[] data = dataList.split(" ");
			//检查是不是所查询的用户
			if (search.equals(data[2])) {
				searchedList.add(String.valueOf(i));
				i++;
				isSearched = true;
				for (String str : data) {
					searchedList.add(str);
				}
				map.put(String.valueOf(i-1), searchedList);
//				searchedList.clear();
//				Iterator<String> iter = map.get(String.valueOf(i-1)).iterator();
//				System.out.println(map.size());
				Iterator<String> iter = searchedList.iterator();
				if (i==2) {
					System.out.println("序号\t用户名\t\t密码\t昵称\t\t学校\t专业\t住址");
				}
				while (iter.hasNext()) {
					System.out.print(iter.next()+"\t");
				}
				searchedList.clear();
				System.out.println();
			}
		}
		if (isSearched) {
			System.out.println("[1]=信息修改 [其它键]=返回上一级");
			Scanner sc1 = new Scanner(System.in);
			if (sc1.nextInt()==1) {
				this.modify();
			} else {
				this.menu();
			}
		} else {
			System.out.println("查无此人!");
			this.menu();
		}
		
	}
	//修改
	public void modify() throws Exception {
		System.out.println("请输入要修改的序号：");
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			int serialNum = sc.nextInt();
			if (serialNum-1<map.size()) {
				System.out.println("请输入新信息完成修改");
//				String newDataLine = sc.nextLine();
//				RandomAccessFile raf = new RandomAccessFile(new File("./loginData.txt"), "rw");
//				
//				raf.close();
//				String[] newData = newDataLine.split(" ");
				System.out.println("功能尚未完善");
				
			} else {
				System.out.println("您输入的序号不符合要求，请重新输入：");
			}
		}
		this.menu();
	}

	//退出
	private void exit() {
		System.out.println("退出系统，程序停止");
		System.exit(0);
	}
	
	public static void main(String[] args) {
		new StuManageControl().welcome();
	}
}
