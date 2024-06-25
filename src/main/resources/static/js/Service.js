function addFeedback(idService) {
    var content = document.getElementById("content" + idService).value;
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/service/addFeedback?service=" + idService + "&content=" + content, true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {

            var feedback = JSON.parse(xhr.responseText);
            showFeedbackAdded(feedback, idService);
            document.getElementById("content" + idService).value = ""; // Clear input field after submission

            // Kiểm tra và ẩn thông báo "Không có bình luận" nếu có bình luận được thêm vào
            var noCommentMessage = document.querySelector(`#modal${idService} .media-comment > p`);
            if (noCommentMessage && document.querySelectorAll(`#modal${idService} .media-comment > div`).length > 0) {
                noCommentMessage.style.display = 'none';
            }
        }
    };
    xhr.send();
}

function showFeedbackAdded(feedback, idService) {
    var feedbackList = document.querySelector(`#modal${idService} .media-comment`).parentElement;

    var feedbackDiv = document.createElement("div");
    feedbackDiv.className = "media g-mb-30 media-comment";

    var img = document.createElement("img");
    img.className = "rounded-circle shadow-1-strong me-3";
    img.src = '/images/' + feedback.user.avatar;
    img.width = 65;
    img.height = 65;
    img.style.display = "inline-flex";
    img.onerror = function () {
        this.onerror = null;
        this.src = '/images/UserAvatarDefault.jpg';
    };

    var mediaBodyDiv = document.createElement("div");
    mediaBodyDiv.className = "media-body u-shadow-v18 g-bg-secondary g-pa-30";

    var headerDiv = document.createElement("div");
    headerDiv.className = "g-mb-15";

    var userNameP = document.createElement("p");
    userNameP.className = "h5 g-color-gray-dark-v1 mb-0";
    userNameP.style.display = "inline-flex";
    userNameP.textContent = feedback.user.username;

    var dateP = document.createElement("p");
    dateP.style.color = "rgba(0, 0, 0, .54)";
    dateP.style.fontSize = ".75rem";
    dateP.textContent = feedback.date;

    var contentP = document.createElement("p");
    contentP.style.paddingTop = "10px";
    contentP.textContent = feedback.content;

    headerDiv.appendChild(userNameP);
    headerDiv.appendChild(dateP);

    mediaBodyDiv.appendChild(headerDiv);
    mediaBodyDiv.appendChild(contentP);

    feedbackDiv.appendChild(img);
    feedbackDiv.appendChild(mediaBodyDiv);

    if (!(document.querySelector(`#modal${idService} .noneComment`))) {
        var firstChild = feedbackList.firstChild;
        feedbackList.insertBefore(feedbackDiv, firstChild);
    } else {
        feedbackList.appendChild(feedbackDiv); // Nếu danh sách rỗng, thêm vào cuối danh sách
    }
}
