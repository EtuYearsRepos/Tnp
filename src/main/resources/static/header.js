
start();

function start()
{
    var headerContent = 'Voici un texte très long qui représentera le titre de ce site!';
    
    var title = document.createElement('h1');
    title.classList.add('test');
    title.dataset.itemId = '1';
    
    title.innerHTML = headerContent;
    
    
    var header = document.getElementsByClassName('header')[0];
    header.append(title);
    
    console.log(`Header ${header}`);
    console.log(`Titre ${title}`);

}
