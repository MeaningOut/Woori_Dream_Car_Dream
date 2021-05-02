/**
 * Template Name: Siimple - v4.1.0
 * Template URL: https://bootstrapmade.com/free-bootstrap-landing-page/
 * Author: BootstrapMade.com
 * License: https://bootstrapmade.com/license/
 */
(function() {
    "use strict";

    /**
     * Easy selector helper function
     */
    const select = (el, all = false) => {
        el = el.trim()
        if (all) {
            return [...document.querySelectorAll(el)]
        } else {
            return document.querySelector(el)
        }
    }

    /**
     * Easy event listener function
     */
    const on = (type, el, listener, all = false) => {
        let selectEl = select(el, all)
        if (selectEl) {
            if (all) {
                selectEl.forEach(e => e.addEventListener(type, listener))
            } else {
                selectEl.addEventListener(type, listener)
            }
        }
    }

    /**
     * Mobile nav toggle
     */
    const toogleNav = function() {
        let navButton = select('.nav-toggle')
        navButton.classList.toggle('nav-toggle-active')
        navButton.querySelector('i').classList.toggle('bx-x')
        navButton.querySelector('i').classList.toggle('bx-menu')

        select('.nav-menu').classList.toggle('nav-menu-active')
    }
    on('click', '.nav-toggle', function(e) {
        toogleNav();
    })

    /**
     * Mobile nav dropdowns activate
     */
    on('click', '.nav-menu .drop-down > a', function(e) {
        e.preventDefault()
        this.nextElementSibling.classList.toggle('drop-down-active')
        this.parentElement.classList.toggle('active')
    }, true)

    /**
     * Scrool links with a class name .scrollto
     */
    on('click', '.scrollto', function(e) {
        if (select(this.hash)) {
            select('.nav-menu .active').classList.remove('active')
            this.parentElement.classList.toggle('active')
            toogleNav();
        }
    }, true)

})()

// 만원 단위로 치환
function numberFormat(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function numberToKorean(number){
    var inputNumber  = number < 0 ? false : number;
    var unitWords    = ['', '만', '억', '조', '경'];
    var splitUnit    = 10000;
    var splitCount   = unitWords.length;
    var resultArray  = [];
    var resultString = '';

    for (var i = 0; i < splitCount; i++){
        var unitResult = (inputNumber % Math.pow(splitUnit, i + 1)) / Math.pow(splitUnit, i);
        unitResult = Math.floor(unitResult);
        if (unitResult > 0){
            resultArray[i] = unitResult;
        }
    }

    for (var i = 0; i < resultArray.length; i++){
        if(!resultArray[i]) continue;
        resultString = String(numberFormat(resultArray[i])) + unitWords[i] + resultString;
    }

    let index = resultString.indexOf('만');
    if(index > 0) {
        resultString = resultString.slice(0, index);
    }

    return resultString;
}

// 동적으로 태그 추가
function make_card(id, img_src, name, big_title, company, company_logo, loan, min_price, max_price) {
    loan = numberToKorean(loan);
    min_price = numberToKorean(min_price);
    max_price = numberToKorean(max_price);

    var $new = '<div class="col-lg-4 col-md-6 d-flex align-items-stretch">'+
        '<div class="card"><img src=' + img_src + ' class="card-img-top">' +
        '<div class="card-icon"><i class="bx bx-book-reader" onclick="detailsFunction(\'' + id + '\')"></i></div>'+
        '<div class="card-body"><h3 class="card-title">' + big_title +' <span>' + name +'</span></h3>'+
        '<div><span>' + company + '</span><img src='+ company_logo +'></div>' +
        '<span class="card-inner">대출한도 <b>'+loan+'만원</b></span>'+
        '<p class="card-inner">신차가격 <b>'+min_price+'만원 ~ '+max_price+'만원</b></p></div></div>';

    $('#card_row').append($new);
}

function detailsFunction(id) {
    location.href = id + '/picture'
}
