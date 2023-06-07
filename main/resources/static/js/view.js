$(document).ready(function () {
    $('#deleteBtn').on("click", function () {
        let conf = confirm("정말 삭제하시겠습니까?");
        if (conf) {
            $('#impDeleteForm').submit();
        }
    });

    $('#list_back').on("click", function () {
        window.location.href = "/community"
    })
});