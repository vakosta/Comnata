let stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    let socket = new SockJS('http://127.0.0.1:8188/ws'); // Это адрес для подключения к WebSocket.

    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);

        // "/user/topic/test" — адрес, который будет проверяться на наличие обновлений.
        stompClient.subscribe('/user/topic/test', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
        // "/topic/test" — адрес, который будет проверяться на наличие обновлений.
        stompClient.subscribe('/topic/test', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });

    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }

    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    // "/app/testUser" — адрес, на который будут отправляться сообщения.
    stompClient.send("/app/testUser", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendName();
    });
});