<?php
$servername="localhost";
$usernme="id2054814_abhishek";
$pswd="abhishekkgarg";
$dbname="id2054814_abhishek";

$response["data"]=array();

$email=$_POST['email'];
$pass=$_POST['pass'];

$con=mysqli_connect($servername,$usernme,$pswd,$dbname);
$result=mysqli_query($con,"select * from users where mail='$email' AND password='$pass'");
if ($result->num_rows > 0)
{
    // output data of each row
    while($row = $result->fetch_assoc()) 
{
       $data=array();
       $data['name']=$row['name'];
       array_push($response["data"],$data);
            
}
$response["success"]=1;
echo json_encode($response);
} else 
{
 $response["success"]=0;
$response["message"]="OOPS!An error occured.";
echo json_encode($response);

}
?>