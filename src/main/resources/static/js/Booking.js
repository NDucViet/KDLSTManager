
function toggleCheckbox(checkbox) {
    var customCheckbox = checkbox.parentElement.querySelector('.custom-checkbox');
    if (checkbox.checked) {
        customCheckbox.classList.add('checked');
    } else {
        customCheckbox.classList.remove('checked');
    }
}
function updateTotalPrice() {
    var startDateString = document.getElementById('startDate').value;
    var endDateString = document.getElementById('endDate').value;

    // Phân tích chuỗi ngày thành đối tượng ngày
    var startDate = parseDate(startDateString);
    var endDate = parseDate(endDateString);

    // Tính toán số ngày giữa startDate và endDate
    var oneDay = 24 * 60 * 60 * 1000; // số mili giây trong một ngày
    var diffDays = Math.round(Math.abs((endDate - startDate) / oneDay));

    // Get total price from selected rooms
    var totalPrice = calculateSelectedRoomsTotal();

    // Tính tổng số tiền
    var totalAmount = diffDays * totalPrice;

    // Update the total price on the UI
    var orderTotalElement = document.getElementById('orderTotal');
    orderTotalElement.textContent = formatCurrency(totalAmount);

    //Set value for total price for sent to payment
    var bookingRoomInput = document.getElementById('total');
    bookingRoomInput.value = totalAmount;

    // Enable/disable book button based on totalPrice
    var bookButton = document.querySelector('.buttonCheckout');
    if (totalAmount > 0) {
        bookButton.disabled = false;
    } else {
        bookButton.disabled = true;
    }
}

function parseDate(dateString) {
    var parts = dateString.split(/[\s,]+/);
    var day = parseInt(parts[0]);
    var monthIndex = getMonthIndex(parts[1]); // Lấy chỉ số của tháng
    var year = parseInt(parts[2]);
    return new Date(year, monthIndex, day);
}

function getMonthIndex(monthName) {
    var months = [
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    ];
    return months.indexOf(monthName);
}
function formatCurrency(amount) {
    // Assuming amount is in đồng (VND), format it as needed
    return amount.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
}

function calculateSelectedRoomsTotal() {
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
    var totalPrice = 0;

    checkboxes.forEach(function (checkbox) {
        if (checkbox.checked) {
            var price = parseFloat(checkbox.value);
            totalPrice += price;
        }
    });

    return totalPrice;
}

function toCheckout() {
    var bookingRoomInput = document.getElementById("bookingRoom");
    var checkboxes = document.querySelectorAll('input[name="checkBoxNoLabel"]:checked');
    var selectedRooms = [];

    checkboxes.forEach(function (checkbox) {
        selectedRooms.push(checkbox.nextElementSibling.nextElementSibling.textContent);
    });

    bookingRoomInput.value = selectedRooms.join(",");
    return true;
}