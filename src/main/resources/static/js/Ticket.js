function addToCart(id, quantity) {
    const pop = document.querySelector("#popchat");
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/cart/add?id=" + id + "&quantity=" + quantity, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // Phản hồi thành công
                console.log(xhr.responseText);
                pop.classList.add("active");
                setTimeout(() => {
                    pop.classList.remove("active");
                }, 2000);
            } else {
                // Phản hồi lỗi
                alert(xhr.responseText); // Hiển thị thông báo từ máy chủ
            }
        }
    };
    xhr.send();
}