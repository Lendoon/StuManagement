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
	public boolean isLogined = false;//�Ƿ��ѵ�¼   private���Σ����Ը�Ϊpublic
	
	//��ӡ��ӭ��� ���빦�ܲ˵�
	private void welcome() {
		System.out.println("��ã���ӭ��¼ѧ������ϵͳ��");
		this.menu();
	}
	
	//���ܲ˵�
	private void menu() {
		System.out.println("��ѡ����Ӧ�Ĺ��ܣ�\n[1]=�û���¼ [2]=�û�ע�� [3]=��Ϣ��ѯ [4]=�˳�ϵͳ");
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
	
	//�����û�����ѡ���ܣ�������Ӧ�Ĺ���
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
	

	//��¼   private���Σ����Ը�Ϊpublic
	public void login() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(new File("./loginData.txt")));
		String string = null;
		System.out.println("�������û��������루ʹ�ÿո�ָ�����");
		Scanner sc = new Scanner(System.in);
		String[] loginInfo = sc.nextLine().split(" ");
		while ((string = br.readLine())!=null) {
			String[] dataInfo = string.split(" ");
			if (loginInfo[0].equals(dataInfo[0])){
				if (loginInfo[1].equals(dataInfo[1])) {
					System.out.println("��¼�ɹ���\n�û�����"+dataInfo[0]+" �ǳƣ�"+dataInfo[2]);
					isLogined=true;
//					this.menu();
				}else{
					System.out.println("������󣡷���������");
//					this.menu();
				}
			}
		}
		br.close();
		System.out.println("�Ҳ����û�������������");
		this.menu();
	}
	
	//ע��
	private void registration() throws Exception {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		int i = 0;
		String data = null;
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("./loginData.txt"),true));
		if (i==0) {
			System.out.println("�������û���������6��8λ����Ҫ�����ֶ�����ѯѧ����Ϣ����");
			
						String isdata = null;
			while ((isdata  = br.readLine())!=null) {
				if (Pattern.matches("^\\w{6,8}$",isdata)) {
					data = isdata;
					i++;
					break;
				} else {
					System.out.println("�û���������Ҫ������������");
				}
			}
		}
		if (i==1) {
			System.out.println("���������루����6��8λ����");
			String isdata = null;
			while ((isdata  = br.readLine())!=null) {
				if (Pattern.matches("^\\w{6,8}$",isdata)) {
					data =  data+" "+isdata;
					i++;
					break;
				} else {
					System.out.println("���벻����Ҫ������������");
				}
			}
		}
		if (i==2) {
			System.out.println("������������");
			data = data+" "+br.readLine();
			i++;
		}
		if (i==3) {
			System.out.println("������ѧУ��");
			data = data+" "+br.readLine();
			i++;
		}
		if (i==4) {
			System.out.println("������רҵ��");
			data = data+" "+br.readLine();
			i++;
		}
		if (i==5) {
			System.out.println("������סַ��");
			data = data+" "+br.readLine()+"\n";
			i++;
		}
		if(i==6) {
			System.out.println("��Ϣ������ɣ��Ƿ����ע�᣿Y/N��ѡ��Yע�ᣬѡ��N���������棩��");
			String isreg = br.readLine();
			while (isreg  != null) {
				if (isreg.equalsIgnoreCase("Y")) {
					bw.write(data);
//					bw.newLine();
					bw.flush();
					System.out.println("ע��ɹ�");
					this.menu();
				}else if (isreg.equalsIgnoreCase("N")) {
					this.menu();
				} else {
					System.out.println("��ʽ����ȷ��������Y/N��ѡ��Yע�ᣬѡ��N���������棩");
					isreg = br.readLine();
				}
			}
		}
		bw.close();
		br.close();
		isr.close();
	}
	
	//����
	public void search() throws Exception {
		if (!isLogined) {
			System.out.println("���¼��ִ�д˲���");
			this.menu(1);
		}
		System.out.println("������Ҫ��ѯ���ǳƣ�");
		Scanner sc = new Scanner(System.in);
		String search = sc.next();
		BufferedReader br = new BufferedReader(new FileReader(new File("./loginData.txt")));
		String dataList = null;
		int i = 1;
		boolean isSearched = false;
		//���������û�
		while ((dataList=br.readLine())!=null) {
			String[] data = dataList.split(" ");
			//����ǲ�������ѯ���û�
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
					System.out.println("���\t�û���\t\t����\t�ǳ�\t\tѧУ\tרҵ\tסַ");
				}
				while (iter.hasNext()) {
					System.out.print(iter.next()+"\t");
				}
				searchedList.clear();
				System.out.println();
			}
		}
		if (isSearched) {
			System.out.println("[1]=��Ϣ�޸� [������]=������һ��");
			Scanner sc1 = new Scanner(System.in);
			if (sc1.nextInt()==1) {
				this.modify();
			} else {
				this.menu();
			}
		} else {
			System.out.println("���޴���!");
			this.menu();
		}
		
	}
	//�޸�
	public void modify() throws Exception {
		System.out.println("������Ҫ�޸ĵ���ţ�");
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			int serialNum = sc.nextInt();
			if (serialNum-1<map.size()) {
				System.out.println("����������Ϣ����޸�");
//				String newDataLine = sc.nextLine();
//				RandomAccessFile raf = new RandomAccessFile(new File("./loginData.txt"), "rw");
//				
//				raf.close();
//				String[] newData = newDataLine.split(" ");
				System.out.println("������δ����");
				
			} else {
				System.out.println("���������Ų�����Ҫ�����������룺");
			}
		}
		this.menu();
	}

	//�˳�
	private void exit() {
		System.out.println("�˳�ϵͳ������ֹͣ");
		System.exit(0);
	}
	
	public static void main(String[] args) {
		new StuManageControl().welcome();
	}
}
