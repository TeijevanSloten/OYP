var attachmentsint = 1;
function myFunction() {
    $("#attachmentbutton").before(
            '<span style="display: inline; width: 300px;">' +
            '<input type="file" name="attachment[' + attachmentsint + ']"></span><br>');
    attachmentsint++;
}
$(document).ready(function () {
    $(".select-to").select2({
        minimumInputLength: 1,
        tags: true,
        placeholder: "To",
        tokenSeparators: [',', ' ']
    });
    $(".select-cc").select2({
        minimumInputLength: 1,
        tags: true,
        placeholder: "CC",
        tokenSeparators: [',', ' ']
    });
    $(".select-bcc").select2({
        minimumInputLength: 1,
        tags: true,
        placeholder: "BCC",
        tokenSeparators: [',', ' ']
    });
    CKEDITOR.config.height = 600;
    CKEDITOR.replace('textareaInput');
});