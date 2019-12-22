
window.onload = function (ev) {

    var searchImgTag = document.getElementsByClassName('search-submit')[0];

    searchImgTag.addEventListener('click', function (evt) {
        console.log('들어왔다!!!');
        var formTag = searchImgTag.parentElement;
        console.log(formTag);
        formTag.submit();
    });
};
