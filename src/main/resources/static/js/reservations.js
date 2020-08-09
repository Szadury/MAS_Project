function showAlert() {
    alert("The button was clicked!");
}

function showReservationsForSeat(seat, availableReservations){
    console.log(availableReservations);
    console.log(seat.id);
    var bookingDiv = document.getElementById('book_available_hour');
    var tbl = document.createElement('table');
    var tbdy = document.createElement('tbody');
    for(let reservation in availableReservations){
        if(reservation.barTable.id === seat.id){
            console.log("Rezerwacja na stolik: " + seat.id);
        }
    }
    alert("The button was clicked!");
}