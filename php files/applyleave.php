<?php
$servername="localhost";
$usernme="id2054814_abhishek";
$pswd="abhishekkgarg";
$dbname="id2054814_abhishek";

$empid = $_POST['empid'];
$startdate = $_POST['startdate'];
$enddate = $_POST['enddate'];
$reason = $_POST['reason'];
$days = $_POST['days'];
$manager = $_POST['manager'];

$response["data"]=array();

$con=mysqli_connect($servername,$usernme,$pswd,$dbname);

$result=mysqli_query($con,"insert into leaves values('$empid','$startdate','$enddate','$reason','$days','','$manager')");

if ($result)
{
   
$response["success"]=1;
echo json_encode($response);
} else
{
 $response["success"]=0;
echo json_encode($response);

}
?>