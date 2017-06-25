/**
 * Created by ben on 7/06/2017.
 */

$(document).ready(function() {
    "use strict";
    //window["counter"] = 0;

    var jsonExample =
        {
            "data": "test123",
            "attributes": {
                "EventType": "Token",
                "EventSource": "Event"
            }
        };

    // using JSON.stringify pretty print capability:
    var jsonExamplePretty = JSON.stringify(jsonExample, undefined, 4);

    $("#json-input").html(jsonExamplePretty);

    // snackbar
    var snackbarContainer = document.querySelector("#toast-container");

    // key value stuff
    var max_fields              = 10; //maximum input boxes allowed
    var wrapperAttributes       = $(".wrapper_all_inputs");
    var wrapperAttributesSpawn  = $(".wrapper_input_spawner");
    var btn_add                 = $(".btn_add_kv"); //Add button ID
    var x                       = 1; //initial text box count

    // hide input fields which are out of focus
    $(".mdl-tabs__tab").on("click", function () {
        // get inactive & active panels
        var $inactivePanel  = $(".mdl-tabs__panel").not(".is-active");
        var $activePanel    = $(".mdl-tabs__panel.is-active");

        // show active & hide inactive
        $(":input", $inactivePanel).hide().prop("required", false).prop("disabled", true);
        $(":input", $activePanel).show().prop("required", true).prop("disabled", false);
    });

    // process the form
    $("#send-pubsub-message-form").submit(function (event) {

        var message;

        // get input data from active tab
        var formData = $(this).serializeArray();

        console.log(formData);

        // process the form
        $.ajax({
            type: "POST",
            url: $(this).attr("action"),
            data: formData,
            //dataType: "json", // what type of data do we expect back from the server
            encode: true
        })
        // using the done promise callback
        .done(function (response) {
            console.log(response.status);

            message = {message: "message sent!"};
            snackbarContainer.MaterialSnackbar.showSnackbar(message);
        })
        .fail(function (response) {
            console.log(response.status);

            message = {message: "failed to send!" + response.status};
            snackbarContainer.MaterialSnackbar.showSnackbar(message);
        })
        .always(function (response) {
            console.log("always? ...always");
            // clear the form
            $("input[type=input]").val("");
        });

        // stop the form from submitting the normal way and refreshing the page
        event.preventDefault();
    });

    $("#refresh-receive-events").submit(function (topic) {
        // get input data
        var data = $("#topicForReceive").serializeArray();

        console.log(data);

        // process the form
        $.ajax({
            type: "POST",
            url: $(this).attr("action"),
            data: data,
            //dataType: "json", // what type of data do we expect back from the server
            encode: true
        })
        // using the done promise callback
        .done(function (response) {
            console.log(response.status);
        })
        .fail(function (response) {
            console.log(response.status);
        })
        .always(function (response) {
            console.log("always? ...always");
        });

        // stop the form from submitting the normal way and refreshing the page
        topic.preventDefault();
    });

    // button for adding key/value fields
    $(btn_add).click(function (e) { //on add input button click
        e.preventDefault();

        if (x < max_fields) { //max input box allowed
            x++; //text box increment
            $(wrapperAttributesSpawn).append(
                "<div class='mdl-grid remove_me'>" +
                "<div class='mdl-textfield my-textfield-wrapper mdl-cell mdl-cell--6-col'>"
                +
                "<input class='my-input-field' id='attribute_key_" + x
                + "' type='input' name='key' placeholder='key' required>" +
                "</div>" +
                "<div class='mdl-textfield my-textfield-wrapper mdl-cell mdl-cell--5-col'>"
                +
                "<input class='my-input-field' id='attribute_value_" + x
                + "' type='input' name='value' placeholder='value' >" +
                "</div>" +
                "<div class='mdl-textfield my-textfield-wrapper mdl-cell mdl-cell--1-col'>"
                +
                "<input class='btn_remove_this' type='button' name='delete' value='X'>"
                +
                "</div>" +
                "</div>"
            );
        }

        componentHandler.upgradeDom(".wrapper_all_inputs");
    });

    //user click on remove btn
    $(wrapperAttributes).on("click", ".btn_remove_this", function (e) {
        e.preventDefault();
        $(this).closest("div.remove_me").remove();
        x--;
    });
})