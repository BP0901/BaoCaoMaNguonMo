<?php
require_once 'dbconnection.php';
$dbConnection = new DBConnection();
$name=$_POST['t1'];
$img=$_POST['upload'];
// $filename="IMG".rand().".jpg";
// 	   file_put_contents("images/".$filename,base64_decode($img));

// $query = "INSERT INTO categories (category, picture) VALUES ('$name', '$filename')";
// if ($conn->query($query) === TRUE) {
// 	echo "Inserted successfully";
//   } else {
// 	echo "Error: " . $sql . "<br>" . $conn->error;
//   }
?>
