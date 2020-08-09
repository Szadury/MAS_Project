function showReservationsForSeat(seat, availableReservations) {
    console.log(seat);
    console.log(availableReservations);
    var bookingDiv = document.getElementById('book_available_hour');
    var tbl = document.createElement('table');
    var tbdy = document.createElement('tbody');
    for (i = 0; i < availableReservations.length; i++) {
        var reservation = availableReservations[i];
        var barTable = reservation.barTable;
        if (barTable.id === seat) {
            //<span th:text="${'Stolik no.' + reservation.barTable.id + ' dla ' + reservation.barTable.numberOfSeats + ' osób  |   ' + #temporals.format(reservation.startTime, 'HH:mm') + ' - ' + #temporals.format(reservation.endTime, 'HH:mm')}"> Name </span>
            var tr = document.createElement('tr');
            var form = document.createElement("form");
            form.setAttribute("method", "post");
            form.setAttribute("action", "/reservation/createReservation");
            var td = document.createElement('td');
            //                 <input type="text" name="tableId" th:value="${reservation.barTable.id}"/>
            //                 <input type="text" name="startTime" th:value="${reservation.startTime}"/>
            //                 <input type="text" name="endTime" th:value="${reservation.endTime}"/>
            //                 <input type="text" name="barId" th:value="${barId}"/>
            //                 <input type="submit" value="Rezerwuj">
            var startTime = new Date(reservation.startTime);
            var endTime = new Date(reservation.endTime);

            var dateSpan = document.createElement('span')
            dateSpan.innerHTML = 'Stolik no. ' + seat + ' dla ' + barTable.numberOfSeats + ' osób  |   '
                + startTime.toLocaleTimeString() + ' - ' + new Date(reservation.endTime).toLocaleTimeString();

            var tableIdInput = document.createElement("input");
            tableIdInput.value = seat;
            var startTimeInput = document.createElement("input");
            tableIdInput.value = seat;


            console.log(dateSpan.innerHTML)
        }
    }
    alert("The button was clicked!");
}