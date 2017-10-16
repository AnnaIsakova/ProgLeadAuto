var btnAddPhone = document.getElementById("btnAddPhone");
var phoneNumber = document.getElementById("phonenumber");
var psw = document.getElementById("inputPassword1");
var confirmPsw = document.getElementById("inputPassword2");

btnAddPhone.addEventListener("click", function() {
	var len = phoneNumber.getElementsByTagName("input").length;
	if (len >= 3) return;
	len++;
	var div = document.createElement('div');
	div.className = "form-group";
	div.innerHTML = `<input type='text' class='form-control' id='inputPhone${len}' name='phone${len}' placeholder='Phone ${len}'>`;
	phoneNumber.appendChild(div);
}, false);

if (psw && confirmPsw) {

psw.addEventListener("change", validatePasswords);
confirmPsw.addEventListener("keyup", validatePasswords);
}

function validatePasswords() {

	if (psw.value != confirmPsw.value) {
		confirmPsw.setCustomValidity('The password and its confirm are not the same');
	} else {
		confirmPsw.setCustomValidity('');
	}
}	