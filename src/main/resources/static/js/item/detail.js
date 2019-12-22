
var countNum = 0;

// 장바구니 추가
function addCart(elem, itemId) {

    var formTag = document.createElement('form');
    formTag.method = 'post';
    formTag.action = '/shoppingCart';

    var inputItemIdForAddCart = document.createElement('input');
    inputItemIdForAddCart.type = 'hidden';
    inputItemIdForAddCart.name = 'itemId';
    inputItemIdForAddCart.value = itemId;

    formTag.append(inputItemIdForAddCart);
    document.body.append(formTag);

    formTag.submit();

}

function closeModal() {

    var modal = document.getElementsByClassName('modal')[0];
    modal.classList.remove('active');

}

function showModal(elem) {

    var imgWrap = elem.parentElement;
    var imgNameInput = imgWrap.getElementsByClassName('img-name')[0];
    var imgNameVal = imgNameInput.value;

    var imgTag = document.createElement('img');
    imgTag.src = '/upload/' + imgNameVal;
    imgTag.style.width = 'fit-content';
    imgTag.style.borderRadius = '5px';
    imgTag.style.maxWidth = '720px';

    var modal = document.getElementsByClassName('modal')[0];
    var modalContent = modal.getElementsByClassName('modal-content')[0];

    var removeImg = modalContent.getElementsByTagName('img')[0];

    if (removeImg != null) {
        removeImg.remove();
    }

    modalContent.append(imgTag);
    modal.classList.add('active');

}


function plusCount(countParam) {
    countNum++;
    countParam.setAttribute('value', countNum);
}

function minusCount(countParam) {
    countNum--;
    countParam.setAttribute('value', countNum);

}



function likeTypeSet() {
    var likeTypeTag = document.getElementsByClassName('likeType')[0];
    var likeImgTag = document.getElementById('likeImg');
    if (likeImgTag.classList.contains('empty')) {
        likeTypeTag.value = 'add'
    } else {
        likeTypeTag.value = 'remove';
    }
}

var infoTab = document.getElementById('info');
var reviewTab = document.getElementById('reviews');
var qnaTab = document.getElementById('qna');

var tabs = document.getElementsByClassName('item-tab');
var tabLinks = document.getElementsByClassName('tab-link');

function displayNoneTabs() {
    for (i = 0; i < tabs.length; i++) {
        tabs[i].setAttribute('style', 'display: none;');
    }
}

function styleReset() {
    for (i = 0; i < tabLinks.length; i++) {
        var tabLink = tabLinks[i];
        tabLink.style.backgroundColor = '#f3f3f3';
        tabLink.style.color = '#555';

    }
}

function styleChange(elem) {
    elem.style.backgroundColor = '#7ebb34';
    elem.style.color = '#fff';
}

function itemInfoScroll(elem) {
    displayNoneTabs();
    styleReset();
    styleChange(elem);
    infoTab.setAttribute('style', 'display: block;');
}

function itemReviewScroll(elem) {
    displayNoneTabs();
    styleReset();
    styleChange(elem);
    reviewTab.setAttribute('style', 'display: block;');

}

function itemQnaScroll(elem) {

    displayNoneTabs();
    styleReset();
    styleChange(elem);
    qnaTab.setAttribute('style', 'display: block;');

}

var qnaFormTag = document.getElementById('qna-form');

function secretCheck(elem) {

    if (elem.classList.contains('secretCheck') && elem.classList.contains('secret-div')) {
        var secretInputTag = document.getElementById('secretVal');
        secretInputTag.value = 'false';

        elem.classList.remove('secretCheck');
        elem.childNodes[1].remove();

    } else if (!elem.classList.contains('secretCheck')) {

        var secretInputTag = document.getElementById('secretVal');
        secretInputTag.value = 'true';

        var imgTag = document.createElement('img');
        imgTag.src = '/image/icon/check_mark.svg';
        imgTag.setAttribute('style', 'max-width: 40px;');
        imgTag.setAttribute('style', 'width: 100%;');

        elem.appendChild(imgTag);
        elem.classList.add('secretCheck');
    }
}

function allowContent(elem) {

    var qnaList = document.getElementsByClassName('heightUp')[0];

    if (qnaList != null) {
        qnaList.style.height = '80px';
        qnaList.classList.remove('heightUp');
    }

    var className = "qna__list";

    if (!elem.classList.contains('active-button')) {

        var activeButton = document.getElementsByClassName('active-button')[0];
        if (activeButton) {
            activeButton.classList.remove('active-button');
        }

        elem.classList.add('active-button');

        while(true) {
            var parentElement = elem.parentElement;
            if (parentElement.classList.contains(className)) {
                parentElement.style.height = 'auto';
                parentElement.classList.add('heightUp');
                break;
            }
            elem = parentElement;
        }
    } else {
        elem.classList.remove('active-button');

        while(true) {
            var parentElement = elem.parentElement;
            if (parentElement.classList.contains(className)) {
                parentElement.style.height = '80px';
                break;
            }
            elem = parentElement;
        }
    }

}

function deniedContent() {
    alert("작성자만 볼 수 있습니다.");
}

