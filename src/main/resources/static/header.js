
start();

function start()
{
    var headerContent = '<h1>Voici un texte très long qui représentera le titre de ce site!</h1>';
    
    var title = document.createElement('div');
    title.classList.add('test');
    title.dataset.itemId = '1';
    
    title.innerHTML = headerContent;
    
    console.log(`Div ${header}`);
    
    var header = document.getElementsByClassName('header')[0];
    header.append(title);

    console.log(`Titre ${title}`);

}
