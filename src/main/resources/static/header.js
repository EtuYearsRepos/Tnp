
start();

function start()
{
    var headerContent = '<h1> Voici un texte très long qui représentera le titre de ce site! </h1>';
    var header = document.getElementById('header');
    // console.log(`Header ${header}`);
    header.innerHTML = headerContent;
}
