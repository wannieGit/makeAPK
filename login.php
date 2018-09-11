<?php
$hostname_localhost ="localhost";
$database_localhost ="jellybox";
$username_localhost ="jellybox";
$password_localhost ="";
$localhost = mysql_connect($hostname_localhost,$username_localhost,$password_localhost) or trigger_error(mysql_error(),E_USER_ERROR);
 
mysql_select_db($database_localhost, $localhost);
 
$u_id = $_POST['u_id'];
$u_pw = $_POST['u_pw'];
$query_search = "select * from custom_info where u_id = '".$id."' AND u_pw = '".$password."'";
$query_exec = mysql_query($query_search) or die(mysql_error());
$rows = mysql_num_rows($query_exec);
 
 if($rows == 0) { 
 echo "No Such User Found"; 
 }
 else  {
    echo "User Found"; 
}
?>