// admin 댓글 업데이트
function updateForm(elem) {
    var btnBoxTag = elem.parentElement;
    var qnaCommentReplyTag = btnBoxTag.parentElement;
    qnaCommentReplyTag.style.display = 'none';
    var qnaContentBox = qnaCommentReplyTag.parentElement;

    var childNodes = qnaContentBox.childNodes;
    var childNode = childNodes[3];
    childNode.style.display = 'block';

}

// admin 댓글 업데이트 취소
function updateCancel(elem) {

    var elemVar = elem;

    while (true) {
        var parentElement = elemVar.parentElement;
        if (parentElement.classList.contains('qna-comment-form-update')) {
            parentElement.style.display = 'none';
        }
        if (parentElement.classList.contains('qna-content')) {
            var qnaCommentReplyTag = parentElement.childNodes[5];
            qnaCommentReplyTag.style.display = 'block';
            break;
        }
        elemVar = parentElement;

    }


}

function tabSetting() {
    var tabSettingInput = document.getElementsByClassName('tab-setting')[0];
    var tabVal = tabSettingInput.value;
    var tabLinkTags = document.getElementsByClassName('tab-link');

    if (tabVal == 'info') {

        displayNoneTabs();
        styleReset();

        var tabLinkTag = tabLinkTags[0];
        tabLinkTag.style.backgroundColor = '#7ebb34';
        tabLinkTag.style.color = '#fff';

        var infoTab = document.getElementById('info');
        infoTab.style.display = 'block';

    } else if (tabVal == 'reviews') {

        displayNoneTabs();
        styleReset();

        var tabLinkTags = document.getElementsByClassName('tab-link');

        var tabLinkTag = tabLinkTags[1];
        tabLinkTag.style.backgroundColor = '#7ebb34';
        tabLinkTag.style.color = '#fff';

        var infoTab = document.getElementById('reviews');
        infoTab.style.display = 'block';

    } else if (tabVal == 'qna') {

        displayNoneTabs();
        styleReset();

        var tabLinkTags = document.getElementsByClassName('tab-link');

        var tabLinkTag = tabLinkTags[2];
        tabLinkTag.style.backgroundColor = '#7ebb34';
        tabLinkTag.style.color = '#fff';

        var infoTab = document.getElementById('qna');
        infoTab.style.display = 'block';

    }
}

function yOffsetSetting(yOffsetNum) {
    window.scroll(0, yOffsetNum);
}

