$('#slider-range').tooltip({disabled: true});

$('.btn-recommend').hover(function() {  // mouse enter
    $(this).css({"color": "white", "background-color": "black"});
}, function() {
    $(this).css({"color": "black", "background-color": "transparent"});
});

$('.btn-move').hover(function() {  // mouse enter
    $(this).css({"color": "white", "background-color": "black"});
}, function() {
    $(this).css({"color": "black", "background-color": "transparent"});
});


$('.closeEvent').on("click", function() {
    location.href="/";
});

var checked_list = []

$('input[name="checkBx"]').change(function() {
    var value = $(this).val();
    var checked = $(this).prop('checked');
    var $label = $(this).next();
    
    // debug
    //console.log($(this).next().children('h2'));

    if(checked) {
        $label.css('background-color', '#0083CB');
        checked_list.push(value);   // 추가

        $label.children('h2').css({'color': 'white'});
        $label.children('p').css({'color': 'white'});
    }
    else {
        $label.css('background-color', '#ededf1');
        
        // 삭제
        let index = checked_list.indexOf(value);
        if(index >= 0) {
            checked_list.splice(index, 1);
        }

        $label.children('h2').css({'color': 'black'});
        $label.children('p').css({'color': 'black'});
    }
    //console.log('check list : ', checked_list);
});
// 기본 항목
// 연소득, 대출한도, 인원수, 외형

// 인원수 
$('#div-person-minus').click(function() {
    let txt = $('#person-cnt').text();
    let people = Number(txt) - 1;
    if(people < 0) {
        people = 0;
    }

    $('#person-cnt').text(String(people));
});


$('#div-person-plus').click(function() {
    let txt = $('#person-cnt').text();
    let people = Number(txt) + 1;
    if(people >= 10) {
        people = 9;
    }

    $('#person-cnt').text(String(people)); 
});

// 외형
var car_body = 'common';

$('.body-btn').click(function() {
    let body = $(this).val();
    if(car_body != body) {
        // 기존 거 색깔 변형
        $('#'+car_body).css({"color": "white", "background-color": "#B38664"});

        // update
        car_body = body;
        $(this).css({"color": "#B38664", "background-color": "white"});
    }
});

// 연소득

var $form = $("#form");
var $input = $form.find("input");
var yearIncome = 0;

$input.on("keyup", function(event) {
    // 사용자가 selection을 하고 있고, selection된 내용이 blank가 아닌 경우 종료
    var selection = window.getSelection().toString();
    if (selection !== '') {
        return;
    }
 
    // keyup 이벤트가 방향키인 경우 종료
    if ($.inArray(event.keyCode, [38,40,37,39]) !== -1 ) {
        return;
    }
  
    var $this = $(this);
    var input = $this.val();
 
    // 정수 추출
    var input = input.replace(/[\D\s\._\-]+/g, "");
    input = input ? parseInt(input, 10) : 0;
    yearIncome = input;
 
    // 1000단위 별로 콤마 출력
    $this.val( function() {
        return (input === 0) ? "" : input.toLocaleString("en-US");
    });
});

$("#slider-range").slider({
    min: 100,
    max: 6000,
    step: 10,
    range: true,
    values: [100, 6000],
    slide: function(event, ui) {
        var low, high;
        low = $(this).slider("values", 0);
        high = $(this).slider("values", 1);
       // $(this).tooltip("option", "content", low + ":" + high);

        $('#min-price').text(low+' 만원');
        $('#max-price').text(high+' 만원');
    }
});

$("#slider-range").tooltip({
    items: ".ui-slider",
    content: "0:1000",
    position: {
        my: "center bottom-20",
        at: "center top",
        using: function(position, feedback) {
        $(this).css(position);
        $("<div>")
            .addClass("arrow")
            .addClass(feedback.vertical)
            .addClass(feedback.horizontal)
            .appendTo(this);
        }
    }
});

function continueFunction() {
    // 연소득
    if(yearIncome <= 0) {   // 연소득 입력 X
        alert("연소득을 입력하세요.");
        return;
    }
    // console.log('연소득 : ', yearIncome);

    // 대출한도 : default 100만원 ~ 6000만원
    var minLimit = parseInt($('#min-price').text())*10000;
    var maxLimit = parseInt($('#max-price').text())*10000;
    // console.log('최소 : ', minLimit, " ~  최대 : ", maxLimit);

    // 인원 수 
    var person = parseInt($('#person-cnt').text());
    // console.log("인원 수 : ", person, " 명")

    // 외형
    // console.log("외형 : ", car_body);
    location.href = 'taste?user-income=' + yearIncome + '&min=' + minLimit + '&max=' + maxLimit +'&people=' + person + '&body-type=' + car_body;
}



function recommendFunction(income, minimum, maximum, people, type) {
    if(checked_list.length == 2 || checked_list.length == 3) {
        console.log(checked_list);
        var dictObject = {};
        dictObject['e-protection'] = 'N';
        dictObject['fuel-efficiency'] = 'N';
        dictObject['boycott-in-japan'] = 'N';
        dictObject['patriotic'] = 'N';
        dictObject['vegan'] = 'N';

        for (var i = 0; i < checked_list.length; i++){
            dictObject[checked_list[i]] = 'Y';
        }

        var form = document.createElement("form");
        var parm = new Array();
        var input = new Array();

        form.action = "car/recommend";
        form.method = "post";

        parm.push(['user-income', income]);
        parm.push(['min', minimum]);
        parm.push(['max', maximum]);
        parm.push(['people', people]);
        parm.push(['body-type', type]);
        parm.push(['environmental-protection', dictObject['e-protection']]);
        parm.push(['fuel-economy', dictObject['fuel-efficiency']]);
        parm.push(['boycott-in-japan', dictObject['boycott-in-japan']]);
        parm.push(['patriotic-campaign', dictObject['patriotic']]);
        parm.push(['vegan', dictObject['vegan']]);

        for (var i = 0; i < parm.length; i++) {
            input[i] = document.createElement("input");
            input[i].setAttribute("type", "hidden");
            input[i].setAttribute("name", parm[i][0]);
            input[i].setAttribute("value", parm[i][1]);
            form.appendChild(input[i]);
        }
        document.body.appendChild(form);
        form.submit();
    } else {
        alert("2개 또는 3개만 선택하세요");
    }
}
