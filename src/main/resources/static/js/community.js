function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    if (content.trim() === "") {
        alert("评论不能为空");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parent_id": questionId,
            "content": content,
            "isChildComment": 0
        }),
        success: function (response) {
            alert(response.message);
            window.location.reload();
        },
        dataType: "json"
    });
}

function childPost() {
    var commentId = $("#comment_id").val();
    var pointCommentIds=$("#comment_ids");
    pointCommentIds.val([]);
    var content = $("#childComment_content").val();
    if (content.trim() === "") {
        alert("评论不能为空");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parent_id": commentId,
            "content": content,
            "isChildComment": 1
        }),
        success: function (response) {
            alert(response.message);
            window.location.reload();
        },
        dataType: "json"
    });
}

function reply(account) {
    var content = $("#childComment_content").val();
    $("#childComment_content").val(content + "@" + account+":");
}

function questionLike(id){
    $.ajax({
        type: "POST",
        url: "/question",
        contentType: 'application/json',
        data: JSON.stringify({
            "id": id
        }),
        success: function () {
            window.location.reload();
        }
    });
}

function commentLike(id){
    $.ajax({
        type: "POST",
        url: "/commentLike",
        contentType: 'application/json',
        data: JSON.stringify({
            "id": id
        }),
        success: function () {
            window.location.reload();
        }
    });
}