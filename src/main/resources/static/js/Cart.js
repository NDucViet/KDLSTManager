document.addEventListener('DOMContentLoaded', function () {
    let total = 0;
    document.querySelectorAll('.main').forEach(function (item) {
        const price = parseFloat(item.getAttribute('data-price'));
        total += price;
    });
    document.getElementById('items-total').innerText = total.toLocaleString('vi-VN') + ' ₫';
    document.getElementById('total-price').innerText = (total + total * 0.1).toLocaleString('vi-VN') + ' ₫'; // Assuming 5000&#8363; is the shipping cost
});

var input;
var currentValue = {};

window.onload = function () {
    input = document.querySelectorAll('input[name^="quantity"]');
    input.forEach(function (inputField) {
        currentValue[inputField.name] = parseInt(inputField.value);
    });
    // Thực hiện các hành động khác sau khi trang đã được tải
};

function adjustQuantity(id, delta) {
    var input = document.querySelector('input[name="quantity' + id + '"]');
    var currentValue = parseInt(input.value);
    var newValue = currentValue + delta;
    // Đảm bảo giá trị không nhỏ hơn giá trị tối thiểu
    if (newValue >= parseInt(input.min)) {
        input.value = newValue;
        updateCart(id, newValue);
    } else {
        alert("Số lượng không hợp lệ");
    }
    // Cập nhật giá trị mới vào input
}

let timeout = null;

function updateCart(id) {
    clearTimeout(timeout);
    timeout = setTimeout(() => {

        var inputField = document.querySelector('input[name="quantity' + id + '"]');
        var currentValueId = inputField.name;
        var quantity = document.getElementById('quantity' + id).value;
        console.log(quantity);

        if (quantity <= 0) {
            alert("Số lượng không hợp lệ");
            inputField.value = currentValue[currentValueId];
            return;
        }

        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/cart/update?id=" + id + "&quantity=" + quantity, true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                location.reload();
            }
        };
        xhr.send();
    }, 500)
}

function deleteRow(id) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/cart/delete?id=" + id, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            location.reload();
        }
    };
    xhr.send();
}
function confirmCheckOut() {
    // Lấy giá trị của trường ngày
    var date = document.querySelector('input[name="date"]').value;

    // Kiểm tra nếu ngày không được chọn
    if (!date) {
        alert("Please select a date.");
        return false; // Ngăn chặn việc gửi form
    }

    // Kiểm tra nếu giỏ hàng rỗng

    // Nếu tất cả các kiểm tra đều vượt qua, cho phép gửi form
    return confirm("Are you sure you want to proceed to checkout?");
}