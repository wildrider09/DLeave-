<?php
$servername="localhost";
$usernme="id2054814_abhishek";
$pswd="abhishekkgarg";
$dbname="id2054814_abhishek";


$response["data"]=array();


$con=mysqli_connect($servername,$usernme,$pswd,$dbname);

$result=mysqli_query($con,"select users.*,leaves.* from users,leaves where leaves.status!='' AND users.type='Employee' AND users.empid=leaves.empid AND leaves.manager=users.manager");


if ($result->num_rows > 0)
{
    // output data of each row
    while($row = $result->fetch_assoc()) 
{
       $data=array();
  
       $data['empname']=$row['name'];
       $data['empid']=$row['empid'];
       $data['startdate']=$row['startdate'];
       $data['enddate']=$row['enddate'];
       $data['days']=$row['days'];
       $data['reason']=$row['reason'];
       $data['status']=$row['status'];
       
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