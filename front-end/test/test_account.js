// const Foundation = require('foundation-sites');

QUnit.test("Missmatched passwords disables submit button", function(){
    document.getElementById('passwordOne').value = "testpass1";
    document.getElementById('passwordTwo').value = "testpass2";
    
    validate_passwords();
    var submit_button_state = document.getElementById( "sign-up-button" ).disabled;
    equal(submit_button_state, true, "Validated Submit Button is disabled");
  });

  QUnit.test("Missmatched passwords pops up tool tip", function(){
    document.getElementById('passwordOne').value = "testpass1";
    document.getElementById('passwordTwo').value = "testpass2";
    validate_passwords();
    var foundation_tooltip_id = "#"+$('#passwordTwo').attr('data-yeti-box');
    var pass_tooltip_status = $(foundation_tooltip_id).attr('data-is-active');
    
    equal(pass_tooltip_status, "true", "Validated Password Tooltip is visible");
  });

  QUnit.test("Matched passwords hides tool tip", function(){
    document.getElementById('passwordOne').value = "testpass1";
    document.getElementById('passwordTwo').value = "testpass1";
    validate_passwords();
    var foundation_tooltip_id = "#"+$('#passwordTwo').attr('data-yeti-box');
    var pass_tooltip_status = $(foundation_tooltip_id).attr('data-is-active');
    
    equal(pass_tooltip_status, "false", "Validated Password Tooltip is not visible");
  });

  QUnit.test("Matched passwords enables submit button", function(){
    document.getElementById('passwordOne').value = "testpass1";
    document.getElementById('passwordTwo').value = "testpass1";
    
    validate_passwords();
    var submit_button_state = document.getElementById( "sign-up-button" ).disabled;
    equal(submit_button_state, false, "Validated Submit Button is enabled");
  });

  QUnit.test("Submit Button sends to database", function(){
    var fname = "fname";
    var lname = "lname";
    var eMail = "email1@email.com";
    var pass1 = "passwordOne";
    var pass2 = "passwordTwo";
    var expectedStatus = 201;
    
    document.getElementById("fname").value = fname;
    document.getElementById("lname").value = lname;
    document.getElementById("eMail").value = eMail;
    document.getElementById("passwordOne").value = pass1;
    document.getElementById("passwordTwo").value = pass2;
    
  //   var promise = new Promise(function(resolve, reject) {
  //     resolve({"status": expectedStatus, 
  //     "firstName": fname,
  //     "lastName": lname,
  //     "eMail": eMail,
  //     "passwordHash": "",
  //     "signupDate": new Date().getTime(),
  //     "userID": 99
  //   });
  // });
  
  let stub = sinon.stub(userrequest, "post");
  // let stub = sinon.stub(window, 'fetch').returns(promise);
  
  document.getElementById( "sign-up-button" ).click();
  
  equal(stub.calledOnce, true, "Post request was called once");
  //console.log(stub.args);
  equal(stub.args[0][0].firstName, fname, "F name was as expected");
  equal(stub.args[0][0].lastName, lname, "L name was as expected");
  equal(stub.args[0][0].eMail, eMail, "Email was as expected");
  equal(stub.args[0][0].paswordHash, pass1, "Password was as expected");
  ok($.isNumeric( stub.args[0][0].signupDate),"Signup Date is present" );
  equal(stub.args[0][1], "http://localhost:8080/user", "URL was as exepected");
  stub.restore();
    
  });

  QUnit.test("Status 201 redirects to login.html", function(assert){

    var done = assert.async();  
    var fname = "fname1";
    var lname = "lname1";
    var eMail = "email1@email.com123";
    var pass1 = "password1";
    var pass2 = "password2";
    var httpStatus = 201;
    
    document.getElementById("fname").value = fname;
    document.getElementById("lname").value = lname;
    document.getElementById("eMail").value = eMail;
    document.getElementById("passwordOne").value = pass1;
    document.getElementById("passwordTwo").value = pass2;
    
    var promise = new Promise(function(resolve, reject) {
      resolve({"status": httpStatus, 
      "firstName": fname,
      "lastName": lname,
      "eMail": eMail,
      "passwordHash": pass1,
      "signupDate": new Date().getTime(),
      "userID": 99
    });
  });
  
  //let spy = sinon.spy(userRedirect, "redirectToIndex");
  let stub1 = sinon.stub(window,"fetch").returns(promise);
  let stub2 = sinon.stub(redirect);
  
  document.getElementById( "sign-up-button" ).click();
  
  setTimeout(function() {
    console.log(stub2);
    equal(stub2.redirectToPage.calledOnce, true, "Redirect was called once");
    equal(stub2.redirectToPage.args[0], "login.html", "Redirect was called with 'login.html'");
    stub1.restore();
    //stub2.restore();
    done();
  });
  });

  QUnit.test("Status 204 pops 'user already exists' alert", function(assert){

    var done = assert.async();
    var fname = "fname1";
    var lname = "lname1";
    var eMail = "email1@email.com";
    var pass1 = "password1";
    var pass2 = "password2";
    var httpStatus = 204;
    
    document.getElementById("fname").value = fname;
    document.getElementById("lname").value = lname;
    document.getElementById("eMail").value = eMail;
    document.getElementById("passwordOne").value = pass1;
    document.getElementById("passwordTwo").value = pass2;
    
    var promise = new Promise(function(resolve, reject) {
      resolve({"status": httpStatus, 
      "firstName": fname,
      "lastName": lname,
      "eMail": eMail,
      "passwordHash": pass1,
      "signupDate": new Date().getTime(),
      "userID": 99
    });
  });
  
  //let spy = sinon.spy(userRedirect, "redirectToIndex");
  let stub1 = sinon.stub(window,"fetch").returns(promise);
  let stub2 = sinon.stub(window,"alert");
  stub2.withArgs('User already exists');
  
  document.getElementById( "sign-up-button" ).click();
  
  setTimeout(function() {
    console.log(stub2);
    equal(stub2.calledOnce, true, "Alert was thrown once");
    stub1.restore();
    stub2.restore();
    done();
  });


  });

  QUnit.test("Status other than 201 or 204 pops 'try again later' alert", function(assert){

    var done = assert.async();
    var fname = "fname1";
    var lname = "lname1";
    var eMail = "email1@email.com";
    var pass1 = "password1";
    var pass2 = "password2";
    var httpStatus = 304;
    
    document.getElementById("fname").value = fname;
    document.getElementById("lname").value = lname;
    document.getElementById("eMail").value = eMail;
    document.getElementById("passwordOne").value = pass1;
    document.getElementById("passwordTwo").value = pass2;
    
    var promise = new Promise(function(resolve, reject) {
      resolve({"status": httpStatus, 
      "firstName": fname,
      "lastName": lname,
      "eMail": eMail,
      "passwordHash": pass1,
      "signupDate": new Date().getTime(),
      "userID": 99
    });
  });
  
  //let spy = sinon.spy(userRedirect, "redirectToIndex");
  let stub1 = sinon.stub(window,"fetch").returns(promise);
  let stub2 = sinon.stub(window,"alert");
  stub2.withArgs('A problem occured, Please try again later.');
  
  document.getElementById( "sign-up-button" ).click();
  
  setTimeout(function() {
    console.log(stub2);
    equal(stub2.calledOnce, true, "Alert was thrown once");
    stub1.restore();
    stub2.restore();
    done();
  });
  });