<?php
// Turn off all error reporting
error_reporting(0);

require_once 'categorydal.php';

// message to return
$message = array();

$dal = new CategoryDAL();

switch($_GET["action"])
{
	case 'getall':
		$message = $dal->getAll();
		break;
	
	case 'insert':
		$catename = $_GET["catename"];
		$pic = $_GET["picture"];
		$result = $dal->insert($catename, $pic);
		$message = ["message" => json_encode($result)];
		break;
		
	case 'delete':
		$id = $_GET["ID"];
		$result = $dal->delete($id);
		$message = ["message" => json_encode($result)];
		break;
	
	case 'update':
        $id = $_GET["ID"];
		$catename = $_GET["catename"];
		$pic = $_GET["picture"];
		$result = $dal->update($id ,$catename, $pic);
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

