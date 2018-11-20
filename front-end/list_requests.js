const list = {
  createList_post: async function (data, url, successCallback) {
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
        successCallback(currId, body.title);
      });
    } else if (response.status === 304) {}
  },
  updateTitle_put: async function (data, url, failureCallback, oldTitle, titleId) {
    let response = await fetch(
      url, {
        body: JSON.stringify(data),
        method: 'PUT',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        }
      })
    if (response.status === 200) {
      console.log('updated')
      response.json().then(function (body) {});
    } else if (response.status === 304) {
      console.log('not updated')
      console.log(oldTitle)
      console.log(titleId)
      failureCallback(oldTitle, titleId)
    }
  }
}