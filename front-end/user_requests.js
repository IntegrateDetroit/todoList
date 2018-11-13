const user = {
  createUser_post: async function (data, url) {
    let response = await fetch(
      url, {
        body: JSON.stringify(data),
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        }
      })
    if (response.status == 201) {
      redirectToPage("login.html");
    } else if (response.status == 204) {
      alert('User already exists');
    } else {
      alert('A problem occured, Please try again later.');
    }
  }
}