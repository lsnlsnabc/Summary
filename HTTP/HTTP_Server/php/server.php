<?php
$db_servername = 'localhost';
$db_adminname = 'root';
$db_pwd = 'root';
$db_name = 'lsnTest';
$table_name =  'InformationOfClass';

function fetchTableInfo($conn){
	global $table_name;
	$queryRequest = "select * from $table_name;";
	$conn->query("set names 'utf8'");
	$conn->query("set character_set_client=utf8");
	$conn->query("set character_set_results=utf8");
	$result = $conn->query($queryRequest);
	if(!$result || $result->num_rows < 1){
		$replyAll = new ReplyAll();
		$replyAll->result = 'bad';
		return json_encode($replyAll);
	}else{
		$replyAll = new ReplyAll();
		$replyAll->result = 'ok';
		$replys = array();
		while($res=$result->fetch_assoc()){
			#$reply->result = 'ok';
			$reply = new Reply();
			$reply->index = $res['index'];
			$reply->name = $res['name'];
			$reply->classes = $res['class'];
			$reply->gender = $res['gender'];
			$reply->score = $res['score'];
			array_push($replys,$reply);
		}
		$replyAll->replys = $replys;
		return json_encode($replyAll);
	}
}
class Reply{
	#public $result;
	public $index;
	public $name;
	public $classes;
	public $gender;
	public $score;
}
class ReplyAll{
	public $result;
	public $replys;
}
?>