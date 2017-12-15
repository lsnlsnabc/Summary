package com.lsn.server;
import java.util.*;
public class CrazyiMap<K,V> {
	//创建一个线程安全的HashMap
	public Map<K,V> map = Collections.synchronizedMap(new HashMap<K,V>());
	//查看大小
	public synchronized int size(){
		return map.size();
	}
	//根据value删除指定项
	public synchronized void removeByValue(Object value){
		for(Object key : map.keySet()){
			if(map.get(key)==value){
				map.remove(key);
				break;
			}
		}
	}
	//获取所有value组成的Set集合
	public synchronized Set<V> valueSet(){
		Set<V> result = new HashSet<>();
		map.forEach((key , value) -> result.add(value));
		return result;
	}
	//根据value查找key
	public synchronized K getKeyByValue(V val){
		for(K key : map.keySet()){
			if(map.get(key)==val || map.get(key).equals(val)){
				return key;
			}
		}
		return null;
	}
	//实现put，不允许value重复
	public synchronized V put(K key,V value){
		for(V val : valueSet()){
			if(val.equals(value)&&val.hashCode()==value.hashCode()){
				throw new RuntimeException("实例中不允许有重复value");
			}
		}
		return map.put(key, value);
	}
}
