function includeHeader()
{
    var headerContent = 'header';
    var header = document.getElementById('header');
    // console.log(`Header ${header}`);
    header.innerHTML = headerContent;
}

function includeFooter()
{
    var footerContent = 'footer';
    var footer = document.getElementById('footer');
    // console.log(`Header ${header}`);
    footer.innerHTML = footerContent;
}

function includeBody() {
    var bodyContent = '<p style="color: red;">body</p>';
    var body = document.getElementById('body');
    // console.log(`Header ${header}`);
    body.innerHTML = bodyContent;
}

function show() {
    includeHeader();
    includeBody();
    includeFooter();
}
show();