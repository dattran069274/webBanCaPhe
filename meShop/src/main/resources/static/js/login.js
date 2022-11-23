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
const signs = document.querySelectorAll('.sign')
const forms = document.querySelectorAll('.form')
const form_login = document.querySelector('#form-login')
const form_regis = document.querySelector('#form-regis')
const show_hide_icon = document.querySelectorAll('.show-hide-icon')


show_hide_icon.forEach(eyeElement => {
    eyeElement.addEventListener('click',()=>{
        const pwFields = eyeElement.parentElement.parentElement.querySelectorAll('.password')
        pwFields.forEach(pwField =>
        {
            if(pwField.type === 'password')
             {
                 pwField.type = 'text'
                 eyeElement.classList.replace('fa-eye-slash','fa-eye')
             }
             else{
                 pwField.type = 'password'
                 eyeElement.classList.replace('fa-eye','fa-eye-slash')
             }

        })
      
    })
});
