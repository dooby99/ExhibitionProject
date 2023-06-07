let Id;
let boardNo;

let commId;
let originContent;

$(document).ready(function () {

    Id = $('#memNo').val();
    boardNo = $('#boardId').val();

    id2 = parseInt(Id);
    boardNo2 = parseInt(boardNo);

    // getCommentList(boardNo);
    getCommentList(boardNo2);

    $('#comWriteBtn').on("click", function () {
        let content = $('#comContent').val();
        if (content === "" || content.length == 0) {
            alert("내용을 입력해주세요.");
        } else {
            writeComment(content);
            console.log("댓글작성")
        }
    });
});

function deleteComment(btn) {
    console.log("삭제버튼")
    let conf = confirm("정말로 삭제하시겠습니까?");
    if (conf) {
        commId = btn.parentNode.parentNode.childNodes[0].children[0].value;

        $.ajax({
            url: "/community/comment/delete",
            type: "post",
            data: {
                "Id": commId
            },

            success: function (result) {
                if (result === "success") {
                    getCommentList(boardNo);
                } else {
                    alert("문제가 발생했습니다.");

                }
            }
        });
    }
}

function getCommentList(boardNo2) {
    console.log("list board : " + boardNo2);
    $.ajax({
        url: "/community/comment/list",
        type: "GET",
        data: {
            'boardNo': boardNo2
        },
        dataType: "json",
        success: function (result) {
            console.log("commentList");
            console.log(result);
            $("#comList").empty();
            result.forEach(e => {
                var info =
                    "<div>"
                    + "<span>" + "작성자 : " + e.writerName + "</span>"
                    + "<span>" + "   |    내용 : " + e.content + "</span>"
                    + " </div>";
                // $("#comList").append(info);
                $("<div class='commentBox'>").html(info).appendTo("#comList");
            });
        }, error: function () {
            console.log("error11111111111111");
        }
    });
}

function writeComment(content) {
    console.log("boardNo " + typeof (boardNo2));
    console.log("Id " + typeof (id2));
    console.log("content " + typeof (content));

    $.ajax({
        url: "/community/comment/write",
        type: "GET",
        data: {
            'content': content,
            'Id': id2,
            'boardNo': boardNo2
        },
        success: function () {
            console.log("writeComment");
            $('#comContent').val("");
            let href = location.origin;
            href += "/community/comment/list?boardNo=";
            href += $.param({"boardNo": boardNo2});
            location.origin = href;
            console.log("gd");
            getCommentList(boardNo2);
        },
        error: function () {
            alert("error!!!!!");
        }
    });
}

function CheckMemberId(Id) {
    let idInputArr = document.getElementsByClassName('n');
    $.ajax({
        url: "/community/comment/checkId?Id=" + Id,
        type: "get",
        success: function (result) {
            for (let i = 0; i < result.length; i++) {
                for (let j = 0; j < idInputArr.length; j++) {
                    if (result[i].Id == idInputArr[j].value) {
                        console.log("checkMemberId")
                        let td = idInputArr[j].parentNode.parentNode.children[4]; // 0부터 센다.
                        let inner =
                            "<button class='modifyBtn' onclick='openUpdateForm(this)' type='button'>수정</button>" +
                            "<button class='deleteBtn' onclick='deleteComment(this)'  type='button'>삭제</button>";
                        td.innerHTML = inner;
                    }
                }
            }
        }, error: function (request, status) {
            console.warn("code : " + status + ", message : " + request.responseText);
        }
    });
}




