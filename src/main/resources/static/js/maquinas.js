document.addEventListener("DOMContentLoaded", () => {
  const modal = document.getElementById("modalMaquina");

  const botonesDetalle = document.querySelectorAll(".btn-detalle-maquina");

  const botonCerrar = document.getElementById("cerrarModalMaquina");

  const botonCerrarInferior = document.getElementById(
    "cerrarModalMaquinaInferior",
  );

  const fondo = document.getElementById("cerrarModalFondo");

  function mostrarValor(valor) {
    if (valor === undefined || valor === null || valor.trim() === "") {
      return "Sin registro";
    }

    return valor;
  }

  function abrirModal(boton) {
    document.getElementById("modalCodigo").textContent = mostrarValor(
      boton.dataset.codigo,
    );

    document.getElementById("modalNombre").textContent = mostrarValor(
      boton.dataset.nombre,
    );

    document.getElementById("modalEstado").textContent = mostrarValor(
      boton.dataset.estado,
    );

    document.getElementById("modalArea").textContent = mostrarValor(
      boton.dataset.area,
    );

    document.getElementById("modalUbicacion").textContent = mostrarValor(
      boton.dataset.ubicacion,
    );

    document.getElementById("modalFabricante").textContent = mostrarValor(
      boton.dataset.fabricante,
    );

    document.getElementById("modalModelo").textContent = mostrarValor(
      boton.dataset.modelo,
    );

    document.getElementById("modalSerie").textContent = mostrarValor(
      boton.dataset.serie,
    );

    document.getElementById("modalDescripcion").textContent = mostrarValor(
      boton.dataset.descripcion,
    );

    modal.classList.remove("hidden");
    modal.classList.add("flex");

    document.body.classList.add("overflow-hidden");
  }

  function cerrarModal() {
    modal.classList.add("hidden");
    modal.classList.remove("flex");

    document.body.classList.remove("overflow-hidden");
  }

  botonesDetalle.forEach((boton) => {
    boton.addEventListener("click", () => {
      abrirModal(boton);
    });
  });

  botonCerrar.addEventListener("click", cerrarModal);

  botonCerrarInferior.addEventListener("click", cerrarModal);

  fondo.addEventListener("click", cerrarModal);

  document.addEventListener("keydown", (evento) => {
    if (evento.key === "Escape") {
      cerrarModal();
    }
  });
});
