

validateLogin = function(){
	const form = document.querySelector("#frmLogin");
	const user = document.getElementById("sdt");
	const userEr = document.querySelector(".errorUser");
	const password = document.getElementById("password");
	const passwordEr = document.querySelector(".errorPass");
	
	const phoneReg = /((09|03|07|08|05)+([0-9]{8})\b)/g;
	
	if(user != null){
		user.addEventListener("input", (event) => {
			  if (user.validity.valid) {
			    userEr.textContent = "";
			    userEr.className = "error";
			  } else {
				  if (user.validity.valueMissing) {
					    userEr.textContent = "Hãy nhập số điện thoại.";
					    event.preventDefault();
					  }
				  userEr.className = "error active";
			  }
			});
	}

	if(password != null){
		password.addEventListener("input", (event) => {
			if (password.validity.valid) {
				passwordEr.textContent = "";
				passwordEr.className = "error";
			} else {
				if (password.validity.valueMissing) {
					  passwordEr.textContent = "Hãy nhập password.";
				  }
				passwordEr.className = "error active";
			}
		});
	}
	
	if(form != null){
		form.addEventListener("submit", (event) => {
			var number = Number(user.value);
			if(!isNaN(number)){
				const isValid = phoneReg.test(user.value);
				if(!isValid){
					userEr.textContent = "Số điện thoại không hợp lệ.";
					userEr.className = "error active";
					event.preventDefault();
				}			
			}
		});
	}

}
validateLogin();

const btn = document.querySelector("#btnSend");
checkEmail = function(){
	const reg =/^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6})*$/;
	var email = $("#enterEmail").val();
	var error = document.getElementById("errEmail");
	var isValid = reg.test(email);
	
	if(email.length == 0){
		error.style.color="red";
		error.innerHTML="Hãy điền email."
	}else if(!isValid){
		error.style.color="red";
		error.innerHTML="Email không đúng định dạng."
	} else{
		error.innerHTML="";
		btn.setAttribute("type","submit");
	}
}