window.onload = function (evt) {

    var plusButton = document.getElementsByClassName('plus')[0];
    var minusButton = document.getElementsByClassName('minus')[0];

    var countTag = document.getElementsByClassName('itemCount');
    var discountTag = document.getElementsByClassName('discountPrice');
    var discountValue = discountTag[0].innerHTML;
    var replace = discountValue.replace(',', '');
    var replaceNum = Number(replace);

    var totalTag = document.getElementsByClassName('total_price')[0];

    var countParam = document.getElementById('count');
    var countText = countParam.getAttribute('value');
    countNum = Number(countText);

    function totalPrice(price) {
        var countValue = Number(countTag[0].innerHTML);
        var total = price * countValue;
        var format = new Intl.NumberFormat().format(total);
        totalTag.innerHTML = format;

    }

    totalPrice(replaceNum);


    // 로딩 했을시에 yOffset 값 있으면 세팅
    var yOffsetSettingInput = document.getElementsByClassName('yOffset-setting')[0];
    var yOffsetNum = Number(yOffsetSettingInput.value);

    if (yOffsetNum > 0) {
        yOffsetSetting(yOffsetNum)
    }

    // 로딩 했을시에 탭 설정
    tabSetting();

    // 구매후기 좋아요 취소
    var likeReviewCancelBtns = document.getElementsByClassName('like-review-cancel-btn');

    for (i = 0; i < likeReviewCancelBtns.length; i++) {

        var likeReviewCancelBtn = likeReviewCancelBtns[i];

        likeReviewCancelBtn.addEventListener('click', function (evt) {

            var div = likeReviewCancelBtn.parentElement;
            var likeInner = div.parentElement;


            var reviewIdInput = likeInner.getElementsByTagName('input')[0];
            var reviewIdVal = Number(reviewIdInput.value);

            var memberIdInput = document.getElementsByClassName('memberId-input')[0];
            var memberIdVal = Number(memberIdInput.value);

            var formTag = document.createElement('form');
            formTag.method = 'post';
            formTag.action = '/likeReview/delete';

            var pageYOffset = window.pageYOffset;

            var inputTagPageYOffset = document.createElement('input');
            inputTagPageYOffset.type = 'hidden';
            inputTagPageYOffset.name = 'yOffset';
            inputTagPageYOffset.value = pageYOffset;

            var inputTagForReviewId = document.createElement('input');
            inputTagForReviewId.type = 'hidden';
            inputTagForReviewId.name = 'reviewId';
            inputTagForReviewId.value = reviewIdVal;

            var inputTagForMemberId = document.createElement('input');
            inputTagForMemberId.type = 'hidden';
            inputTagForMemberId.name = 'memberId';
            inputTagForMemberId.value = memberIdVal;

            var methodInput = document.createElement('input');
            methodInput.name = '_method';
            methodInput.value = 'delete';

            formTag.append(inputTagPageYOffset);
            formTag.append(inputTagForReviewId);
            formTag.append(inputTagForMemberId);
            formTag.append(methodInput);
            document.body.append(formTag);

            formTag.submit();

        })

    }

    // 구매후기 리뷰 좋아요
    var likeReviewBtns = document.getElementsByClassName('like-review-btn');

    for (i = 0; i < likeReviewBtns.length; i++) {

        var likeReviewBtn = likeReviewBtns[i];
        likeReviewBtn.addEventListener('click', function (evt) {

            var div = likeReviewBtn.parentElement;
            var likeInner = div.parentElement;
            var reviewIdInput = likeInner.getElementsByTagName('input')[0];
            var reviewIdVal = Number(reviewIdInput.value);

            var memberIdInput = document.getElementsByClassName('memberId-input')[0];
            var memberIdVal = Number(memberIdInput.value);

            if (memberIdVal == null || memberIdVal == "") {
                alert("로그인을 하셔야 좋아요를 누를 수 있습니다");
                return;
            }

            var formTag = document.createElement('form');
            formTag.method = 'post';
            formTag.action = '/likeReview';

            var pageYOffset = window.pageYOffset;

            var inputTagPageYOffset = document.createElement('input');
            inputTagPageYOffset.type = 'hidden';
            inputTagPageYOffset.name = 'yOffset';
            inputTagPageYOffset.value = pageYOffset;

            var inputTagForReviewId = document.createElement('input');
            inputTagForReviewId.type = 'hidden';
            inputTagForReviewId.name = 'reviewId';
            inputTagForReviewId.value = reviewIdVal;

            var inputTagForMemberId = document.createElement('input');
            inputTagForMemberId.type = 'hidden';
            inputTagForMemberId.name = 'memberId';
            inputTagForMemberId.value = memberIdVal;

            formTag.append(inputTagPageYOffset);
            formTag.append(inputTagForReviewId);
            formTag.append(inputTagForMemberId);

            document.body.append(formTag);
            formTag.submit();

        })

    }

    // modal 바깥 눌렀을 때 hidden
    var modal = document.getElementsByClassName('modal')[0];

    modal.addEventListener('click', function (evt) {
        modal.classList.remove('active');
    });

    var modalContent = modal.getElementsByClassName('modal-content')[0];
    modalContent.addEventListener('click', function (evt) {
        evt.stopPropagation();
    });

    // 리뷰 별점
    var ratingSpans = document.getElementsByClassName('rating');

    for (i = 0; i < ratingSpans.length; i++) {

        var ratingSpan = ratingSpans[i];
        var ratingInput = ratingSpan.getElementsByClassName('star-rating')[0];
        var ratingInputNum = Number(ratingInput.value);

        for (j = 0; j < 5; j++) {

            if (j < ratingInputNum) {
                ratingSpan.innerHTML += '<svg class="star" viewBox="0 0 64 64" xmlns="http://www.w3.org/2000/svg" aria-labelledby="title"\n' +
                    'aria-describedby="desc" role="img" xmlns:xlink="http://www.w3.org/1999/xlink">\n' +
                    '                                    <path d="M32 48L15 58l4-19L5 26l19-2 8-18 8 18 19 2-14 13 4 19-17-10z"\n' +
                    '                                          stroke-width="2" stroke-miterlimit="10" stroke-linecap="round" stroke="#7ebb34"\n' +
                    '                                          fill="#7ebb34" data-name="layer1" stroke-linejoin="round"></path>\n' +
                    '                                </svg>'
            } else {
                ratingSpan.innerHTML += '<svg class="star" viewBox="0 0 64 64" xmlns="http://www.w3.org/2000/svg" aria-labelledby="title"\n' +
                    'aria-describedby="desc" role="img" xmlns:xlink="http://www.w3.org/1999/xlink">\n' +
                    '                                    <path d="M32 48L15 58l4-19L5 26l19-2 8-18 8 18 19 2-14 13 4 19-17-10z"\n' +
                    '                                          stroke-width="2" stroke-miterlimit="10" stroke-linecap="round" stroke="#555555"\n' +
                    '                                          fill="none" data-name="layer1" stroke-linejoin="round"></path>\n' +
                    '                                </svg>'
            }
        }
    }

    plusButton.onclick = function () {
        var countValue = Number(countTag[0].innerHTML);
        countTag[0].innerHTML = countValue + 1;
        totalPrice(replaceNum);
        plusCount(countParam);

    };

    minusButton.onclick = function () {
        var countValue = Number(countTag[0].innerHTML);
        if (countValue == 1) {
            alert("상품 개수는 한 개 이상이여야 합니다");
            return;
        } else if (countValue > 1) {
            countTag[0].innerHTML = countValue - 1;
            totalPrice(replaceNum);
            minusCount(countParam);
        }

    };

    likeTypeSet();

    // search();

};