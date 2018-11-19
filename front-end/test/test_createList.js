QUnit.test("When creating a list, the list title field initially 'Enter title here'", function () {
  const input = document.querySelector('#titleInput');

  equal(input.placeholder, "Enter title here", "Initial list title placeholder is presents");
});

QUnit.test("Clicking the create list button button sends the list title to list controller", function () {
  var spy = sinon.spy(list, 'createList_post');
  const input = document.querySelector('#titleInput');

  var expectedTitle = "this is new title";
  input.value = expectedTitle;
  var button = document.querySelector('#createListButton').click();
  equal(spy.args[0][0].title, expectedTitle, "title was as expected");
  equal(spy.args[0][1], "http://localhost:8080/list");
  spy.restore();
});

QUnit.test("Clicking the Edit title button sends the updated list title to list controller", async function () {
  var spy = sinon.spy(list, 'updateTitle_put');
  //create a list
  var idForList = 3
  currId = idForList
  var listTitleEntered = "new list"

  await new Promise(
    (resolve) => {
      createList(listTitleEntered)
      setTimeout(() => resolve(), 500)
    })

  //click the edit title button
  await new Promise(
    (resolve) => {
      document.querySelector(`#editTitleButton${idForList}`).click()
      setTimeout(() => resolve(), 500)
    })

  //grab the h6, update value
  var title = document.querySelector(`#title${idForList}`)
  ok(title.getAttribute('contenteditable'))
  //click/enter the h6 
  var updatedTitle = "this title has been updated";
  title.value = updatedTitle;

  await new Promise(
    (resolve) => {
      title.click()
      setTimeout(() => resolve(), 500)
    }
  )

  //verify that request to backend was sent.
  equal(spy.args[0][0].title, updatedTitle, "title was updated");
  equal(spy.args[0][1], "http://localhost:8080/list");
  spy.restore();
});

QUnit.test("when editing the list title if bad response title is not updated in DB", async function () {
  var spy = sinon.spy(list, 'updateTitle_put');
  //create a list
  var idForList = 5
  currId = idForList
  let listTitleEntered = "this is a list"

  await new Promise(
    (resolve) => {
      createList(listTitleEntered);
      setTimeout(() => resolve(), 500)
    })

  promise = new Promise(function (resolve, reject) {
    resolve({
      status: 304,
      json() {
        return new Promise(function (resolve, reject) {
          resolve({
            "title": listTitleEntered
          });
        });
      }
    });
  });

  let stub = sinon.stub(window, 'fetch').returns(promise);

  //click the edit title button
  await new Promise(
    (resolve) => {
      document.querySelector(`#editTitleButton${idForList}`).click();
      setTimeout(() => resolve(), 500)
    })

  //grab the h6, update value
  var title = document.querySelector(`#title${idForList}`)
  ok(title.getAttribute('contenteditable'))

  //click/enter the h6 
  var updatedTitle = "this title has been updated";
  title.value = updatedTitle;
  //title.innerText = updatedTitle
  title.click()

  //verify that request to backend was sent.
  await new Promise((resolve) => {
    equal(spy.args[0][0].title, updatedTitle, "title was updated");
    equal(spy.args[0][1], "http://localhost:8080/list");
    equal(title.value, listTitleEntered)
    setTimeout(() => resolve(), 500)
  })
  spy.restore();
  stub.restore();

});

/* ------------- WIP test failing after api.js code refactor.  -------------- */
QUnit.test("User input entered populates list title on card after button click", function (assert) {

  var done = assert.async();
  card.called = false;
  currId = 9909;
  let spy = sinon.spy(card, "createListCard");
  let listTitleEntered = "new list"
  createList(listTitleEntered);

  setTimeout(function () {
    currId = 0;
    equal(spy.calledWithExactly(9909, listTitleEntered), true);
    spy.restore();
    done();
  });
});

QUnit.test("list title field initially 'Enter title here' for a new card after other cards have been added", function () {
  const input = document.querySelector('#titleInput');
  equal(input.placeholder, "Enter title here", "Initial list title placeholder is presents");
});

QUnit.test("Card was not created with 304 status", function (assert) {
  var done = assert.async();
  var listTitleEntered = "New List";
  const input = document.querySelector('#titleInput');
  input.value = listTitleEntered;

  var promise = new Promise(function (resolve, reject) {
    resolve({
      "status": 304,
      "title": listTitleEntered
    });
  });

  let spy = sinon.spy(card, "createListCard");
  let stub = sinon.stub(window, 'fetch').returns(promise);

  document.querySelector('#createListButton').click();

  setTimeout(function () {
    equal(spy.called, false);
    stub.restore();
    spy.restore();
    done();
  });

});

createList = async function (listTitle) {
  const titleForNewCard = document.querySelector('#titleInput');
  titleForNewCard.value = listTitle

  var promise = new Promise(function (resolve, reject) {
    resolve({
      status: 201,
      json() {
        return new Promise(function (resolve, reject) {
          resolve({
            "title": listTitle
          });
        });
      }
    });
  });

  let stub = sinon.stub(window, 'fetch').returns(promise);
  await new Promise(
    (resolve) => {
      document.querySelector('#createListButton').click()
      setTimeout(() => resolve(), 500)
    })
  stub.restore()
}