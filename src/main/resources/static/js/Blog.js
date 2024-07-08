function submitComment(blogID) {
    var content = document.getElementById("content" + blogID).value;
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/blog/submitComment?blogID=" + blogID + "&content=" + encodeURIComponent(content), true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var comment = JSON.parse(xhr.responseText);
            document.getElementById("content" + blogID).value = "";
            console.log(comment);
            showCommentAdded(comment, blogID);
             // Clear input field after submission
        }
    };
    xhr.send();
}



function showCommentAdded(comment, blogID) {
    var commentList = document.querySelector('.comment');
    var newComment = document.createElement('li');
    newComment.className = 'comment';
    newComment.id = 'comment' + comment.commentID;

    var vcardDiv = document.createElement('div');
    vcardDiv.className = 'vcard bio';

    var avatarImg = document.createElement('img');
    avatarImg.src = '/images/' + comment.user.avatar;
    avatarImg.alt = 'Image placeholder';
    vcardDiv.appendChild(avatarImg);

    var commentBodyDiv = document.createElement('div');
    commentBodyDiv.className = 'comment-body';

    var usernameH3 = document.createElement('h3');
    usernameH3.textContent = comment.user.username;
    commentBodyDiv.appendChild(usernameH3);

    var metaDiv = document.createElement('div');
    metaDiv.className = 'meta';
    metaDiv.textContent = comment.commentDate;
    commentBodyDiv.appendChild(metaDiv);

    var contentP = document.createElement('p');
    contentP.textContent = comment.content;
    commentBodyDiv.appendChild(contentP);

    var deleteButton = document.createElement('button');
    deleteButton.setAttribute("onclick", `deleteComment(${comment.commentID})`);
    deleteButton.className = 'btn btn-danger btn-sm';
    deleteButton.style.float = 'right';

    var removeIcon = document.createElement("i");
    removeIcon.className = "fa-solid fa-trash";
    deleteButton.appendChild(removeIcon);
    deleteButton.appendChild(document.createTextNode(" Xóa"));
    commentBodyDiv.appendChild(deleteButton);
    newComment.appendChild(vcardDiv);
    newComment.appendChild(commentBodyDiv);

    // Append the new comment to the comment list
    commentList.appendChild(newComment);

    var firstChild = commentList.firstChild;
    commentList.insertBefore(newComment, firstChild);
}

function deleteComment(commentID) {
    if (confirm("Bạn có đồng ý xóa bình luận này không?")) {
        var xhr1 = new XMLHttpRequest();
        xhr1.open("POST", "/blog/deleteComment?commentID=" + commentID, true);
        xhr1.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr1.onreadystatechange = function () {
            if (xhr1.readyState === 4 && xhr1.status === 200) {
                var commentDiv = document.getElementById('comment' + commentID);
                if (commentDiv) {
                    var commentList = commentDiv.parentNode;
                    commentList.removeChild(commentDiv);
                }
            }
        };
        xhr1.send();
    }
}



