/**
 * Created by ben on 7/06/2017.
 */

/*(function() {

}());*/

$(document).ready(function() {
    "use strict";
    //window["counter"] = 0;

    // snackbar
    var snackbarContainer   = document.querySelector("#toast-container");
    //var showToastButton     = document.querySelector("#show-toast");

    // key value stuff
    var max_fields          = 10; //maximum input boxes allowed
    var wrapper             = $(".wrapper_input_spawner"); //Fields wrapper
    var add_button          = $(".btn_add_kv"); //Add button ID
    var x                   = 1; //initial text box count

/*    showToastButton.addEventListener("click", function () {
        "use strict";
        var data = {message: "Example Message # " + ++counter};
        snackbarContainer.MaterialSnackbar.showSnackbar(data);
    });*/

    // process the form
    $("#send-pubsub-message-form").submit(function (event) {

        // get the form data
/*        var formData = {
            "topic": $("select[name=topic]").val(),
            "payload": $("input[name=payload]").val(),
            "attributes": $("input[name=attributes]").val()
        };*/

        var formData =  $("#send-pubsub-message-form").serializeArray();

        //console.log(formData);

        // process the form
        $.ajax({
            type: "POST",
            url: "/pubsub/publish",
            data: formData,
            //dataType: "json", // what type of data do we expect back from the server
            encode: true
        })
        // using the done promise callback
        .done(function (response) {
            console.log(response.status);
            var data1 = {message: "message sent!"};
            snackbarContainer.MaterialSnackbar.showSnackbar(data1);
        })
        .fail(function (response) {
            console.log(response.status);
        })
        .always(function (response) {
            console.log("always? ...always");
            // clear the form
            $("input[type=input]").val("");
        });

        // stop the form from submitting the normal way and refreshing the page
        event.preventDefault();
    });

    // button for adding key/value fields
    $(add_button).click(function (e) { //on add input button click
        e.preventDefault();

        if (x < max_fields) { //max input box allowed
            x++; //text box increment
            $(wrapper).append(
                "<div class='mdl-grid remove_me'>" +
                "<div class='mdl-textfield my-textfield-wrapper mdl-cell mdl-cell--6-col'>" +
                "<input class='my-input-field' id='attribute_key_" + x + "' type='input' name='key' placeholder='key' required>" +
                "</div>" +
                "<div class='mdl-textfield my-textfield-wrapper mdl-cell mdl-cell--5-col'>" +
                "<input class='my-input-field' id='attribute_value_" + x + "' type='input' name='value' placeholder='value' >" +
                "</div>" +
                "<div class='mdl-textfield my-textfield-wrapper mdl-cell mdl-cell--1-col'>" +
                "<input class='btn_remove_this' type='button' name='delete' value='X'>" +
                "</div>" +
                "</div>"
            );
        }

        componentHandler.upgradeDom(".wrapper_all_inputs");
    });

    $(wrapper).on("click", ".btn_remove_this", function (e) { //user click on remove btn
        e.preventDefault();
        $(this).closest("div.remove_me").remove();
        x--;
    })
})