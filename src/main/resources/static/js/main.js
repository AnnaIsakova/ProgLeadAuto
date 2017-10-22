
var psw = document.getElementById("inputPassword1");
var confirmPsw = document.getElementById("inputPassword2");

document.addEventListener("click", function(){
    var phoneNumber = document.getElementById("phoneNumber");
    var len = phoneNumber.getElementsByTagName("input").length;
    if (len >= 3) return;
    len++;
    var div = document.createElement('div');
    div.className = "form-group";
    div.innerHTML =
        `<div class="input-group">
			<input 
			th:field="*{phone${len}}" 
			type='text' class='form-control' 
			id='inputPhone${len}' 
			name='phone${len}' 
			placeholder='Phone ${len}'>
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

function dropUserBtn() {
    document.getElementById("myDropdown").classList.toggle("show");
}

// Close the dropdown if the user clicks outside of it
window.onclick = function(event) {
    if (!event.target.matches('.username-btn')) {

        var dropdowns = document.getElementsByClassName("dropdown-content");
        var i;
        for (i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}