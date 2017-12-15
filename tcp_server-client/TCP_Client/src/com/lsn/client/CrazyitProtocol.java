package com.lsn.client;

public interface CrazyitProtocol {
	//定义协议字符串长度
	int PROTOCOL_LEN = 2;
	//协议字符串
	String MSG_ROUND = "&&";
	String USER_ROUND = "@@";
	String LOGIN_SUCCESS = "1";
	String NAME_REP = "-1";
	String PRIVATE_ROUND = "[[";
	String SPLIT_SIGN = ";";
	String MAXPOOL = "0";
	String SEND_SUCCESS = "2";
	String SEND_ROUND = "%%";
}
