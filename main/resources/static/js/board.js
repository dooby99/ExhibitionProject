$(function () {
    $('#writeBtn').on('click', function () {

      let subject = $('#subject').val();
      if (subject.trim() == "") {
        alert("제목을 입력해주세요.");
        $('#subject').focus();
        return false;
      }

      let content = $('#content').val();
      if (content.trim() == "") {
        alert("내용을 입력해주세요");
        $('#content').focus();
        return false;
      }
      $('#frm').submit();
    })
  })