<?php
// Turn off all error reporting
error_reporting(0);

require_once 'fooddal.php';

// message to return
$message = array();

$dal = new FoodDAL();

switch($_GET["action"])
{
	case 'getall':
		$message = $dal->getAll();
		break;
	
	case 'insert':
        $name = $_GET["name"];
        $price = $_GET["price"]; 
        $discount = $_GET["discount"]; 
        $picture = $_GET["picture"]; 
        $category = $_GET["category"];
		$result = $dal->insert($name, $price, $discount, $picture, $category);
		$message = ["message" => json_encode($result)];
		break;
		
	case 'delete':
		$id = $_GET["id"];
		$result = $dal->delete($id);
		$message = ["message" => json_encode($result)];
		break;
	
	case 'update':
        $id = $_GET["id"];
		$name = $_GET["name"];
        $price = $_GET["price"]; 
        $discount = $_GET["discount"]; 
        $picture = $_GET["picture"]; 
        $category = $_GET["category"];
		$result = $dal->update($id, $name, $price, $discount, $picture, $category);
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

