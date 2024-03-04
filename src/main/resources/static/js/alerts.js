document.querySelectorAll("[id^='openAlert']").forEach(function (element) {
  element.addEventListener("click", function (e) {
    e.preventDefault();
    var index = this.id.replace("openAlert", "");
    $('#confirmAlert' + index).modal('show');
  });
});

document.querySelectorAll("[id^='closeModalBtn']").forEach(function (element) {
  element.addEventListener("click", function () {
    var index = this.closest(".modal").id.replace("confirmAlert", "");
    $('#confirmAlert' + index).modal('hide');
  });
});

function confirmAction(confirm, index) {
  if (confirm) {
    // Handle OK action
  } else {
    $('#confirmAlert' + index).modal('hide');
  }
}
