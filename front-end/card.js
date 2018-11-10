
var card = {
  createListCard: function (newCardId, newTitle) {
    let cardId = "list" + currId;
    
    //create the card 
    let todoCard = document.createElement('div')
    todoCard.id = cardId 
    todoCard.classList.add('grid-x', 'grid-padding-x')

    let todoCardOutterContainer = document.createElement('div')
    todoCardOutterContainer.classList.add('large-6','large-pull-6','cell')

    let todoCardInnerContainer = document.createElement('div')
    todoCardInnerContainer.classList.add('card','card-tabs','large-6')

    todoCard.appendChild( todoCardOutterContainer )
    todoCardOutterContainer.appendChild( todoCardInnerContainer )
    
    //create the title
    //create the archive button
    let cardDivider = document.createElement('div')
    cardDivider.classList.add('card-divider')

    let titleContainer = document.createElement('div')
    titleContainer.classList.add('listCardTitle')

    let cardTitle = document.createElement('h6')
    cardTitle.style['display'] = 'inline'
    cardTitle.innerText = newTitle
    cardTitle.id = `title${currId}`


    let editTitleButton = document.createElement( 'button' )
    editTitleButton.style[ 'opacity' ] = 0.5
    editTitleButton.style[ 'padding-left' ] = '10px'
    editTitleButton.style[ 'padding-bottom' ] = '15px'
    editTitleButton.id = `editTitleButton${currId}`

    console.log('created editTitleButton')

    let titlePen = document.createElement( 'img' )
    titlePen.src = './resources/pen-solid.svg'
    titlePen.style[ 'width' ] = '15px'

    todoCardInnerContainer.appendChild( cardDivider )
    cardDivider.appendChild( titleContainer )

    titleContainer.appendChild( cardTitle )
    titleContainer.appendChild( editTitleButton )

    editTitleButton.appendChild( titlePen )

    let archiveButton = document.createElement( 'button' )
    archiveButton.setAttribute( 'href', '#' )
    archiveButton.setAttribute( 'data-close', '' )
    archiveButton.setAttribute( 'data-reveal-id', 'archiveModal' )
    archiveButton.setAttribute( 'type', 'submit' )
    archiveButton.classList.add('secondary', 'button')

    archiveButton.id = 'archiveModal'
    archiveButton.innerText = 'Archive List'

    let archiveIcon = document.createElement( 'img' )
    archiveIcon.src = './resources/iconmonstr-gear-1.svg'
    
    cardDivider.appendChild( archiveButton )
    archiveButton.appendChild( archiveIcon )

    //create container for the items
    let itemContainer = document.createElement('div')

    //create the add item button
    let addItemContainer = document.createElement( 'div' )
    addItemContainer.classList.add( 'grid-x', 'align-center' )

    let addItemButton = document.createElement( 'button' )
    
    let addItemIcon = document.createElement( 'img' )
    addItemIcon.src = './resources/plusSign.svg'

    addItemButton.appendChild( addItemIcon )
    addItemContainer.appendChild( addItemButton )

    todoCardInnerContainer.appendChild( addItemContainer )
    
    $("body").append(todoCard);
    
    // * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 

    addItemButton.addEventListener(
      'click',
      ( event ) => {
        alert( 'ouch!' )
      })

    editTitleButton.addEventListener(
      'click',
      ( event ) => {
        cardTitle.setAttribute(
          "contenteditable", "true"
        )

        editTitleButton.style["display"]="none"
        cardTitle.focus()

      })

    cardTitle.addEventListener(
      'blur',
      ( event ) => {
        cardTitle.setAttribute(
          "contenteditable", "false"
        )

        editTitleButton.style["display"]="inline"

      }
    )

    


    const card = document.querySelector("#"+cardId);
    // card.addEventListener('click', function (e) {
    //   card.remove()
    //   })
    currId++;
  }

};