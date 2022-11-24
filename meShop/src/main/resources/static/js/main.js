function showConfirm(id){
var retVal = confirm("Do you want to delete?");
if(retVal) {
	 document.location.href = "/deleteUser/"+id;
}}
function validateFormDelete(productName){
	var retVal = confirm("Do you want to delete product "+productName+" ?");
	return retVal;
}
function changeMomneyFormat(){
	let number = document.getElementById("price").value;
	const { format } = new Intl.NumberFormat('vi-VN');
	const [, decimalSign] = /^0(.)1$/.exec(format(0.1));
	var num = number.replace(new RegExp(`[^${decimalSign}\\d]`, 'g'), '').replace(decimalSign, '.');
	var numFloat=parseFloat(num);
    let number3=numFloat.toLocaleString('vi-VN');
	document.getElementById("price").value=number3;
}
function submitFormQuantity(){
	//document.getElementById("quantityForm").submit();
	document.forms["quantityForm"].submit();
}

function showInfoForm(userName,fullName){
	var label = document.createElement("label");
	label.innerText="USERNAME: ";
	var label2 = document.createElement("label");
	label2.innerText="FULLNAME: ";
	var label3 = document.createElement("label");
	label3.innerText="PHONE: ";
	var input = document.createElement("input");
	input.type = "text";
	input.name = "fullName";
	input.value=fullName;
	
	
	var input2 = document.createElement("input");
	input2.type = "text";
	input2.name = "userName";
	input2.value=userName;
	var input3 = document.createElement("input");
	input3.type = "text";
	input3.name = "phone";
	input3.value="069274";
	dialog=document.getElementById("mydialog");
	dialog.style.display = "block";
	input.setAttribute("disabled",true);
	input2.setAttribute("disabled",true);
	input3.setAttribute("disabled",true);
	var br = document.createElement("br");
	//value='${userList[0].getId()};'
	dialog.innerHTML="";
	dialog.appendChild(label);
	dialog.appendChild(input);
	//dialog.appendChild(br);
	dialog.appendChild(label2);
	dialog.appendChild(input2);
	//dialog.appendChild(br);
	dialog.appendChild(label3);
	dialog.appendChild(input3);
}

// checkout
	const modal = document.querySelector('.modal')
	const check_out_address = document.querySelector('.check--out__address')
	check_out_address.onclick= ()=>
	{
  modal.classList.add('disable__none')
}
const butOuts = document.querySelectorAll('.modal__container-button button')
butOuts.forEach((butOut)=>
{
  butOut.onclick = ()=>
{
  modal.classList.remove('disable__none')
}
})
// checkout
