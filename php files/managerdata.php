<?php
$servername="localhost";
$usernme="id2054814_abhishek";
$pswd="abhishekkgarg";
$dbname="id2054814_abhishek";

$empid = $_POST['empid'];
$startdate = $_POST['startdate'];
$status = $_POST['status'];



$empid1 = explode(',',$empid);

$startdate1 = explode(',',$startdate);

$status1 = explode(',',$status);


$response["data"]=array();


$con=mysqli_connect($servername,$usernme,$pswd,$dbname);

for($i = 0 ; $i < count($empid1) ; $i++)
{
    
$result=mysqli_query($con,"update leaves set status='$status1[$i]' where empid='$empid1[$i]' AND startdate='$startdate1[$i]'");

    
}

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