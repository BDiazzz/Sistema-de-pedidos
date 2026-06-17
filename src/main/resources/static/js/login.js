document.addEventListener("DOMContentLoaded", function () {
  const formRecuperar = document.getElementById("formRecuperar");
  formRecuperar.addEventListener("submit", function (event) {
    Swal.fire({
      title: "¡Procesado recuperación de credenciales!",
      html: "Se está procesando la petición de recuperación de usuario",
      allowOutsideClick: false, // Evita que se cierre al hacer clic fuera de la alerta
      didOpen: () => {
        Swal.showLoading(); // Muestra el indicador de carga
      },
    });
  });
});
