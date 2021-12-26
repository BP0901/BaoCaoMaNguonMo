<?php
// the dbconnection file
require_once 'dbconnection.php';

class FoodDAL{

	public function getAll()
	{
		$dbConnection = new DBConnection();
		$conn = $dbConnection->getConnection();
		$query = 'SELECT * FROM foods';
		$list = array();
		$result = $conn->query($query);
		while ($row = $result->fetch_assoc())
		{
			$list[] = $row;
		}
		return $list;
	}
	
	
	function insert($name, $price, $discount, $picture, $category)
	{
		$dbConnection = new DBConnection();
		$conn = $dbConnection->getConnection();
		$query = "INSERT INTO foods (namefood, price, discount, picture, category) VALUES ('$name', '$price', '$discount', '$picture','$category')";
		if ($conn->query($query) === TRUE) {
			return "Inserted successfully";
		  } else {
			return "Error: " . $sql . "<br>" . $conn->error;
		  }
	}
	
	
	function delete($id)
	{
		$dbConnection = new DBConnection();
		$conn = $dbConnection->getConnection();
		$query = "DELETE FROM foods WHERE id  = '$id'";
		if ($conn->query($query) === TRUE) {
			return "Deleted successfully";
		  } else {
			return "Error: " . $sql . "<br>" . $conn->error;
		  }
	}
	

	function update($id, $name, $price, $discount, $picture, $category)
	{
		$dbConnection = new DBConnection();
		$conn = $dbConnection->getConnection();
		$query = "UPDATE foods SET namefood = '$name', price = '$price', discount='$discount', picture='$picture', category='$category' WHERE id = '$id'";
		if ($conn->query($query) === TRUE) {
			return "Updated successfully";
		  } else {
			return "Error: " . $sql . "<br>" . $conn->error;
		  }
	}
}

