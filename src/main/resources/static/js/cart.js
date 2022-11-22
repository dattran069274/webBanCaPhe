
$(document).ready(function () {

    $("#submitFormQuantity").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        fire_ajax_submit();

    });

});
async function submitFormUpdateQuantity(event,productId) {
  let formData = new FormData(); 
  formData.append("quantity",event.target.value);
  formData.append("productId",$("#"+productId).val());
  let response =  await  fetch('/shopping-cart/updateCart', {
    method: "POST", 
    body: formData
  }); 
  if (response.status == 200) {
    response.json().then(data => updateView(data));
    //reload neu update ve 0
    if(event.target.value==0){
		location.reload();
	}
    }
}
function updateView(data){
	$("#total").text(data.total);
}
function submitCheckout(){
	 $("#checkoutForm").submit();
}
