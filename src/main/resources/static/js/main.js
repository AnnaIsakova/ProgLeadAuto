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
	div.innerHTML =
		`<div class="input-group">
			<input th:field="*{phone${len}}" type='text' class='form-control' id='inputPhone${len}' name='phone${len}' placeholder='Phone ${len}'>
			<span class="input-group-addon">
				<i class="fa fa-phone fa-lg" aria-hidden="true"></i>
			</span>
		</div>`;
	phoneNumber.appendChild(div);
}, false);

if (psw && confirmPsw) {

psw.addEventListener("change", validatePasswords);
confirmPsw.addEventListener("keyup", validatePasswords);
}

function validatePasswords() {

	if (psw.value != confirmPsw.value) {
		confirmPsw.setCustomValidity('This field should match the password');
	} else {
		confirmPsw.setCustomValidity('');
	}
}