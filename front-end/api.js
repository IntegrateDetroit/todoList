var firstId = 0;
var currId = firstId;

var request = {
  post: function (data, url, successCallback, failureCallback)
  {
    fetch(
        url,
        {
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(data),
          method: 'POST'
        })
    .then(
        function(data) {
          if (data.status === 201) {
            data.json().then(function (body) {
              console.log("in the success case of fetch")
              successCallback(currId, body.title);
            });
          }
          else if (data.status === 304) {
          }
        },
        function(error) {
          console.log("ERROR = ", error);
        });
      },
      accountCreationPost: function (data, url, successCallback, failureCallback)
      {
        fetch(
            url,
            {
              headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
              },
              body: JSON.stringify(data),
              method: 'POST'
            })
        .then(
            function(data) {
              if (data.status === 201) {
                console.log("ERROR = ", error)
                data.json().then(function (body) {
                  successCallback(currId, body.title);
                });
              }
              else if (data.status === 304) {
              }
            },
            function(error) {
              console.log("ERROR = ", error);
            });
          }
     
  }

var redirect = { redirectToPage: function (url) {
    $(location).attr('href', url)
}}

var userrequest = { post: function (data, url){
  fetch(
    url,
    {
      headers: { 
        'Accept': 'application/json',
        'Content-Type': 'application/json' 
      },
      body: JSON.stringify(data),
      method: 'POST'
    })
    .then(response => {
      console.log(response)          
      //return response;
      if(response.status==201){
        redirect.redirectToPage("login.html");
      }else if(response.status==204){
        alert('User already exists');
      }else{
        alert('A problem occured, Please try again later.');
      }
    })
  }
}


