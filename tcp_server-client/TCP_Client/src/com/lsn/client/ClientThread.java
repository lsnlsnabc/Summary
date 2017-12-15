package com.lsn.client;
import java.net.*;
import java.io.*;
public class ClientThread implements Runnable{
	BufferedReader br = null;
	public ClientThread(BufferedReader br) throws IOException{
		this.br = br;
	}
	@Override
	public void run(){
		// TODO Auto-generated method stub
		try{
			String content = null;
			while((content=br.readLine())!=null){
				if(content.startsWith(CrazyitProtocol.SEND_ROUND)&&content.endsWith(CrazyitProtocol.SEND_ROUND)){
					content = getRealMsg(content);
					System.out.println(Client.list.get(Integer.parseInt(content))+" success!");
					Client.list.remove(Integer.parseInt(content));
				}else{
					System.out.println(content);
				}
			}
		}catch(Exception ex){
			System.out.println("服务器已断开！");
			System.exit(1);
			//ex.printStackTrace();
		}finally{
			try{
				if(br!=null){
					br.close();
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
	}

	private String getRealMsg(String line){
		return line.substring(CrazyitProtocol.PROTOCOL_LEN,line.length()-CrazyitProtocol.PROTOCOL_LEN);
	}
}
