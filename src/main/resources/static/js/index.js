$(document).ready(function() {
    loadHtml();
})

function loadHtml() {
    clearVariable();
    getMemoList();
}

function clearVariable() {
    $('.contents-area').empty();
    $('#search-memo-text').empty();
    $('#create-memo-text').empty();
}

function getMemoList() {
    $.ajax({
        type: 'GET',
        url: '/memo',
        success: function (response) {
            if (Array.isArray(response)) {
                response.forEach(function(memo) {
                    const createdAt = formatDateTime(memo.createdAt);
                    addMemoHtml(memo.id, memo.contents, createdAt);
                })
            }
        }
    })
}

function addMemoHtml(id, contents, createdAt) {
    const memoHtml = `
        <div class="contents-area-memo common-width">
            <div class="contents-area-memo-content">
                <div class="contents-area-memo-content-createdAt" id="${id}-createdAt"> ${createdAt} </div>
                <div class="contents-area-memo-content-contents" id="${id}-contents"> ${contents} </div>
                <div contenteditable class="contents-area-memo-content-edit-contents" id="${id}-edit-memo-text">${contents}</div>
            </div>
            <div class="contents-area-memo-option">
                <span id="${id}-edit" class="material-icons" onClick="changeUpdateMemoForm(${id})">edit</span>
                <span id="${id}-edit-done" class="material-icons" onClick="updateMemo(${id})">done</span>
                <span id="${id}-delete" class="material-icons" onClick="deleteMemo(${id})">delete</span>
            </div>
        </div>
    `;
    $('.contents-area').append(memoHtml);
    $(`#${id}-edit-memo-text, #${id}-edit-done`).addClass('hidden');

}

function searchMemo() {
    const searchText = $('#search-memo-text').text();

    $.ajax({
        type: 'GET',
        url: '/memo/search',
        data: { keyword: searchText },
        success: function (response) {
            $('.contents-area').empty();
            if (Array.isArray(response)) {
                response.forEach(function(memo) {
                    const createdAt = formatDateTime(memo.createdAt);
                    addMemoHtml(memo.id, memo.contents, createdAt);
                })
            }
        }
    })
}

function createMemo() {
    const createMemoText = $('#create-memo-text').html().replace(/\n/g, "<br>");

    if (isValidContents(createMemoText)==false) return;

    $.ajax({
        type: 'POST',
        url: '/memo',
        contentType: 'application/json',
        data: JSON.stringify({ contents: createMemoText }),
        success: function (response) {
            window.location.reload();
        }
    })
}

function changeUpdateMemoForm(id) {
    $(`#${id}-contents, #${id}-edit, #${id}-delete`).addClass('hidden');
    $(`#${id}-edit-memo-text, #${id}-edit-done`).removeClass('hidden');
}

function updateMemo(id) {
    const updateMemoText = $(`#${id}-edit-memo-text`).html().replace(/\n/g, "<br>");


    if (isValidContents(updateMemoText)==false) return;

    $.ajax({
        type: 'PUT',
        url: `/memo/${id}`,
        contentType: 'application/json',
        data: JSON.stringify({ contents: updateMemoText }),
        success: function (response) {
            window.location.reload();
        }
    })
}

function deleteMemo(id) {
    const deleteMemoId = Number(id);

    if(window.confirm("메모를 삭제하시겠습니까?")){
        $.ajax({
            type: 'DELETE',
            url: `/memo/${deleteMemoId}`, // 주의! js에서 템플릿 문자열을 이용할 때는 백틱(`)을 이용해야함
            success: function (response) {
                window.location.reload();
            }
        })
    }
}

function formatDateTime(localDateTime) {
    // JavaScript Date 객체 생성
    const date = new Date(localDateTime);

    // 년, 월, 일, 시, 분, 초 추출
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    const hour = date.getHours();
    const minute = date.getMinutes();
    const second = date.getSeconds();

    // 원하는 형식으로 변환하여 반환
    return `${year}/${month}/${day} ${hour}:${minute}:${second}`;
}

function isValidContents(contents) {
    console.log(contents);
    if (contents == '') {
        alert('내용을 입력해주세요');
        return false;
    }
    if (contents.length > 1000) {
        alert('공백 포함 1000자 이하로 입력해주세요');
        return false;
    }
    return true;
}

function handleSearchKeyPress(event) {
    if (event.key === 'Enter') {
        event.preventDefault();
        searchMemo();
    }
}
