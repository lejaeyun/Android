<?php 
    $con = mysqli_connect("localhost", "dietapp", "duftlafm37!", "dietapp");
    mysqli_query($con,'SET NAMES utf8');

    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];
    $userName = $_POST["userName"];
    $userAge = $_POST["userAge"];
    $userGender = $_POST["userGender"];
    $userHeight = $_POST["userHeight"];
    $userWeight = $_POST["userWeight"];

    $statement = mysqli_prepare($con, "INSERT INTO USER VALUES (?,?,?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "sssisii", $userID, $userPassword, $userName, $userAge, $userGender, $userHeight, $userWeight);
    mysqli_stmt_execute($statement);


    $response = array();
    $response["success"] = true;
 
   
    echo json_encode($response);
?>