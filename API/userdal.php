<?php
// the dbconnection file
require_once 'dbconnection.php';

class UserDAL{
	/**
	 * Lấy danh user
	 *
	 * @param none
	 * @return array[] Danh sách user
	 */
	public function getAll()
	{
		$dbConnection = new DBConnection();
		$conn = $dbConnection->getConnection();
		$query = 'SELECT * FROM users';
		$list = array();
		$result = $conn->query($query);
		while ($row = $result->fetch_assoc())
		{
			$list[] = $row;
		}
		return $list;
	}
	
	/**
	 * Thêm 1 user vào CSDL
	 *
	 * @param string username	username
	 * @param string pass	password
	 * @param int type	loại tài khoản
	 * @return true/false	Kết quả thực hiện câu sql
	 */
	function insert($username, $pass, $type)
	{
		$dbConnection = new DBConnection();
		$conn = $dbConnection->getConnection();
		$query = "INSERT INTO users (username, pass, accounttype) VALUES ('$username', '$pass', '$type')";
		if ($conn->query($query) === TRUE) {
			return "Inserted successfully";
		  } else {
			return "Error: " . $sql . "<br>" . $conn->error;
		  }
	}
	
	/**
	 * Xóa 1 user
	 *
	 * @param string username	username
	 * @return true/false	Kết quả thực hiện câu sql
	 */
	function delete($username)
	{
		$dbConnection = new DBConnection();
		$conn = $dbConnection->getConnection();
		$query = "DELETE FROM users WHERE username = '$username'";
		if ($conn->query($query) === TRUE) {
			return "Deleted successfully";
		  } else {
			return "Error: " . $sql . "<br>" . $conn->error;
		  }
	}
	
	/**
	 * Cập nhật 1 user
	 *
	 * @param string username	username
	 * @param string pass	password
	 * @param int type	loại tài khoản
	 * @return true/false	Kết quả thực hiện câu sql
	 */
	function update($username, $pass)
	{
		$dbConnection = new DBConnection();
		$conn = $dbConnection->getConnection();
		$query = "UPDATE users SET pass = '$pass' WHERE username = '$username'";
		if ($conn->query($query) === TRUE) {
			return "Updated successfully";
		  } else {
			return "Error: " . $sql . "<br>" . $conn->error;
		  }
	}
}

