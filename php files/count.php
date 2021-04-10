<?php
$servername="localhost";
$usernme="id2054814_abhishek";
$pswd="abhishekkgarg";
$dbname="id2054814_abhishek";

$empid = $_POST['empid'];

$response["data"]=array();

$con=mysqli_connect($servername,$usernme,$pswd,$dbname);

$result=mysqli_query($con,"select empid from leaves where empid='$empid' AND status  = 'allow'");




if ($result->num_rows < 10)
{
 $response["success"]=2;
echo json_encode($response);



}else
{
 $response["success"]=0;
echo json_encode($response);

}

?>