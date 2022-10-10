function handleChange(checkbox)
{
	if(checkbox.checked == true){
        document.getElementById("remember").value="true";
        console.log(true);
    }else{
        document.getElementById("remember").value="false";
        console.log(false);
   }
}
function checkLogin(){
	var checkbox=document.getElementById("rememberCheckbox");
	handleChange(checkbox);
	var name=document.getElementById("name");
	var pass=document.getElementById("password");
	if(name!=null&&pass!=null){
		if(length(pass)>3){
			return true;
		}
		else document.getElementById("message").innerText="PASSWORD too short(>=3)!";
	}
	document.getElementById("message").innerText="PASSWORD is not correct!";
	return false;
}
