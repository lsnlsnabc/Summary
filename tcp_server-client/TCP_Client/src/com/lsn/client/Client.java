package com.lsn.client;
import java.util.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
public class Client {
	private static final int SERVER_PORT = 8888;
	private static final String IP = "10.96.78.42";
	private static Socket socket;
	private static PrintStream ps;
	private static BufferedReader brServer;
	private static BufferedReader keyIn;
	public static HashMap<Integer,String> list;
	public void init(){//int name
		try{
			//初始化键入输入流
			keyIn = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("连接中……");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//设定连接超时
			socket = new Socket();
			SocketAddress sa = new InetSocketAddress(IP,SERVER_PORT);
			socket.connect(sa, 10000);
			ps = new PrintStream(socket.getOutputStream());
			brServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String tip = "";
			System.out.println("输入用户名：");
			String userName = null;
			//String userName = name+"";
			String result = null;
			while(true){
				/*if(userName!=null){
					ps.println(CrazyitProtocol.USER_ROUND+userName+CrazyitProtocol.USER_ROUND);
				}*/
				if(userName==null&&(userName=keyIn.readLine())!=null){
					ps.println(CrazyitProtocol.USER_ROUND+userName+CrazyitProtocol.USER_ROUND);
				}
				if(userName!=null&&result==null){
					result = brServer.readLine();
					if(result.equals(CrazyitProtocol.NAME_REP)){
						System.out.println("用户名重复！请重新输入用户名：");
						result = null;
						userName = null;
						continue;
					}
					if(result.equals(CrazyitProtocol.LOGIN_SUCCESS)){
						Date date = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
						System.out.println("当前时间："+dateFormat.format(date));
						System.out.println(userName+"登录成功！");
						list = new HashMap<>();
						break;
					}
					if(result.equals(CrazyitProtocol.MAXPOOL)){
						System.out.println("上线人数已满，请稍后登录！");
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						closeRs();
						System.exit(1);
					}
				}
			}
			
		}catch(SocketTimeoutException ste){
			System.out.println("连接超时！");
			closeRs();
			System.exit(1);
		}catch(UnknownHostException e){
			//e.printStackTrace();
			System.out.println("找不到远程服务器，请确定服务器已经启动！");
			closeRs();
			System.exit(1);
		}catch(IOException ex){
			System.out.println("网络异常！请重新登录！");
			closeRs();
			System.exit(1);
		}
		try {
			new Thread(new ClientThread(brServer)).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
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
			//ex.printStackTrace();
		}
	}
	public void readAndSend(){
		try{
			String line = null;
			while((line=keyIn.readLine().trim())!=null){
				int num = list.size();
				list.put(num,line);
				//如果发送的信息中有冒号，且以//开头，则认为想发送私聊
				if(line.indexOf(":")>0&&line.startsWith("//")){
					line = line.substring(2);
					//System.out.println("****"+line);
					ps.println(CrazyitProtocol.PRIVATE_ROUND+num+CrazyitProtocol.SPLIT_SIGN+line.split(":")[0]+CrazyitProtocol.SPLIT_SIGN+line.split(":")[1]+CrazyitProtocol.PRIVATE_ROUND);
					new Thread(new TimeOut(num)).start();
				}else{
					ps.println(CrazyitProtocol.MSG_ROUND+num+CrazyitProtocol.SPLIT_SIGN+line+CrazyitProtocol.MSG_ROUND);
					new Thread(new TimeOut(num)).start();
				}
			}
		}catch(IOException ex){
			System.out.println("网络通信异常！请重新登录！");
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
		/*for(int i=0;i<1000;i++){
			Client client = new Client();
			client.init(i);
			//client.readAndSend();
		}*/
	}
}
