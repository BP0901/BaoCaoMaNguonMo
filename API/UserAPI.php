<?php
// Turn off all error reporting
error_reporting(0);

// the userdal file
require_once 'userdal.php';

// message to return
$message = array();

$dal = new UserDAL();

switch($_GET["action"])
{
	case 'getall':
		$message = $dal->getAll();
		break;
	
	case 'insert':
		$username = $_GET["username"];
		$password = $_GET["password"];
		$type = $_GET["type"];
		$result = $dal->insert($username, $password, $type);
		$message = ["message" => json_encode($result)];
		break;
		
	case 'delete':
		$username = $_GET["username"];
		$result = $dal->delete($username);
		$message = ["message" => json_encode($result)];
		break;
	
	case 'update':
		$username = $_GET["username"];
		$password = $_GET["password"];
		$result = $dal->update($username, $password);
		$message = ["message" => json_encode($result)];
		break;

	default:
		$message = ["message" => "Unknown method " . $_GET["action"]];
		break;
}

//The JSON message
header('Content-type: application/json; charset=utf-8');

//Clean (erase) the output buffer
ob_clean();

echo json_encode($message);

