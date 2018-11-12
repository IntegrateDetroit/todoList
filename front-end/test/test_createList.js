

QUnit.test("list title field initially 'Enter title here'", function() {
  const input = document.querySelector('#titleInput');

  equal(input.placeholder, "Enter title here", "Initial list title placeholder is presents");
});

QUnit.test("button click sends information to DB", function() {
  var spy = sinon.spy(request, "post");
  const input = document.querySelector('#titleInput');

  var expectedTitle = "this is new title";
  input.value = expectedTitle;
  var button = document.querySelector('#createListButton').click();
  equal(spy.args[0][0].title, expectedTitle, "title was as expected");
  equal(spy.args[0][1], "http://localhost:8080/list");
  spy.restore();
});

QUnit.test("button click sends updated title to DB", async function() {
  var spy = sinon.spy(request, "post");
  //create a list
  var idForList = 3
  currId = idForList
  const titleForNewCard = document.querySelector('#titleInput');
  var listTitleEntered = "new list"
  titleForNewCard.value = listTitleEntered

  var promise = new Promise(function(resolve, reject) {
    resolve(
        {
          status: 201,
          json() { return new Promise(function(resolve, reject)
            {
              resolve ({"title": listTitleEntered });
              reject();
            });
          }
        }
    );
    reject();
  });

  let stub = sinon.stub(window, 'fetch').returns(promise);
  await new Promise(
    ( resolve ) => {
      document.querySelector('#createListButton').click()
      setTimeout(() => resolve(), 500)
    })
  
  //grab the button
  console.log(`#editTitleButton${idForList}`)
  var button = document.querySelector(`#editTitleButton${idForList}`)
  //click the button
  await new Promise(
    ( resolve ) => {
      button.click();
      setTimeout(() => resolve(), 500)
    })

  var title = document.querySelector(`#title${idForList}`)

  ok(title.getAttribute('contenteditable'))
  //grab the h6, update value
  //click/enter the h6 
  //verify that request to backend was sent.

  // var updatedTitle = "this title has been updated";
  // input.value = updatedTitle;
  // equal(spy.args[0][0].title, updatedTitle, "title was updated");
  // equal(spy.args[0][1], "http://localhost:8080/list");
  spy.restore();
  stub.restore();
  
});
/* ------------- WIP test failing after api.js code refactor.  -------------- */
QUnit.test("User input entered populates list title on card after button click", function(assert) {

  var done = assert.async();
  card.called = false;
  currId = 9909;
  var listTitleEntered = "New List";
  const input = document.querySelector('#titleInput');
  input.value = listTitleEntered;

  var promise = new Promise(function(resolve, reject) {
    resolve(
        {
          status: 201,
          json() { return new Promise(function(resolve, reject)
            {
              resolve ({"title": listTitleEntered });
              reject();
            });
          }
        }
    );
    reject();
  });

  let spy = sinon.spy(card, "createListCard");
  let stub = sinon.stub(window, 'fetch').returns(promise);

  document.querySelector('#createListButton').click();

  setTimeout(function() {
    currId = 0;
    equal(spy.calledWithExactly(9909, listTitleEntered), true);
    stub.restore();
    spy.restore();
    done();
  });
});

QUnit.test("list title field initially 'Enter title here' for a new card after other cards have been added", function(){
  const input = document.querySelector('#titleInput');

  equal(input.placeholder, "Enter title here", "Initial list title placeholder is presents");
});

QUnit.test("Card was not created with 304 status", function(assert) {
  var done = assert.async();
  var listTitleEntered = "New List";
  const input = document.querySelector('#titleInput');
  input.value = listTitleEntered;

  var promise = new Promise(function(resolve, reject) {
      resolve({"status": 304, "title": listTitleEntered});
  });
  
  let spy = sinon.spy(card, "createListCard");
  let stub = sinon.stub(window, 'fetch').returns(promise);
  
  document.querySelector('#createListButton').click();

  setTimeout(function() {
    equal(spy.called, false);
    stub.restore();
    spy.restore();
    done();
  });

});
