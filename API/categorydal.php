<?php
// the dbconnection file
require_once 'dbconnection.php';

class CategoryDAL{

	public function getAll()
	{
		$dbConnection = new DBConnection();
		$conn = $dbConnection->getConnection();
		$query = 'SELECT * FROM categories';
		$list = array();
		$result = $conn->query($query);
		while ($row = $result->fetch_assoc())
		{
			$list[] = $row;
		}
		return $list;
	}
	
	
	function insert($catename, $pic)
	{
		$dbConnection = new DBConnection();
		$conn = $dbConnection->getConnection();
		$filename="IMG".rand().".jpg";
		file_put_contents("images/".$filename,base64_decode($pic));
		$query = "INSERT INTO categories (category, picture) VALUES ('$catename', '$filename')";
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
		$query = "DELETE FROM categories WHERE ID = '$id'";
		if ($conn->query($query) === TRUE) {
			return "Deleted successfully";
		  } else {
			return "Error: " . $sql . "<br>" . $conn->error;
		  }
	}
	

	function update($id ,$catename, $pic)
	{
		$dbConnection = new DBConnection();
		$conn = $dbConnection->getConnection();
		$query = "UPDATE categories SET category = '$catename', picture = '$pic'  WHERE ID = '$id'";
		if ($conn->query($query) === TRUE) {
			return "Updated successfully";
		  } else {
			return "Error: " . $sql . "<br>" . $conn->error;
		  }
	}
}

