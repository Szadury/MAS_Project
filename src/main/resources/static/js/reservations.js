function showReservationsForSeat(seat, availableReservations) {
    let bookingDiv = document.getElementById('book_available_hour');
    let tbl = document.createElement('table');
    let tbdy = document.createElement('tbody');
    for (i = 0; i < availableReservations.length; i++) {
        let reservation = availableReservations[i];
        var barTable = reservation.barTable;
        if (barTable.id === seat) {
            var tr = document.createElement('tr');
            var form = document.createElement("form");
            form.setAttribute("method", "post");
            form.setAttribute("action", "/reservation/createReservation");
            var td = document.createElement('td');
            var startTime = new Date(reservation.startTime);
            var endTime = new Date(reservation.endTime);
           var dateSpan = document.createElement('span')
            dateSpan.innerHTML = 'Stolik no. ' + seat + ' dla ' + barTable.numberOfSeats + ' osób  |   '
                + startTime.toLocaleTimeString() + ' - ' + new Date(reservation.endTime).toLocaleTimeString();

            var tableIdInput = document.createElement("INPUT");
            tableIdInput.setAttribute("type", "text");
            tableIdInput.setAttribute("name", "tableId");
            tableIdInput.setAttribute("value", seat);
            var startTimeInput = document.createElement("INPUT");
            startTimeInput.setAttribute("type", "text");
            startTimeInput.setAttribute("name", "startTime");
            startTimeInput.setAttribute("value", startTime.toDateString());
            var endTimeInput = document.createElement("INPUT");
            endTimeInput.setAttribute("type", "text");
            endTimeInput.setAttribute("name", "endTime");
            endTimeInput.setAttribute("value", endTime.toDateString());
            var barIdInput = document.createElement("INPUT");
            barIdInput.setAttribute("type", "text");
            barIdInput.setAttribute("name", "barId");
            barIdInput.setAttribute("value", reservation.bar.id);
            var submitInput = document.createElement("INPUT");
            submitInput.setAttribute("type", "submit");
            submitInput.setAttribute("value", "Rezerwuj");

            td.appendChild(dateSpan);
            td.appendChild(tableIdInput);
            td.appendChild(startTimeInput);
            td.appendChild(endTimeInput);
            td.appendChild(barIdInput);
            td.appendChild(submitInput);
            form.appendChild(td);
            tr.appendChild(form);
            tbdy.appendChild(tr);
            console.log(dateSpan.innerHTML);
        }
    }
    tbl.appendChild(tbdy);
    console.log(tbl.innerHTML);
    bookingDiv.replaceChild(tbl, bookingDiv.firstChild);
}