
var card = {
  createListCard: function (newCardId, newTitle) {
    let cardId = "list" + currId;
    let todoCard = `
    <div id="${cardId}" class="grid-x grid-padding-x">
      <div class="large-6 large-pull-6 cell">
       <div class="card card-tabs large-6">
        <div class="card-divider">

        <div class="listCardTitle">
          <h6 style="display: inline;">${newTitle}</h6>

          <button style="opacity: 0.5; padding-left: 10px; padding-bottom: 15px;">
            <img src="./resources/pen-solid.svg" width="15px">
          </button>
          </div>

          
          <button href="#" data-close data-reveal-id="archiveModal" class="secondary button" id="archiveModal" type="submit">Archive List
            <img src="./resources/iconmonstr-gear-1.svg">
          </button>
        </div>
      </div>
    </div>
  </div>`

   
    $("body").append(todoCard);
    const card = document.querySelector("#"+cardId);
    // card.addEventListener('click', function (e) {
    //   card.remove()
    //   })
    currId++;
  }

};