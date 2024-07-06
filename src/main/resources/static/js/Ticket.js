var currentValue = {};

window.onload = function () {
    input = document.querySelectorAll('input[name^="quantity"]');
    input.forEach(function (inputField) {
        currentValue[inputField.name] = parseInt(inputField.value);
    });
    // Thực hiện các hành động khác sau khi trang đã được tải
};

function addToCart(id) {
    var input = document.querySelector('input[name="quantity' + id + '"]');
    var quantity = parseInt(input.value);
    var min = parseInt(input.min);
    var max = parseInt(input.max);
    var currentValueId = input.name;

    if (isNaN(quantity) || quantity < min || quantity > max) {
        alert("Số lượng không hợp lệ");
        input.value = currentValue[currentValueId];
        return;
    }

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

function adjustQuantity(id, delta) {
    var input = document.querySelector('input[name="quantity' + id + '"]');
    var currentValue = parseInt(input.value);
    var newValue = currentValue + delta;
    // Đảm bảo giá trị không nhỏ hơn giá trị tối thiểu
    if (newValue > parseInt(input.min) || newValue < parseInt(input.max)) {
        input.value = newValue;
        updateCart(id, newValue);
    } else {
        alert("Số lượng không hợp lệ");
    }
}