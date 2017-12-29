package com.lsn.httpClient;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class JunitTest {

	/*@Test
	public void test() {
		fail("Not yet implemented");
	}*/
	@Test
	public void JsonToStr1(){
		ArrayList<sqlName> list = new ArrayList<>();
		//String str = "{\"result\":\"ok\",\"replys\":[{\"index\":\"1\",\"name\":\"lsn\",\"classes\":\"1\",\"gender\":\"1\",\"score\":\"95\"},{\"index\":\"2\",\"name\":\"lll\",\"classes\":\"1\",\"gender\":\"0\",\"score\":\"80\"},{\"index\":\"3\",\"name\":\"sss\",\"classes\":\"1\",\"gender\":\"0\",\"score\":\"50\"}]}";
		String str = "{\"result\":\"ok\",\"replys\":[{\"index\":\"1\",\"name\":\"lsn\",\"classes\":\"1\",\"gender\":\"1\",\"score\":\"95\"}]}";
		sqlName sql = new sqlName();
		sql.index = 1;
		sql.name = "lsn";
		sql.classes = 1;
		sql.gender = "Ů";
		sql.score = 95;
		list.add(sql);
		ArrayList<sqlName> list2 = new Client().JsonToStr(str);
		for(int i=0;i<list2.size();i++){
			if(list.get(i).index!=list2.get(i).index){
				fail("Not yet implemented");
				return;
			}
			if(!list.get(i).name.equals(list2.get(i).name)){
				fail("Not yet implemented");
				return;
			}
			if(list.get(i).classes!=list2.get(i).classes){
				fail("Not yet implemented");
				return;
			}
			if(!list.get(i).gender.equals(list2.get(i).gender)){
				fail("Not yet implemented");
				return;
			}
			if(list.get(i).score!=list2.get(i).score){
				fail("Not yet implemented");
				return;
			}
		}
		assertEquals(true,true);
	}
	@Test
	public void JsonToStr2(){
		ArrayList<sqlName> list = new ArrayList<>();
		list = null;
		String str = "{\"result\":\"bad\"}";
		assertEquals(list,new Client().JsonToStr(str));
	}
}
