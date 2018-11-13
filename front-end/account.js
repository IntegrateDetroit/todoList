
function validate_passwords(){
var password1 = document.getElementById("passwordOne");
var password2 = document.getElementById("passwordTwo");

// console.log(password1.value +'|'+ password2.value);
    if(password1.value !== password2.value){
        document.getElementById("sign-up-button").disabled = true;
        $("#passwordTwo").foundation('show');
    }else{
        document.getElementById("sign-up-button").disabled = false;
        $("#passwordTwo").foundation('hide');
    }

}

function submit_click(){
    event.preventDefault();
    create_user();
}

function create_user(){
    
    var fname = document.getElementById("fname").value;
    var lname = document.getElementById("lname").value;
    var eMail = document.getElementById("eMail").value;
    var password1 = document.getElementById("passwordOne").value;
    //var password2 = document.getElementById("passwordTwo");
    var signupDate = new Date().getTime();

    data = {
        firstName: fname ,
        lastName: lname ,
        eMail: eMail ,
        paswordHash: password1 ,
        signupDate: signupDate 
      }
      //console.log(JSON.stringify(data));
      user.createUser_post(data, "http://localhost:8080/user");
}



