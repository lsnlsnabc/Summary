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
				System.out.println(content);
			}
		}catch(Exception ex){
			ex.printStackTrace();
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
