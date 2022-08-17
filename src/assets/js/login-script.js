var user = window.localStorage.getItem("user");

if(user == null){
        user = " ";
}else{
    document.getElementById("userName").value = user;
}

function checkR(){ 
    var userRem = document.getElementById("remember").checked;
    var userName = document.getElementById("userName").value;
    if(userRem){
        window.localStorage.setItem("user", userName);
    }
    console.log(userRem);
    console.log(typeof(userRem));
}