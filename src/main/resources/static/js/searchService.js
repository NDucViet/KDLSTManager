function searchService() {
    var keyword = document.getElementById("keywordService").value.trim();
    var sanitizedKeyword = keyword.replace(/[^\w\s]/gi, '');
    if (sanitizedKeyword === '') {
        alert('Bạn chưa nhập từ khóa. (Không tính các ký tự đặc biệt vào độ dài từ khóa)');
    } else {
        var url = "/service/searchService?keyword=" + sanitizedKeyword;

        window.location.href = url;
    }
}

function handleSearchService(event) {
    if (event.key === 'Enter') {
        searchService();
    }
}

let timeout1 = null;

function handleInputService() {
    clearTimeout(timeout);
    timeout1 = setTimeout(() => {
        var keyword = document.getElementById("keywordService").value.trim();
        if (keyword.length >= 1) {
            var url = "/service/serviceSuggestion?keyword=" + keyword;
            var xhr = new XMLHttpRequest();
            xhr.open("GET", url, true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    var suggestions = JSON.parse(xhr.responseText);
                    displaySuggestions1(suggestions);
                }
            };
            xhr.send();
        }
        // Kiểm tra xem keyword có rỗng không sau khi nhận kết quả từ server
        if (keyword === "") {
            var suggestionsDiv = document.getElementById("suggestionBoxForService");
            suggestionsDiv.style.display = "none"; // Ẩn các gợi ý khi không có ký tự nào trong ô input
        }
    }, 500)
}

function displaySuggestions1(suggestions) {
    var suggestionsDiv = document.getElementById("suggestionBoxForService");
    suggestionsDiv.innerHTML = ""; // Xóa nội dung cũ
    for (var i = 0; i < suggestions.length; i++) {
        var suggestion = document.createElement("button");
        suggestion.setAttribute("data-bs-toggle", "modal");
        suggestion.setAttribute("data-bs-target", "#modal" + suggestions[i].serviceID);
90
        // Tạo phần tử <img> cho ảnh phòng
        var roomImage = document.createElement("img");
        roomImage.src = "images/" + suggestions[i].image;
        suggestion.appendChild(roomImage); // Thêm ảnh vào phần tử suggestion

        // Tạo phần tử <span> cho tên phòng
        var roomName = document.createElement("span");
        roomName.textContent = suggestions[i].serviceName + "-" + suggestions[i].serviceTypeID.serviceName; // Thay "name" bằng trường thông tin bạn muốn hiển thị
        suggestion.appendChild(roomName); // Thêm tên phòng vào phần tử suggestion

        suggestionsDiv.appendChild(suggestion);
    }
    suggestionsDiv.style.display = suggestions.length > 0 ? "block" : "none"; // Hiển thị suggestionsDiv nếu có gợi ý, ẩn nếu không có
}