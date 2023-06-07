$(document).ready(function () {
    console.log("start");

    // 게시글 작성시 유효성 검사
    $('#writeBtn').on("click", function () {
        console.log("hihihihihihihihihihihihihihihihihi");
        let title = $('#subject').val();
        let writerNo = $('#writerNo').val();
        let content = $('#content').text();
        // let text = $('#content').text();

        if (title.trim().length == 0 || text.trim().length == 0) {
            alert("제목이나 내용은 비어있을 수 없습니다.");
        } else if (content.length > 2048) {
            alert("최대 2048자 까지 쓸 수 있습니다.");
        } else {
            $.ajax({
                url: "/community/writeProc",
                type: 'post',
                dataType: 'text',
                data: {
                    'title': title,
                    'writerNo': writerNo,
                    'content': content
                },
                success: function (data) {
                    console.log(data)
                    location.href = "/community/" + data;
                },
                error: function (request, status) {
                    alert("문제가 발생했습니다. 지속될 경우 관리자에 문의바랍니다." + "title : " + title);
                    console.warn("code : " + status + "\nmessage : " + request.responseText);
                }
            });
        }
    });
});