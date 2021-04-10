<?php
$servername="localhost";
$usernme="id2054814_abhishek";
$pswd="abhishekkgarg";
$dbname="id2054814_abhishek";

$empid = $_POST['empid'];
$pass = $_POST['pass'];


$response["data"]=array();

$con=mysqli_connect($servername,$usernme,$pswd,$dbname);


$result=mysqli_query($con,"SELECT * FROM users WHERE empid='$empid' AND password='$pass' ");


if ($result->num_rows > 0)
{
    // output data of each row
    while($row = $result->fetch_assoc()) 
{
       $data=array();
  
       $data['name']=$row['name'];
       $data['email']=$row['mail'];
       $data['type']=$row['type'];
       $data['mname']=$row['mname'];
       $data['empid']=$row['manager'];
       
       array_push($response["data"],$data);
            
}
$response["success"]=1;
echo json_encode($response);
} else 
{
 $response["success"]=0;
$response["message"]="hoooo, Did not match !";
echo json_encode($response);

}
?>