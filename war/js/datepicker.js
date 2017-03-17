function showCalendar(id, sistema) {
  var vWinCal=window.open(sistema+'js/calender.html', 'calendar','width=210,height=160,status=no,resizable=yes,top=200,left=200');
  objeto=id;
  vWinCal.focus();
}