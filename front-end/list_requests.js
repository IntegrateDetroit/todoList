const list =  {createList_post: async function(data, url, successCallback){
  let response = await fetch(
    url, {
      body: JSON.stringify(data),
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    })
  if (response.status === 201) {
    response.json().then(function (body) {
      console.log("in the success case of fetch")
      successCallback(currId, body.title);
    });
  } else if (response.status === 304) {
  }
},
updateTitle_put: async function(data, url, failureCallback, oldTitle, titleId){
  let response = await fetch(
    url, {
      body: JSON.stringify(data),
      method: 'PUT',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    })
    
    console.log("we are in update title")
    if (response.status === 200) {
    response.json().then(function (body) {
  
    });
  } else if (response.status === 304) {
    failureCallback(oldTitle, titleId)
  }
}
}