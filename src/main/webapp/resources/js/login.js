$().ready(function () {
    $("#lang").on("change", function(){
        window.location='login.xhtml?lang=' + $(this).val();
    });

    $("#login_form").validate();
    $("#employee_email").focus();
    jQuery.extend(jQuery.validator.messages, {
        required: "",
        email: "Invalid email address"
    });
});