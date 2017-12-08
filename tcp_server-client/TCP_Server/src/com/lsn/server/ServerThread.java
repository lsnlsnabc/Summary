package com.lsn.server;

import java.net.Socket;
import java.io.*;
public class ServerThread implements Runnable{
	//���嵱ǰ�����Socket
	private Socket socket;
	BufferedReader br = null;
	PrintStream ps = null;
	public ServerThread(Socket socket) throws IOException{
		this.socket = socket;
	}
	public void run(){
		try{
			//��ʼ����socket��Ӧ��������
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			ps = new PrintStream(socket.getOutputStream());
			String line = null;
			//ѭ����ȡ�ͻ��˷��͵�����
			while((line=br.readLine().trim())!=null){
				//���������û���¼���û���
				if(line.startsWith(CrazyitProtocol.USER_ROUND)&&line.endsWith(CrazyitProtocol.USER_ROUND)){
					String userName = getRealMsg(line).trim();
					if(Server.clients.map.containsKey(userName)){
						System.out.println("���û����Ѵ���");
						ps.println(CrazyitProtocol.NAME_REP);
					}else{
						System.out.println(userName+"��¼�ɹ���");
						ps.println(CrazyitProtocol.LOGIN_SUCCESS);
						Server.clients.put(userName, ps);
					}
				}else if(line.startsWith(CrazyitProtocol.PRIVATE_ROUND)&&line.endsWith(CrazyitProtocol.PRIVATE_ROUND)){
					//˽����Ϣ
					String userAndMsg = getRealMsg(line);
					String user = userAndMsg.split(CrazyitProtocol.SPLIT_SIGN)[0];
					String msg = userAndMsg.split(CrazyitProtocol.SPLIT_SIGN)[1];
					Server.clients.map.get(user).println(Server.clients.getKeyByValue(ps)+"���Ķ���˵�� "+msg);
				}else{
					//������Ϣ
					String msg = getRealMsg(line);
					for(PrintStream psClient : Server.clients.valueSet()){
						psClient.println("��������"+Server.clients.getKeyByValue(ps)+"˵�� "+getRealMsg(line));
					}
				}
				/*System.out.println("receive message!");
				for(int i=0;i<Server.socketList.size();i++){
					PrintStream ps = new PrintStream(socket.getOutputStream());
					ps.println(content);
				}
				System.out.println(content);*/
			}
		}catch(IOException ex){
			System.out.println(Server.clients.getKeyByValue(ps)+"���ߣ�");
			Server.clients.removeByValue(ps);
			System.out.println("����������"+Server.clients.map.size());
			try{
				if(br!=null){
					br.close();
				}
				if(ps!=null){
					ps.close();
				}
				if(socket!=null){
					socket.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	private String getRealMsg(String line){
		return line.substring(CrazyitProtocol.PROTOCOL_LEN,line.length()-CrazyitProtocol.PROTOCOL_LEN);
	}
	//�����ȡ�ͻ������ݵķ���
	/*private String readFromClient(){
		try{
			return br.readLine();
		}catch(Exception ex){
			Server.socketList.remove(socket);
		}
		return null;
	}*/
}
