package com.lsn.httpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Client {
	public static void main(String[] args){
		try {
			URL url = new URL("http://192.168.31.144:8011/select.php");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			InputStream input = conn.getInputStream();
			StringBuilder sb = new StringBuilder();
			int resLen = 0;
			byte[] res = new byte[1024];
			while((resLen=input.read(res))!=-1){
				sb.append(new String(res,0,resLen));
			}
			System.out.println(sb);
			Client client = new Client();
			ArrayList<sqlName> list = client.JsonToStr(sb.toString());
			client.showContent(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void showContent(ArrayList<sqlName> list){
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				System.out.println("编号："+list.get(i).index+" 姓名："+list.get(i).name+" 班级："+list.get(i).classes+" 性别："+list.get(i).gender+" 成绩："+list.get(i).score);
			}
		}else{
			System.out.println("no data!");
		}
	}
	public ArrayList<sqlName> JsonToStr(String str){
		ArrayList<sqlName> list = new ArrayList<>();
		JSONObject object = JSONObject.fromObject(str);
		if(object.get("result").equals("ok")){
			JSONArray array = object.getJSONArray("replys");
			for(int i=0;i<array.size();i++){
				sqlName packge = new sqlName();
				packge.index = Integer.parseInt((String)array.getJSONObject(i).get("index"));
				packge.name = (String)array.getJSONObject(i).get("name");
				packge.classes = Integer.parseInt((String)array.getJSONObject(i).get("classes"));
				String gender;
				if(Integer.parseInt((String)array.getJSONObject(i).get("gender"))==1){
					gender = "女";
				}else{
					gender = "男";
				}
				packge.gender = gender;
				packge.score = Integer.parseInt((String)array.getJSONObject(i).get("score"));
				list.add(packge);
			}
			return list;
		}else{
			return null;
		}
	}
}
