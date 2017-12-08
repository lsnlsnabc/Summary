package com.lsn.client;
import java.util.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
public class Client {
	private static final int SERVER_PORT = 8888;
	private static final String IP = "192.168.31.196";
	private static Socket socket;
	private static PrintStream ps;
	private static BufferedReader brServer;
	private static BufferedReader keyIn;
	public void init(){
		try{
			//��ʼ������������
			keyIn = new BufferedReader(new InputStreamReader(System.in));
			socket = new Socket(IP,SERVER_PORT);
			ps = new PrintStream(socket.getOutputStream());
			brServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String tip = "";
			System.out.println("�����û�����");
			String userName = null;
			String result = null;
			while(true){
				if(userName==null&&(userName=keyIn.readLine())!=null){
					ps.println(CrazyitProtocol.USER_ROUND+userName+CrazyitProtocol.USER_ROUND);
				}
				if(userName!=null&&result==null){
					result = brServer.readLine();
					if(result.equals(CrazyitProtocol.NAME_REP)){
						System.out.println("�û����ظ��������������û�����");
						result = null;
						userName = null;
						continue;
					}
					if(result.equals(CrazyitProtocol.LOGIN_SUCCESS)){
						Date date = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");
						System.out.println("��ǰʱ�䣺"+dateFormat.format(date));
						System.out.println(userName+"��¼�ɹ���");
						break;
					}
				}
			}
		}catch(UnknownHostException e){
			//e.printStackTrace();
			System.out.println("�Ҳ���Զ�̷���������ȷ���������Ѿ�������");
			closeRs();
			System.exit(1);
		}catch(IOException ex){
			System.out.println("�����쳣�������µ�¼��");
			closeRs();
			System.exit(1);
		}
		try {
			new Thread(new ClientThread(brServer)).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void closeRs(){
		try{
			if(keyIn!=null){
				ps.close();
			}
			if(brServer!=null){
				ps.close();
			}
			if(ps!=null){
				ps.close();
			}
			if(socket!=null){
				keyIn.close();
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	public void readAndSend(){
		try{
			String line = null;
			while((line=keyIn.readLine().trim())!=null){
				//������͵���Ϣ����ð�ţ�����//��ͷ������Ϊ�뷢��˽��
				if(line.indexOf(":")>0&&line.startsWith("//")){
					line = line.substring(2);
					//System.out.println("****"+line);
					ps.println(CrazyitProtocol.PRIVATE_ROUND+line.split(":")[0]+CrazyitProtocol.SPLIT_SIGN+line.split(":")[1]+CrazyitProtocol.PRIVATE_ROUND);
				}else{
					ps.println(CrazyitProtocol.MSG_ROUND+line+CrazyitProtocol.MSG_ROUND);
				}
			}
		}catch(IOException ex){
			System.out.println("����ͨ���쳣�������µ�¼��");
			closeRs();
			System.exit(1);
		}
	}
	public static void main(String[] args) throws IOException{
		/*Socket socket = new Socket("192.168.31.196",8888);
		new Thread(new ClientThread(socket)).start();
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String line = br.readLine();
		System.out.println(line);
		br.close();
		socket.close();
		PrintStream ps = new PrintStream(socket.getOutputStream());
		String line = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while((line=br.readLine())!=null){
			ps.println(line);
		}*/
		Client client = new Client();
		client.init();
		client.readAndSend();
	}
}