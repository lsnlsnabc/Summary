package com.lsn.server;
import java.util.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
public class Server {
	//public static List<Socket> socketList = new ArrayList<>();
	public static final int SERVER_PORT = 8888;
	public static CrazyiMap<String,PrintStream> clients = new CrazyiMap<>();
	public void init(){
		//try括号里面的变量将自动关闭，无须serverSocket.close()
		try(ServerSocket serverSocket = new ServerSocket(SERVER_PORT)){
			System.out.println("服务器已启动！");
			while(true){
				Socket socket = serverSocket.accept();
				new Thread(new ServerThread(socket)).start();
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	public static void main(String[] args) throws IOException{
		/*ServerSocket serverSocket = new ServerSocket(8888);
		while(true){
			Socket socket = serverSocket.accept();
			socketList.add(socket);
			System.out.println("add a socket,size:"+socketList.size());
			PrintStream ps = new PrintStream(socket.getOutputStream());
			ps.println("Hello,this is a server!");
			ps.close();
			socket.close();
			new Thread(new ServerThread(socket)).start();
			String line = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			while((line=br.readLine())!=null){
				ps.println(line);
			}
		}*/
		Server server = new Server();
		server.init();
	}
}
