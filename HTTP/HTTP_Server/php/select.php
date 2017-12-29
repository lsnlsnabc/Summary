<?php

require 'server.php';

$conn = new mysqli($db_servername,$db_adminname,$db_pwd,$db_name);
if(!$conn || $conn->connect_error){
	echo 'fail to connect to SQL'; 
}else{
	#$json = $_GET['json'];
	#$obj = json_decode($json);
	header('Content-type: application/json; charset=UTF-8');

	echo fetchTableInfo($conn);
}
$conn->close();
?>
